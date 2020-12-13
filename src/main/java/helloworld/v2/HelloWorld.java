package helloworld.v2;

/**
 * 一个封闭的系统
 * 1，不用关心触发
 * 2，不用关心执行
 * 3，只关心按照预定的位置写代码、调用方法
 */
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
    public static void execute() {
        hello();
    }

    public static void hello() {
        System.out.println("hello, world!");
    }
}
