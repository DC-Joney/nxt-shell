package com.nxt.shell.command.support;

@FunctionalInterface
public interface ExceptionHandler {

    void handleException(Throwable throwable);

    default ExceptionHandler addThen(ExceptionHandler exceptionHandler){
        return exceptionHandler;
    }
}
