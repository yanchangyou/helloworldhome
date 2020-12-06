package helloworld.v7;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Scanner;

public class HelloWorld {

    /**
     * 支持命令从文件中读取
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        InputStream input = HelloWorld.class.getResourceAsStream("hello.run");
        String cmd = new Scanner(input).nextLine();
        execute(cmd);
    }

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
