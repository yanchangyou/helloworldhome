package helloworld.v10;

import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class HelloWorld {

    /**
     * java执行入口;
     * 上一版main中引入了过多的复杂度，需要重构，按照单一职责原则进行重构
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        final String cmdFile = "hello.run";
        task(cmdFile);
    }

    /**
     * 定时任务层
     *
     * @param cmdFile
     */
    private static void task(String cmdFile) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                executeWithNoException(cmdFile);
            }
        }, 1, 2000L);
    }

    /**
     * 异常处理层
     *
     * @param cmdFile
     */
    private static void executeWithNoException(String cmdFile) {
        try {
            executeFile(cmdFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 执行文件层
     *
     * @param cmdFile
     * @throws Exception
     */
    private static void executeFile(String cmdFile) throws Exception {
//                System.out.println("begin run...");
        URL url = HelloWorld.class.getResource(cmdFile);
        String runPath = url.getFile().replace("/build/resources/main", "/src/main/resources");
        InputStream input = null;
        input = new FileInputStream(runPath);
        Scanner scanner = new Scanner(input);
        while (scanner.hasNext()) {
            String cmd = scanner.nextLine();
            executeCmd(cmd);
        }
//                System.out.println("end run!");
        System.out.println();
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


