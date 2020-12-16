package helloworld.v1.v3;

import java.lang.reflect.Method;
import java.util.Scanner;

public class HelloWorld {

    /**
     * 支持控制台输出入命令;
     * <p>
     * 把命令入口放到java执行之后；上一版是放到java执行的参数中，只能实现一次传入
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        String cmd = new Scanner(System.in).next();
        execute(cmd);
    }

    public static void execute(String cmd) throws Exception {

        Method method = HelloWorld.class.getDeclaredMethod(cmd, new Class[0]);
        method.invoke(null);

    }

    public static void hello() {
        System.out.println("hello, world!");
    }
}
