package helloworld.v11;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;

/**
 * 方法分层、属性分域
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
     * 执行文件层;
     * 上一版执行文件夹杂了很多复杂逻辑，需要进一步抽象，重构
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

        String instruction = cmd.split("\\s+")[0];
        String param = cmd.split("\\s+")[1];

        Method method = HelloWorld.class.getDeclaredMethod(instruction, String.class);

        method.invoke(null, param);

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
}
