package helloworld.v3.v2.v1;

/**
 * 系统直接输出的方式
 */
public class HelloWorldWithSystemOut implements Printable {

    public static void main(String[] args) {

        String who = "world";
        HelloWorldWithSystemOut helloWorld = new HelloWorldWithSystemOut();

        String text = hello(who);

        helloWorld.print(text);
    }

    private static String hello(String who) {
        return "hello, " + who + "!";
    }

    @Override
    public void print(String text) {
        System.out.println(text);
    }


}
