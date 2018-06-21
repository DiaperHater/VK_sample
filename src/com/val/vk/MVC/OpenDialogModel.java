package com.val.vk.MVC;

import com.val.vk.Message;
import com.val.vk.VkMethods;
import com.val.vk.exceptions.EmptyParamException;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.lang.reflect.Method;

public class OpenDialogModel {
    public StringProperty userName = new SimpleStringProperty();
    public IntegerProperty id = new SimpleIntegerProperty();
    public ObservableList<Message> messages = FXCollections.observableArrayList();
    private VkMethods vk;

    public OpenDialogModel(VkMethods vk){
        this.vk = vk;
    }

    public void checkForNewMessages() throws IOException {
        ObservableList<Message> freshMessages
                = FXCollections.observableArrayList(
                vk.getMessageHistory(0, 20, id.get()).items);
        ObservableList<Message> newMessages = FXCollections.observableArrayList();

        for (int i = 0 ; i < freshMessages.size(); i++){
            if(freshMessages.get(i).date == messages.get(0).date){
                break;
            }else {
                newMessages.add(freshMessages.get(i));
            }
        }

        messages.addAll(0, newMessages);
    }

    public void openDialog(int userId, String userName) {
        this.userName.set(userName);
        this.id.set(userId);
        messages.clear();
        try {
            messages.addAll(vk.getMessageHistory(0, 20, userId).items);
        } catch (IOException e) {
            e.printStackTrace();
            Platform.exit();
        }
    }
}
