package helloworld.v2.v3.v3;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.io.*;

public class HelloWorldTest {

    @Test
    void test() throws Exception {
        System.out.println("test begin");
        final int BYTE_SIZE = 1024 * 1024;
        PrintStream oldPrint = System.out;
        String codeFile = "hello.run";
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(BYTE_SIZE);
        PrintStream out = new PrintStream(outputStream);
        System.setOut(out);
        HelloWorld.executeFile(codeFile);
        String output = outputStream.toString();
        System.setOut(oldPrint);
        System.out.println("output: " + output);
        String expected = HelloWorld.readProjectFile("hello.run.out");
        Assert.assertEquals(expected, output);

    }
}