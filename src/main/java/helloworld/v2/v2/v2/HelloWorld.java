package helloworld.v2.v2.v2;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 方法分层、属性分域
 * <p>
 * 遇到一个问题：命令行解析时，怎么按照语法进行解析;
 * 1, 按照空格区分，语法定义：第一个单词是命令，后面是命令行参数，位置定义，按照前导参数名定义
 * 1.0, 定一个单词是命令，后面的整体部分作为参数
 * 1.1，-Dname=value 这种语法
 * 1.2，空格区分
 * 1.3，-name value 形式（支持引号和非引号混用）
 * 2，异常处理，不满足语法的进行提示
 * 3，添加if逻辑，条件为1时，才进行输出
 * <p>
 * 总结
 * 1, 不要使用相近的单词，避免混淆
 * 2, 修改代码后，删除多余的代码，清理无用代码
 * <p>
 * 命令式属于线性执行，不支持嵌套，递归;
 * 表达式支持嵌套，花括号支持嵌套，层层嵌套，实现复杂逻辑
 * 使用栈也能实现嵌套效果，逆波兰式
 *
 */
public class HelloWorld {

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

        String instruction = cmd.split("\\s+")[0];

        String params = cmd.substring(instruction.length()).trim();

//        Pattern paramPart
        // 这个这种表达式很难写，因为有两者规则，不知道如何写
        //第一种规则：空格区分，第二种规则：引号间隔；而且这两者规则可以组合使用
        //分析：引号的优先级高于非引号，于是可以分两步处理，先处理引号，然后使用非引号，这样是否合理
        //简化处理，要么全部使用引号，要么不使用引号这样判断就简单了，后续再考虑组合的情况；一定有折中的方案，以及折中的折中
        Object[] paramParts = parseParams(params).toArray();

        Method method = getMethod(instruction);

        if (method.getParameterCount() == 1) {
            method.invoke(null, paramParts[0]);
        } else if (method.getParameterCount() == 0) {
            method.invoke(null);
        } else {
            method.invoke(null, paramParts);
        }

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
     * @param who
     */
    private static void hello(String who) {
        who = who == null ? "world" : who;
        System.out.println("hello, " + who + "!");
    }

    /**
     * 扩展更多命令
     *
     * @param text
     */
    private static void print(String text) {
        System.out.println(text);
    }

    /**
     * 扩展更多命令
     *
     * @param text
     */
    private static void ifPrint(String condition, String text) {
        if ("1".equals(condition)) {
            System.out.println(text);
        }
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
