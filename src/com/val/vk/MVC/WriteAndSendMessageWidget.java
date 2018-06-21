package com.val.vk.MVC;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;


public class WriteAndSendMessageWidget extends VBox {
    private TextArea textArea = new TextArea();
    private Button sendButton = new Button("Send");

    public WriteAndSendMessageWidget(){

        setAlignment(Pos.CENTER_RIGHT);
        getChildren().addAll(textArea, sendButton);
    }

    public void setOnSendButtonClicked(EventHandler<ActionEvent> handler){
        sendButton.setOnAction(handler);
    }
    public String getText() {
        return textArea.getText();
    }
    public void clearText() {
        textArea.clear();
    }

    public TextArea getTextArea() {
        return textArea;
    }

    public void setOnTextAreaKeyReleased(EventHandler<KeyEvent> handler) {
        textArea.setOnKeyReleased(handler);
    }
}
