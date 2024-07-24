package io.github.kamilszewc.simplerouter;

public class NoRoutingException extends Exception {

    public NoRoutingException() {
        super();
    }

    public NoRoutingException(String message) {
        super(message);
    }

    public NoRoutingException(String message, Throwable cause) {
        super(message, cause);
    }
}
