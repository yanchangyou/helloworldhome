package helloworld.v0.v4;

/**
 * 面向对象
 */
public class HelloWorld {

    public static void main(String[] args) {
       HelloWorld helloWorld = new HelloWorld();
       helloWorld.execute();
    }

    /**
     * 统一的调用方法封装;
     * 接口思想；统一的接口，不同的实现;
     * 对象级别复用；
     * 通过接口统一；
     * <p>
     * 稳定的结构
     */
    public void execute() {
        hello();
    }

    public void hello() {
        System.out.println("hello, world!");
    }
}
