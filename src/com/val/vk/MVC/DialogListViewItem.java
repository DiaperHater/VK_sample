package com.val.vk.MVC;

import com.val.vk.UserList;
import com.val.vk.VkMethods;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.time.format.DateTimeFormatter;

public class DialogListViewItem extends GridPane {
    private Label userName = new Label("user");
    private Label time = new Label("time");
    private Label body = new Label("body");
    private int userId;

    {
        time.setMinWidth(230);
        time.setMaxWidth(230);
        time.setAlignment(Pos.CENTER_RIGHT);
        body.setMaxWidth(230);

        add(time, 1, 1);
        add(userName, 1,2);
        add(body, 1, 3);
    }

    public DialogListViewItem(DialogWrapper dialogWrapper) {

        userName.setText(dialogWrapper.user);
        time.setText(dialogWrapper.time.format(
                DateTimeFormatter.ofPattern("dd/MM/YYYY  HH:MM")));
        body.setText(dialogWrapper.body.split("\n")[0]); //only first line

        userId = dialogWrapper.userId;
    }

    public String getUserName() {
        return userName.getText();
    }

    public int getUserId() {
        return userId;
    }
}
