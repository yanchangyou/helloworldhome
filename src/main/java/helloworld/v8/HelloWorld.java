package helloworld.v8;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Scanner;

public class HelloWorld {

    /**
     * 支持多条命令录入
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        InputStream input = HelloWorld.class.getResourceAsStream("hello.run");
        Scanner scanner = new Scanner(input);
        while (scanner.hasNext()) {
            String cmd = scanner.nextLine();
            execute(cmd);
        }
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
