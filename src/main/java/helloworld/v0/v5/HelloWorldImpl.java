package helloworld.v0.v5;

/**
 * 接口+对象实现的方式
 */
public class HelloWorldImpl implements HelloWorld, Executable {

    public static void main(String[] args) {

        HelloWorld helloWorld = new HelloWorldImpl();
        helloWorld.hello();

        Executable executor = new HelloWorldImpl();
        executor.execute();
    }

    public void hello() {
        System.out.println("hello, world!");
    }

    @Override
    public void execute() {
        hello();
    }
}
