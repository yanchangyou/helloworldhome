package helloworld.v3;

import java.lang.reflect.Method;

public class HelloWorld {

    public static void main(String[] args) throws Exception {
        execute("hello");
    }

    /**
     * 支持传入字符串调用;
     * 通过输入的内容确定执行的内容，更灵活；
     * 把执行权交给用户；
     * 更高维度的抽象；
     * 当传入不同命令时，执行不同的逻辑；
     * 命令和命令的执行独立开来；
     * 命令是面向用户的，命令执行是面向机器的
     * 代码入口：
     *
     * @param cmd
     * @throws Exception
     */
    private static void execute(String cmd) throws Exception {

        Method method = HelloWorld.class.getDeclaredMethod(cmd, new Class[0]);
        method.invoke(null);

    }

    private static void hello() {
        System.out.println("hello, world!");
    }
}
