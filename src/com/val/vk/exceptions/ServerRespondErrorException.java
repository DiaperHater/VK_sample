package com.val.vk.exceptions;

public class ServerRespondErrorException extends RuntimeException {
    public ServerRespondErrorException(String errorMsg){
        super(errorMsg);
    }
}
