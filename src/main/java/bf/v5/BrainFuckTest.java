package bf.v5;

import java.util.Arrays;

public class BrainFuckTest {

    public static void main(String[] args) {

        //源代码
        String code = "+2>+2>+2>+2>1<-1-1-1-1+3+2>3+2>2+2>5+2>1+129<123-+0-0<0>0>+-<";
        System.out.println(code);

        //编译成指令代码 : 把数字展开，重复指令N次，N>0
        String instructions = compile(code);
        System.out.println(instructions);

        final int SIZE = 8;

        byte[] data = new byte[SIZE];

        //解释执行指令
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
                    if (num == 0) {
                        builder.deleteCharAt(builder.length() - 1);
                    }
                    end = -1;
                }
                begin = i;
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
    public static void execute(String instructions, byte[] data) {

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

            //循环位移
            if (index >= data.length) {
                index = index % data.length;
            } else if (index < 0) {
                index = (index + data.length) % data.length;
            }

            StringBuilder message = new StringBuilder();

            message.append(instruction).append(" : ");
            message.append(index).append(" : ");
            message.append(Arrays.toString(data));

            System.out.println(message);
        }
    }
}
