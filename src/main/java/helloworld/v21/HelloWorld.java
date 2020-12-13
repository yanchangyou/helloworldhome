package helloworld.v21;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 变更：
 * 1，支持分号分隔语句
 * 2，支持if else
 * <p>
 * <p>
 * 思索：
 * 1，表达式可以独立拆分出来，单独解析，然后通过变量传递
 * 2，流程类无法拆分
 * 3，括号表达嵌套的概念，任意嵌套，任意组合
 * 4，通过goto实现复杂逻辑，通过函数封装细节
 * 5，边界字符，是否有歧义
 * 6，层次结构：{{},{}} ((),(),(()));[[][]] <>,//,./@#&*
 * 7，正则语义表达，能使用正则表达表达的语法结构；正则+嵌套：上下文无关文法；
 */
public class HelloWorld {

    static Map<String, String> nameValueMap = new HashMap();

    /**
     * 避免在多个参数之间透传：使用上下文，方法执行的上下文；
     * 对象是方法执行的上下文;
     */
    String cmdFile;

    HelloWorld(String cmdFile) {
        this.cmdFile = cmdFile;
    }

    /**
     * java执行入口
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        final String cmdFile = "hello.run";
        new HelloWorld(cmdFile).task();
    }

    /**
     * 定时任务层
     */
    private void task() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                executeWithNoException();
            }
        }, 1, 2000L);
    }

    /**
     * 异常处理层
     */
    private void executeWithNoException() {
        try {
            executeFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 执行文件层
     *
     * @throws Exception
     */
    private void executeFile() throws Exception {
//                System.out.println("begin run...");
        String codeFilePath = getCodeFilePath(cmdFile);
        List<String> statementList = readCodeFile(codeFilePath);
        for (String statement : statementList) {
            statement = statement.trim();
            if (isIgnore(statement)) {
                continue;
            }
            executeStatement(statement);
        }
//                System.out.println("end run!");
        System.out.println();
    }

    static boolean isIgnore(String statement) {
        return statement.startsWith("#");
    }

    /**
     * 读取文件工具层
     *
     * @param codeFile
     * @return
     * @throws FileNotFoundException
     */
    private List<String> readCodeFile(String codeFile) throws FileNotFoundException {
        InputStream input = new FileInputStream(codeFile);
        Scanner scanner = new Scanner(input);
        List<String> statementList = new ArrayList();
        while (scanner.hasNext()) {
            statementList.add(scanner.nextLine());
        }
        return statementList;
    }

    /**
     * 获取依赖路径工具层
     *
     * @param cmdFile
     * @return
     */
    private String getCodeFilePath(String cmdFile) {
        URL url = HelloWorld.class.getResource(cmdFile);
        String runPath = url.getFile().replace("/build/resources/main", "/src/main/resources");
        return runPath;
    }

    /**
     * 添加语句执行
     *
     * @param statement
     */
    static void executeStatement(String statement) throws Exception {

        executeCmdWrap(statement);
    }

    /**
     * 执行命令层
     *
     * @param cmd
     * @throws Exception
     */
    private static void executeCmdWrap(String cmd) throws Exception {
        /**
         * 空格处理
         */
        cmd = cmd.trim();

        if ("".equals(cmd)) {
            return;
        }
        //忽略注释
        if (cmd.startsWith("#")) {
            return;
        }

        String flow = null;
        String realCmd;
        if (cmd.matches("^(loop|if)\\b.*")) {
            flow = cmd.substring(0, cmd.indexOf(":")).trim();
            realCmd = cmd.substring(cmd.indexOf(":") + 1).trim();
        } else {
            realCmd = cmd;
        }
        if (flow != null && flow.startsWith("if")) {

            IfStatement ifStatement = new IfStatement(cmd);
            String condition = ifStatement.getIfExpress();
            boolean isExecuted = false;
            if ("true".equals(condition)) {
                executeCmd(ifStatement.getIfStatement());
                isExecuted = true;
            }
            if (!isExecuted && !ifStatement.getElseifExpress().isEmpty()) {
                List<String> conditions = ifStatement.getElseifExpress();
                for (int i = 0; i < conditions.size(); i++) {
                    String elseIfCondition = conditions.get(i);
                    if (!isExecuted && "true".equals(elseIfCondition)) {
                        executeCmd(ifStatement.getElseifStatement().get(i));
                        isExecuted = true;
                    }
                }
            }
            if (!isExecuted && ifStatement.getElseStatement() != null) {
                executeCmd(ifStatement.getElseStatement());
                isExecuted = true;
            }
            if (!isExecuted) {
                System.out.println("WARN: if not executed");
            }

        } else {
            executeFlow(flow, realCmd);
        }

    }

    /**
     * 流程处理层
     *
     * @param flow
     * @param cmd
     * @throws Exception
     */
    static void executeFlow(String flow, String cmd) throws Exception {
        if (flow == null) {
            executeCmd(cmd);
        } else {
            List<String> flowParts = parse(flow);
            String flowName = flowParts.get(0);
            if ("loop".equals(flowName)) {
                int times = Integer.parseInt(flowParts.get(1));
                for (int i = 0; i < times; i++) {
                    nameValueMap.put("i", i + "");
                    executeCmd(cmd);
                    nameValueMap.remove("i");
                }
            }
        }
    }

    /**
     * 执行命令层
     *
     * @param cmd
     * @throws Exception
     */
    private static void executeCmd(String cmd) throws Exception {

        List<String> cmdParts = parse(cmd);

        String instruction = cmdParts.get(0);

        Method method = getMethod(instruction);

        Object[] paramParts = new Object[]{parseVariable(cmdParts.subList(1, cmdParts.size()))};
        method.invoke(null, paramParts);
    }

    /**
     * 变量的语法规则：$开头的表示是变量
     *
     * @param params
     * @return
     */
    static List<String> parseVariable(List<String> params) {
        List<String> results = new ArrayList<>();
        for (String param : params) {

            String result = param;
            Pattern pattern = Pattern.compile("\\$\\{(\\w+)\\}");
            Matcher matcher = pattern.matcher(param);
            while (matcher.find()) {
                String key = matcher.group(1);
                String value = nameValueMap.get(key);
                result = result.replace("${" + key + "}", value);
            }
            results.add(result);
        }
        return results;
    }

    private static Method getMethod(String instruction) {
        Method[] methods = HelloWorld.class.getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().equals(instruction)) {
                return method;
            }
        }
        return null;
    }

    /**
     * 具体命令执行层
     *
     * @param params
     */
    private static void hello(List<String> params) {
        String who = params.get(0);
        who = who == null ? "world" : who;
        System.out.println("hello, " + who + "!");
    }

    /**
     * 扩展更多命令
     *
     * @param params
     */
    private static void print(List<String> params) {
        String text = params.get(0);
        System.out.println(text);
    }


    static String get(List<String> params) {
        String name = params.get(0);
        return getValue(name);
    }

    /**
     * 设置值
     *
     * @param params
     */
    static void set(List<String> params) {

        String name = params.get(0);
        String value = params.get(1);

        setValue(name, value);
    }

    static String getValue(String name) {
        return nameValueMap.get(name);
    }

    static void setValue(String name, String value) {
        nameValueMap.put(name, value);
    }

    /**
     * 解析参数独立出来
     *
     * @param params
     * @return
     */
    public static List<String> parse(String params) {

        List<String> paramList = new ArrayList();

        Pattern pattern = Pattern.compile("'([^']*?)'|\"([^\"]*?)\"|([^\\s:;]+)");
        Matcher matcher = pattern.matcher(params);
        while (matcher.find()) {
            String param = matcher.group(1);
            param = (param == null ? matcher.group(2) : param);
            param = (param == null ? matcher.group(3) : param);
            param = (param == null ? matcher.group(4) : param);
            paramList.add(param);
        }

        return paramList;
    }
}

