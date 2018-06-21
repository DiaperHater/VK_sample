package com.val.vk.exceptions;

public class EmptyTokenUrlException extends RuntimeException {
    public EmptyTokenUrlException(){}
    public EmptyTokenUrlException(String comment){
        super(comment);
    }
}
