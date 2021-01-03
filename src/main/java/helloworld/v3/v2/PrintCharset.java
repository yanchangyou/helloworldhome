package helloworld.v3.v2;

import java.util.HashMap;
import java.util.Map;

/**
 * 打印字符串单独抽取出来，方便维护
 */
public class PrintCharset {

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
            " OOOOOO \n" +
            "OO    OO\n" +
            "OO    OO\n" +
            "OO    OO\n" +
            " OOOOOO \n" +
            "";
    static String W = "" +
            "O     O     O\n" +
            " O   O O   O \n" +
            " O   O O   O \n" +
            "  O O   O O  \n" +
            "   O     O   \n" +
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
    public static Map<String, String> PRINT_CHARSET = new HashMap();

    static {
        PRINT_CHARSET.put("h", H);
        PRINT_CHARSET.put("H", H);
        PRINT_CHARSET.put("E", E);
        PRINT_CHARSET.put("e", E);
        PRINT_CHARSET.put("L", L);
        PRINT_CHARSET.put("l", L);
        PRINT_CHARSET.put("O", O);
        PRINT_CHARSET.put("o", O);
        PRINT_CHARSET.put("W", W);
        PRINT_CHARSET.put("w", W);
        PRINT_CHARSET.put("R", R);
        PRINT_CHARSET.put("r", R);
        PRINT_CHARSET.put("D", D);
        PRINT_CHARSET.put("d", D);
        PRINT_CHARSET.put(" ", SPACE);
        PRINT_CHARSET.put(",", COMMA);
        PRINT_CHARSET.put("!", TAN);
    }

}
