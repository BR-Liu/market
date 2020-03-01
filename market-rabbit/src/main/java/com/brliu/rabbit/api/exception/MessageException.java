package com.brliu.rabbit.api.exception;

public class MessageException extends RuntimeException {

    private static final long serialVersionUID = -1274988344127687250L;

    public MessageException() {

    }

    public MessageException(String message) {
        super(message);
    }

    public MessageException(Throwable cause) {
        super(cause);
    }

    public MessageException(String message, Throwable cause) {
        super(message, cause);
    }
}
