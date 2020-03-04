package com.brliu.rabbit.api.exception;

public class MessageException extends Exception {

    private static final long serialVersionUID = 1336293688514957424L;

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
