package edu.miu.cs544.identityprovider.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String s) {
        super("Type not found: " + s);
    }
}
