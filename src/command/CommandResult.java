package command;

/**
 * Created by qmgeng on 2014/8/4.
 */
public class CommandResult {
    private int exitVal;
    private String stdOut;
    private String stdErr;

    public CommandResult(int exitVal, String stdOut, String stdErr) {
        this.exitVal = exitVal;
        this.stdOut = stdOut;
        this.stdErr = stdErr;
    }

    public int getExitVal() {
        return exitVal;
    }

    public void setExitVal(int exitVal) {
        this.exitVal = exitVal;
    }

    public String getStdErr() {
        return stdErr;
    }

    public void setStdErr(String stdErr) {
        this.stdErr = stdErr;
    }

    public String getStdOut() {
        return stdOut;
    }

    public void setStdOut(String stdOut) {
        this.stdOut = stdOut;
    }
}
