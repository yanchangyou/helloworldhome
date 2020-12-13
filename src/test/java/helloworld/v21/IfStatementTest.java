package helloworld.v21;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IfStatementTest {

    @Test
    void parseIfTrue() {
        String ifStatementCode = "if true: hello world";
        IfStatement ifStatement = new IfStatement(ifStatementCode);
        System.out.println(ifStatement);
        Assert.assertEquals("true", ifStatement.getIfExpress());
        Assert.assertEquals("hello world", ifStatement.getIfStatement());
    }

    @Test
    void parseIfFalse() {
        String ifStatementCode = "if false: hello world";
        IfStatement ifStatement = new IfStatement(ifStatementCode);
        System.out.println(ifStatement);
        Assert.assertEquals("false", ifStatement.getIfExpress());
        Assert.assertEquals("hello world", ifStatement.getIfStatement());
    }

    @Test
    void parseIfElse() {
        String ifStatementCode = "if true: hello world :else: hello cosmos";
        IfStatement ifStatement = new IfStatement(ifStatementCode);
        System.out.println(ifStatement);
        Assert.assertEquals("true", ifStatement.getIfExpress());
        Assert.assertEquals("hello world", ifStatement.getIfStatement());
        Assert.assertEquals("hello cosmos", ifStatement.getElseStatement());
    }

    @Test
    void parseIfElseIf() {
        String ifStatementCode = "if true: hello world :elseif true: hello cosmos";
        IfStatement ifStatement = new IfStatement(ifStatementCode);
        System.out.println(ifStatement);
        Assert.assertEquals("true", ifStatement.getIfExpress());
        Assert.assertArrayEquals(new String[]{"true"}, ifStatement.getElseifExpress().toArray(new String[0]));
        Assert.assertArrayEquals(new String[]{"hello cosmos"}, ifStatement.getElseifStatement().toArray());
        Assert.assertNull(ifStatement.getElseStatement());
    }

    @Test
    void parseIfElseIfMore() {
        String ifStatementCode = "if true: hello world :elseif false: hello cosmos1 :elseif true: hello cosmos2";
        IfStatement ifStatement = new IfStatement(ifStatementCode);
        System.out.println(ifStatement);
        Assert.assertEquals("true", ifStatement.getIfExpress());
        Assert.assertArrayEquals(new String[]{"false", "true"}, ifStatement.getElseifExpress().toArray(new String[0]));
        Assert.assertArrayEquals(new String[]{"hello cosmos1", "hello cosmos2"}, ifStatement.getElseifStatement().toArray());
        Assert.assertNull(ifStatement.getElseStatement());
    }

    @Test
    void parseIfElseIfMoreElse() {
        String ifStatementCode = "if true: hello world :elseif false: hello cosmos1 :elseif true: hello cosmos2 :else: hello cosmos3";
        IfStatement ifStatement = new IfStatement(ifStatementCode);
        System.out.println(ifStatement);
        Assert.assertEquals("true", ifStatement.getIfExpress());
        Assert.assertArrayEquals(new String[]{"false", "true"}, ifStatement.getElseifExpress().toArray(new String[0]));
        Assert.assertArrayEquals(new String[]{"hello cosmos1", "hello cosmos2"}, ifStatement.getElseifStatement().toArray());
        Assert.assertEquals("hello cosmos3", ifStatement.getElseStatement());
    }
}