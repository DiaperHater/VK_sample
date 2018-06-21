package com.val.vk.MVC;

import com.val.vk.Dialog;
import com.val.vk.Message;
import com.val.vk.UserList;
import com.val.vk.VkMethods;
import com.val.vk.exceptions.EmptyParamException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class DialogListModel {

    ObservableList<DialogWrapper> currentDialogWrappers = FXCollections.observableArrayList();
    ObservableList<DialogWrapper> originalDialogWrappers = FXCollections.observableArrayList();
    private VkMethods vk;

    public DialogListModel(VkMethods vk) throws EmptyParamException {
        this.vk = vk;

        Dialog[] allDialogs = vk.getDialogs(200);//200 its max allowed by API
        List<DialogWrapper> wrappers = new ArrayList<>();
        for (Dialog d : allDialogs){
            if(d.getUserId() < 0){//ignore groups id
                continue;
            }
            wrappers.add(new DialogWrapper(d, vk));
        }

        originalDialogWrappers.setAll(wrappers);
        currentDialogWrappers.setAll(wrappers);
    }

    public void findInUserName(String text){

        if (text.isEmpty()){
            currentDialogWrappers.setAll(originalDialogWrappers);
            return;
        }

        currentDialogWrappers.clear();
        for (DialogWrapper d : originalDialogWrappers){
            if (d.user.toLowerCase().contains(text.toLowerCase())){
                currentDialogWrappers.add(d);
            }
        }
    }

    public void reset() {
        currentDialogWrappers.setAll(originalDialogWrappers);
    }

    public void refresh() {
        Dialog[] allDialogs = vk.getDialogs(originalDialogWrappers.size());


        boolean hasNewDialogs = true;
        for(Dialog d : allDialogs){
            if (d.getUserId() < 0){
                continue;
            }
            for (DialogWrapper dw : originalDialogWrappers){
                if (dw.getUserId() == d.getUserId()){
                    dw.setBody(d.getBody());
                    hasNewDialogs = false;
                    break;
                }
            }
            if (hasNewDialogs){
                originalDialogWrappers.add(new DialogWrapper(d, vk));
            }
            hasNewDialogs = true;
        }
    }
}
