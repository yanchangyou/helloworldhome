package helloworld.v0.v2;

public class HelloWorld {

    public static void main(String[] args) {
        hello();
    }

    /**
     * 实现静态方法封装;
     * 复用思想：代码级别复用；
     * 提炼、抽取便于复用
     */
    public static void hello() {
        System.out.println("hello, world!");
    }
}
