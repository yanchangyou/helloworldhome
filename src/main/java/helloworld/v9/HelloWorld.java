package helloworld.v9;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class HelloWorld {

    /**
     * 支持定时执行命令文件;
     * 可以连续实验，验证更多的场景；
     * 定时执行；
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
//                System.out.println("begin run...");
                URL url = HelloWorld.class.getResource("hello.run");
                String runPath = url.getFile().replace("/build/resources/main", "/src/main/resources");
                InputStream input = null;
                try {
                    input = new FileInputStream(runPath);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                Scanner scanner = new Scanner(input);
                while (scanner.hasNext()) {
                    String cmd = scanner.nextLine();
                    try {
                        execute(cmd);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
//                System.out.println("end run!");
                System.out.println();
            }
        }, 1, 2000L);

    }

    private static void execute(String cmd) throws Exception {

        String instruction = cmd.split("\\s+")[0];
        String param = cmd.split("\\s+")[1];

        Method method = HelloWorld.class.getDeclaredMethod(instruction, String.class);

        method.invoke(null, param);

    }

    private static void hello(String who) {
        who = who == null ? "world" : who;
        System.out.println("hello, " + who + "!");
    }
}
