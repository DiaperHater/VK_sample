package com.val.vk.MVC;

import com.val.vk.VkMethods;
import com.val.vk.exceptions.SendMessageErrorException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
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
    }

    private void startUpdateIncomingMessages() {
        new Thread(()->{
            while (true){
                try {
                    Thread.sleep(1000);
                    Platform.runLater(() -> {
                        if(openDialogModel.id.getValue().intValue() != 0){
                            try {
                                openDialogModel.checkForNewMessages();
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

    private void setOnActions() {
        dialogListView.getFindBoxField().setOnAction(this::onFind);
        dialogListView.getFindBoxField().setOnKeyReleased(this::onFind);
        dialogListView.getFindBoxClearButton().setOnAction(this::onClear);
        dialogListView.getListView().setOnMouseClicked(this::onDialogSelected);
        writeAndSendMessageWidget.setOnSendButtonClicked(this::onSendMessage);
        writeAndSendMessageWidget.setOnTextAreaKeyReleased(this::onSendMessage);
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

    private void onClear(ActionEvent event) {
        dialogListView.clearFindField();
        dialogListModel.reset();
    }

    private void onFind(Event event) {
        dialogListModel.findInUserName(dialogListView.findFieldVale());
    }
}
