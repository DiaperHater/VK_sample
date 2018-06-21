package com.val.vk.exceptions;

public class SendMessageErrorException extends Exception {
    public SendMessageErrorException(String message){
        super("error code: "+message);
    }
}
