package helloworld.v2.v2.v2;

import java.util.List;

class HelloWorldTest {

    @org.junit.jupiter.api.Test
    void parseParams() {
        String params = "abc 123 \"xyz\" \"897\"";
        List paramList = HelloWorld.parseParams(params);
        System.out.println(paramList);
    }

    @org.junit.jupiter.api.Test
    void parseParamsMore() {
        String[] params = new String[]{
                "abc 123 \"xyz\" \"897\"",
                "abc 123 \"xyz 897\"",
                "abc 123 'xyz\" \"897' 23 3",
        };
        for (int i = 0; i < params.length; i++) {
            List paramList = HelloWorld.parseParams(params[i]);
            System.out.println(paramList);
        }

    }
}