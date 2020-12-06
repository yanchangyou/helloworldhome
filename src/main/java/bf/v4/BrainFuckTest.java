package bf.v4;

import java.util.Arrays;

public class BrainFuckTest {

    public static void main(String[] args) {

        String code = "+2>+2>+2>+2>+3";

        System.out.println(code);
        String instructions = compile(code);
        System.out.println(instructions);

        final int SIZE = 8;

        int[] data = new int[SIZE];

        execute(instructions, data);

    }


    /**
     * 编译代码
     *
     * @param code
     * @return
     */
    public static String compile(String code) {

        StringBuilder builder = new StringBuilder();

        int begin = -1;
        int end = -1;
        char instruction = 0;
        boolean isNumber;

        for (int i = 0; i < code.length(); i++) {

            isNumber = false;

            char ch = code.charAt(i);

            if ('0' <= ch && ch <= '9') {
                end = i;
                isNumber = true;
            }

            if (('+' == ch || '-' == ch || '<' == ch || '>' == ch) || (isNumber && i == code.length() - 1)) {
                //数字结束
                if (end != -1) {
                    String numText = code.substring(begin + 1, end + 1);
                    int num = Integer.parseInt(numText);
                    for (int j = 0; j < num - 1; j++) {
                        builder.append(instruction);
                    }
                    begin = -1;
                    end = -1;
                } else {
                    begin = i;
                }
                if (('+' == ch || '-' == ch || '<' == ch || '>' == ch)) {
                    instruction = ch;
                    builder.append(instruction);
                }
            }
        }

        return builder.toString();
    }

    /**
     * 解释执行
     *
     * @param instructions
     * @param data
     */
    public static void execute(String instructions, int[] data) {

        int index = 0;

        for (int i = 0; i < instructions.length(); i++) {
            char instruction = instructions.charAt(i);
            switch (instruction) {
                case '+':
                    data[index]++;
                    break;
                case '-':
                    data[index]--;
                    break;
                case '<':
                    index--;
                    break;
                case '>':
                    index++;
                    break;
                default:
                    throw new RuntimeException("not recognized instruction: " + instruction);
            }

            StringBuilder message = new StringBuilder();

            message.append(instruction).append(" : ");
            message.append(index).append(" : ");
            message.append(Arrays.toString(data));

            System.out.println(message);
        }
    }
}
