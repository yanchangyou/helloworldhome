package helloworld.v21;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * if语句对象
 */
public class IfStatement {

    String ifExpress;
    String ifStatement;

    List<String> elseifExpress = new ArrayList();
    List<String> elseifStatement = new ArrayList();

    String elseStatement;

    IfStatement(String ifCode) {
        if (ifCode == null) {
            throw new RuntimeException("if statement can't null");
        }
        ifCode = ifCode.trim();
        if (!ifCode.startsWith("if")) {
            throw new RuntimeException("must be start with if");
        }

        parse(ifCode);
    }

    /**
     * 解析if语句
     *
     * @param ifCode
     */
    void parse(String ifCode) {
        Pattern ifPatternFirst = Pattern.compile("(\\s*if\\s*(true|false)\\s*:\\s*(\\w+\\s\\w+)\\s*)(.*)");

        Matcher matcher = ifPatternFirst.matcher(ifCode);

        if (matcher.find()) {
            ifExpress = matcher.group(2);
            ifStatement = matcher.group(3);
            String otherStatement = matcher.group(4);
            if (otherStatement != null) {

                Pattern elseifPattern = Pattern.compile("((:elseif\\s*\\w+\\s*:\\s*\\w+\\s*\\w+\\s*)*)" +
                        "(:else:\\s*((print|hello)\\s*\\w+\\s*))?");

                Matcher otherMatcher = elseifPattern.matcher(otherStatement);
                if (otherMatcher.find()) {
                    String elseIfStatement = otherMatcher.group(1);
                    elseStatement = otherMatcher.group(4);

                    if (elseIfStatement != null) {

                        Pattern onlyElseifPattern = Pattern.compile(":elseif\\s*(\\w+)\\s*:\\s*(\\w+\\s*\\w+)\\s*");
                        Matcher onlyElseifMather = onlyElseifPattern.matcher(elseIfStatement);

                        while (onlyElseifMather.find()) {
                            String onlyElseifExpress = onlyElseifMather.group(1);
                            elseifExpress.add(onlyElseifExpress);
                            String onlyElseifStatement = onlyElseifMather.group(2);
                            elseifStatement.add(onlyElseifStatement);
                        }
                    }
                }
            }
        } else {
            throw new RuntimeException("if statement must match: " + ifPatternFirst);
        }
    }

    public String getIfExpress() {
        return ifExpress;
    }

    public void setIfExpress(String ifExpress) {
        this.ifExpress = ifExpress;
    }

    public String getIfStatement() {
        return ifStatement;
    }

    public void setIfStatement(String ifStatement) {
        this.ifStatement = ifStatement;
    }

    public List<String> getElseifExpress() {
        return elseifExpress;
    }

    public void setElseifExpress(List<String> elseifExpress) {
        this.elseifExpress = elseifExpress;
    }

    public List<String> getElseifStatement() {
        return elseifStatement;
    }

    public void setElseifStatement(List<String> elseifStatement) {
        this.elseifStatement = elseifStatement;
    }

    public String getElseStatement() {
        return elseStatement;
    }

    public void setElseStatement(String elseStatement) {
        this.elseStatement = elseStatement;
    }

    @Override
    public String toString() {
        return "If{" +
                "ifExpress='" + ifExpress + '\'' +
                ", ifStatement='" + ifStatement + '\'' +
                ", elseifExpress=" + elseifExpress +
                ", elseifStatement=" + elseifStatement +
                ", elseStatement=" + elseStatement +
                '}';
    }

    public static void main(String[] args) {

//        testRegexp();

        String[] ifCodeText = {
                "if true: hello world",
                "if true: hello world :else: hello cosmos",
                "if true: hello world :elseif true: print space :else: hello cosmos",
                "if true: hello world :elseif true: print space1 :elseif true: print space2 :else: hello cosmos",
        };

        for (int i = 0; i < ifCodeText.length; i++) {

            IfStatement ifStatementCode = new IfStatement(ifCodeText[i]);
            System.out.println(ifStatementCode);
        }

    }

    private static void testRegexp() {
        Pattern pattern = Pattern.compile("(?<word>\\w+ )");
        Matcher matcher = pattern.matcher("abc def ");
        int start = 0;
        while (matcher.find(start)) {
            String all = matcher.group();
            System.out.println(all);
            String group = matcher.group("word");
            System.out.println(group);
            start = matcher.end();
        }
    }
}
