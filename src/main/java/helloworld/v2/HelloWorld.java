package helloworld.v2;

public class HelloWorld {

    public static void main(String[] args) {
        execute();
    }

    /**
     * 统一的调用方法封装;
     * 接口思想；统一的接口，不同的实现;
     * 对象级别复用；
     * 通过接口统一；
     *
     */
    private static void execute() {
        hello();
    }

    private static void hello() {
        System.out.println("hello, world!");
    }
}
