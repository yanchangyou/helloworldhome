package bf.v0;

import java.util.Arrays;

public class BrainFuckTest {

    public static void main(String[] args) {

        final int SIZE = 8;
        int[] data = new int[SIZE];

        String instructions = "+>+>+>-<+";

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
