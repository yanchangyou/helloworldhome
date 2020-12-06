package helloworld.v15;

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
 * 1，支持注释
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
        String cmdFilePath = getCmdFilePath(cmdFile);
        List<String> cmdList = readCmdFile(cmdFilePath);
        for (String cmd : cmdList) {
            executeCmd(cmd);
        }
//                System.out.println("end run!");
        System.out.println();
    }

    /**
     * 读取文件工具层
     *
     * @param cmdFile
     * @return
     * @throws FileNotFoundException
     */
    private List<String> readCmdFile(String cmdFile) throws FileNotFoundException {
        InputStream input = new FileInputStream(cmdFile);
        Scanner scanner = new Scanner(input);
        List<String> cmdList = new ArrayList();
        while (scanner.hasNext()) {
            cmdList.add(scanner.nextLine());
        }
        return cmdList;
    }

    /**
     * 获取依赖路径工具层
     *
     * @param cmdFile
     * @return
     */
    private String getCmdFilePath(String cmdFile) {
        URL url = HelloWorld.class.getResource(cmdFile);
        String runPath = url.getFile().replace("/build/resources/main", "/src/main/resources");
        return runPath;
    }

    /**
     * 执行命令层
     *
     * @param cmd
     * @throws Exception
     */
    private static void executeCmd(String cmd) throws Exception {

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

        String instruction = cmd.split("\\s+")[0];

        String params = cmd.substring(instruction.length()).trim();

        // 这个这种表达式很难写，因为有两者规则，不知道如何写
        //第一种规则：空格区分，第二种规则：引号间隔；而且这两者规则可以组合使用
        //分析：引号的优先级高于非引号，于是可以分两步处理，先处理引号，然后使用非引号，这样是否合理
        //简化处理，要么全部使用引号，要么不使用引号这样判断就简单了，后续再考虑组合的情况；一定有折中的方案，以及折中的折中

        Object[] paramParts = new Object[]{parseVariable(parseParams(params))};

        Method method = getMethod(instruction);

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
            if (param.startsWith("$")) {
                String variableName = param.substring(1);
                if (nameValueMap.containsKey(variableName)) {
                    result = nameValueMap.get(variableName);
                } else {
                    throw new RuntimeException("not exists variable [" + variableName + "]");
                }
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
    public static List<String> parseParams(String params) {

        List<String> paramList = new ArrayList();

        Pattern pattern = Pattern.compile("'([^']*?)'|\"([^\"]*?)\"|(\\S+)");
        Matcher matcher = pattern.matcher(params);
        while (matcher.find()) {
            String param = matcher.group(1);
            param = (param == null ? matcher.group(2) : param);
            param = (param == null ? matcher.group(3) : param);
            paramList.add(param);
        }

        return paramList;
    }
}
