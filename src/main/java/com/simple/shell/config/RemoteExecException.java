package com.simple.shell.config;


public class RemoteExecException extends RuntimeException {


    protected int defReturnCode = 500;

    public int getDefReturnCode() {
        return this.defReturnCode;
    }

    public void setDefReturnCode(int defReturnCode) {
        this.defReturnCode = defReturnCode;
    }

    public RemoteExecException() {
        super();
    }

    public RemoteExecException(String message) {
        super(message);
    }

    public RemoteExecException(String message, Throwable cause) {
        super(message, cause);
    }

    public RemoteExecException(Throwable cause) {
        super(cause);
    }

    protected RemoteExecException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
