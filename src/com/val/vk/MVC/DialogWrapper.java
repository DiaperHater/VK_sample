package com.val.vk.MVC;

import com.val.vk.Dialog;
import com.val.vk.HttpsGetter;
import com.val.vk.UserList;
import com.val.vk.VkMethods;
import com.val.vk.exceptions.EmptyParamException;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

public class DialogWrapper {
    String user;
    private int userId;
    LocalDateTime time;
    String body;
    StringProperty bodyProperty = new SimpleStringProperty();

    public DialogWrapper(Dialog dialog, VkMethods vk)  {
        UserList users = null;
        try {
            users = vk.getUsers(new int[]{dialog.getUserId()});
        } catch (EmptyParamException e) {
            e.printStackTrace();
        }

        user = users.getUsersFullName(0);
        userId = dialog.getUserId();
        time = LocalDateTime.ofInstant(
                Instant.ofEpochSecond(dialog.getTime())
                , TimeZone.getDefault().toZoneId());
        setBody(dialog.getBody());
    }

    public int getUserId() {
        return userId;
    }

    public void setBody(String text){
        body = text;
        bodyProperty.set(text);
    }
}
