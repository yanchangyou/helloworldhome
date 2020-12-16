package helloworld.v1.v2;

import java.lang.reflect.Method;

/**
 * 执行机制改变：由控制台输入内容
 */
public class HelloWorld {

    /**
     * 支持程序执行参数：传入执行命令
     * 把命令输入：提到java命令行入口，命令行入口;
     * 面向终端用户;
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        String cmd = args != null && args.length > 0 ? args[0] : "hello";
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
