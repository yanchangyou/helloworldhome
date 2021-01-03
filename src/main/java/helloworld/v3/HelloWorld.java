package helloworld.v3;

import java.util.HashMap;
import java.util.Map;

/**
 * 使用点阵形式输出字符串
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

    static String H = "" +
            "OO    OO\n" +
            "OO    OO\n" +
            "OOOOOOOO\n" +
            "OO    OO\n" +
            "OO    OO\n" +
            "";
    static String E = "" +
            "OOOOOOOO\n" +
            "OO      \n" +
            "OOOOOOOO\n" +
            "OO      \n" +
            "OOOOOOOO\n" +
            "";
    static String L = "" +
            "OO      \n" +
            "OO      \n" +
            "OO      \n" +
            "OO      \n" +
            "OOOOOOOO\n" +
            "";
    static String O = "" +
            "OOOOOOOO\n" +
            "OO    OO\n" +
            "OO    OO\n" +
            "OO    OO\n" +
            "OOOOOOOO\n" +
            "";
    static String W = "" +
            "O     O    O\n" +
            " O   O O   O\n" +
            " O   O O   O\n" +
            "  O O   O O \n" +
            "   O     O  \n" +
            "";
    static String R = "" +
            "OOOOOOO \n" +
            "OO    OO\n" +
            "OOOOOOO \n" +
            "OO  OO  \n" +
            "OO    OO\n" +
            "";
    static String D = "" +
            "OOOOO   \n" +
            "OO   OO \n" +
            "OO    OO\n" +
            "OO   OO \n" +
            "OOOOO   \n" +
            "";
    static String COMMA = "" +
            "       \n" +
            "       \n" +
            "  OO   \n" +
            "  OO   \n" +
            " OO    \n" +
            "";
    static String SPACE = "" +
            "   \n" +
            "   \n" +
            "   \n" +
            "   \n" +
            "   \n" +
            "";
    static String TAN = "" +
            "   OO   \n" +
            "   OO   \n" +
            "   OO   \n" +
            "        \n" +
            "   OO   \n" +
            "";
    static Map<String, String> map = new HashMap();

    static {
        map.put("h", H);
        map.put("H", H);
        map.put("E", E);
        map.put("e", E);
        map.put("L", L);
        map.put("l", L);
        map.put("O", O);
        map.put("o", O);
        map.put("W", W);
        map.put("w", W);
        map.put("R", R);
        map.put("r", R);
        map.put("D", D);
        map.put("d", D);
        map.put(" ", SPACE);
        map.put(",", COMMA);
        map.put("!", TAN);
    }

    /**
     * 打印字符：使用字符拼接字符，字符点阵拼接
     *
     * @param ch
     */
    private static void print(char ch, int line) {

        System.out.print(map.get(ch + "").split("\n")[line]);
        System.out.print("   ");

    }

}
