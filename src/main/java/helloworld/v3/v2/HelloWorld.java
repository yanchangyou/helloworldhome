package helloworld.v3.v2;

/**
 * 使用点阵形式输出字符串：除了正误问题，还有精度问题，体验问题，对称问题
 */
public class HelloWorld {

    public static void main(String[] args) {
        String who = "world";
        String text = hello(who);
        print(text);
    }

    private static String hello(String who) {
        return "hello, " + who + "!";
    }

    private static void print(String text) {

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
