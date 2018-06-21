package com.val.vk.MVC;

import com.val.vk.VkMethods;
import com.val.vk.exceptions.SendMessageErrorException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class Controller {
    private VkMethods vk;
    private DialogListModel dialogListModel;
    private DialogListView dialogListView;
    private OpenDialogModel openDialogModel;
    private OpenDialogView openDialogView;
    private WriteAndSendMessageWidget writeAndSendMessageWidget;

    public Controller(VkMethods vk,
                      DialogListModel dialogListModel,
                      DialogListView dialogListView,
                      OpenDialogModel openDialogModel,
                      OpenDialogView openDialogView,
                      WriteAndSendMessageWidget writeAndSendMessageWidget) {
        this.vk = vk;
        this.dialogListModel = dialogListModel;
        this.dialogListView = dialogListView;
        this.openDialogModel = openDialogModel;
        this.openDialogView = openDialogView;
        this.writeAndSendMessageWidget = writeAndSendMessageWidget;

        setOnActions();
        startUpdateIncomingMessages();
        openLastDialog();
    }

    private void setOnActions() {
        dialogListView.getListView().setOnMouseClicked(this::onDialogSelected);
        writeAndSendMessageWidget.setOnSendButtonClicked(this::onSendMessage);
        writeAndSendMessageWidget.setOnTextAreaKeyReleased(this::onSendMessage);
    }

    private void startUpdateIncomingMessages() {
        new Thread(()->{
            while (true){
                try {
                    Thread.sleep(3000);
                    Platform.runLater(() -> {
                        if(openDialogModel.id.getValue().intValue() != 0){
                            try {
                                openDialogModel.checkForNewMessages();
                                dialogListModel.refresh();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void openLastDialog() {
        int id = dialogListView.getUserIdOfTopItem();
        String userName = dialogListView.getUserNameOfTopItem();
        openDialogModel.openDialog(id, userName);
    }

    private void onSendMessage(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER){
            onSendMessage(new ActionEvent());
        }
    }

    private void onSendMessage(ActionEvent event) {
        int to = openDialogModel.id.get();
        String msg = writeAndSendMessageWidget.getText();

        try {
            vk.sendMessage(to, msg);
            writeAndSendMessageWidget.clearText();
            openDialogModel.checkForNewMessages();
            dialogListModel.refresh();
        } catch (SendMessageErrorException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void onDialogSelected(MouseEvent mouseEvent) {
        DialogListViewItem item = dialogListView.getSelectedItem();
        if (item == null){
            return;
        }
        int userId = item.getUserId();
        String userName = item.getUserName();
        openDialogModel.openDialog(userId, userName);
    }
}
