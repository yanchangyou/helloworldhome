package helloworld.v3.v2.v1;

import helloworld.v3.v1.v2.PrintCharset;

/**
 * 字符点阵的实现方式
 */
public class HelloWorldWithPrintCharset implements Printable {

    public static void main(String[] args) {

        String who = "world";
        HelloWorldWithPrintCharset helloWorld = new HelloWorldWithPrintCharset();

        String text = hello(who);

        helloWorld.print(text);
    }

    private static String hello(String who) {
        return "hello, " + who + "!";
    }

    @Override
    public void print(String text) {

        System.out.println();
        char[] chars = text.toCharArray();
        for (int j = 0; j < 5; j++) {
            for (int i = 0; i < chars.length; i++) {
                print(chars[i], j);
                if (i >= chars.length - 1) {
                    System.out.println();
                }
            }
        }
    }

    /**
     * 打印字符：使用字符拼接字符，字符点阵拼接
     *
     * @param ch
     */
    private static void print(char ch, int line) {

        System.out.print(PrintCharset.PRINT_CHARSET.get(ch + "").split("\n")[line]);
        System.out.print("   ");

    }
}
