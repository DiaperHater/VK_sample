package com.val.vk.MVC;

import com.val.vk.Dialog;
import com.val.vk.VkMethods;
import com.val.vk.exceptions.EmptyParamException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class DialogListModel {

    ObservableList<DialogWrapper> currentDialogWrappers = FXCollections.observableArrayList();
    ObservableList<DialogWrapper> originalDialogWrappers = FXCollections.observableArrayList();

    public DialogListModel(VkMethods vk) throws EmptyParamException {
        Dialog[] allDialogs = vk.getDialogs(200);//200 its max allowed by API

//        for (Dialog d : allDialogs){
//            if(d.getUserId() < 0){//ignore groups id
//                continue;
//            }
//        }

        List<DialogWrapper> wrappers = DialogWrapper.list(allDialogs, vk);
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
}
