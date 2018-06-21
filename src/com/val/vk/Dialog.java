package com.val.vk;

import com.val.vk.exceptions.DialogNullFieldException;

public class Dialog{
    private int unread;
    private Message message;

    public int getUserId(){
        if(message == null){
            throw new DialogNullFieldException();
        }
        return message.user_id;
    }

    public String getBody() {
        if(message == null){
            throw new DialogNullFieldException();
        }
        return message.body;
    }

    public long getTime() {
        if(message == null){
            throw new DialogNullFieldException();
        }
        return message.date;
    }
}