package com.simple.shell.config;

/**
 * RemoteCallback
 *
 * @author wuji
 * @date 2020-12-06 17:34
 */
public class RemoteCallback implements RemoteShellExecutor.Callback {

    private String stdoutString;

    private String stderrString;


    @Override
    public void setStdoutString(String stdoutString) {
        this.stdoutString = stdoutString;
    }

    @Override
    public void setStderrString(String stderrString) {
        this.stderrString = stderrString;
    }

    public String getStdoutString() {
        return stdoutString;
    }

    public String getStderrString() {
        return stderrString;
    }
}
