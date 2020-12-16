package helloworld.v1.v4;

import java.lang.reflect.Method;
import java.util.Scanner;

public class HelloWorld {

    public static void main(String[] args) throws Exception {
        String cmd = new Scanner(System.in).nextLine();
        execute(cmd);
    }

    /**
     * 支持输入命令拆分：命令+参数，更灵活
     * 支持参数：对代理的复用，处理不同的数据；对功能的复用，处理不同的数据
     *
     * @param cmd
     * @throws Exception
     */
    private static void execute(String cmd) throws Exception {

        String instruction = cmd.split("\\s+")[0];
        String param = cmd.split("\\s+")[1];

        Method method = HelloWorld.class.getDeclaredMethod(instruction, String.class);

        method.invoke(null, param);

    }

    private static void hello(String who) {
        who = who == null ? "world" : who;
        System.out.println("hello, " + who + "!");
    }
}
