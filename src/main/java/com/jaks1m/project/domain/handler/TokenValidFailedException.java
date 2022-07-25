package com.jaks1m.project.domain.handler;

public class TokenValidFailedException extends RuntimeException{
    public TokenValidFailedException() {
        super("Failed to generate Token.");
    }

    private TokenValidFailedException(String message) {
        super(message);
    }
}
