package com.brliu.rabbit.api.exception;

public class MessageRuntimeException extends RuntimeException {

    private static final long serialVersionUID = -1274988344127687250L;

    public MessageRuntimeException() {

    }

    public MessageRuntimeException(String message) {
        super(message);
    }

    public MessageRuntimeException(Throwable cause) {
        super(cause);
    }

    public MessageRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
