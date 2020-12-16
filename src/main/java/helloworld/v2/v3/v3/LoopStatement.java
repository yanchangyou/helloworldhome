package helloworld.v2.v3.v3;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * loop循环语句
 * 语法 loop N: statement
 * 把statement执行N次，N>=0;
 */
public class LoopStatement {
    int loopTimes;
    String statement;

    public LoopStatement(String loopStatement) {
        //参数检查
        if (loopStatement == null) {
            throw new RuntimeException("loop can't be null!");
        }
        loopStatement = loopStatement.trim();

        String loopPattern = "loop\\s+(\\d+)\\s*:\\s*(.+)";
        Pattern pattern = Pattern.compile(loopPattern);
        Matcher matcher = pattern.matcher(loopStatement);
        if (matcher.find()) {
            loopTimes = Integer.parseInt(matcher.group(1));
            statement = matcher.group(2);
        } else {
            throw new RuntimeException("loop statement must match: " + loopPattern);
        }
    }

    public int getLoopTimes() {
        return loopTimes;
    }

    public void setLoopTimes(int loopTimes) {
        this.loopTimes = loopTimes;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }
}
