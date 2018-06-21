package com.val.vk.MVC;

import com.val.vk.Message;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class OpenDialogView extends VBox {
    private OpenDialogModel model;
    private ListView listView = new ListView();

    public OpenDialogView(OpenDialogModel model){
        this.model = model;

        model.messages.addListener(new ListChangeListener<Message>() {
            @Override
            public void onChanged(Change<? extends Message> c) {
                c.next();
                listView.getItems().clear();

                for(Message m : model.messages){
                    String timeStamp = LocalDateTime.ofInstant(
                            Instant.ofEpochSecond(m.date),
                            ZoneId.systemDefault())
                            .toString();
                    String userName = (m.from_id == model.id.get() ? model.userName.get() : "Me");

                    listView.getItems().add(0, new Item(userName, timeStamp, m.body));
                }
                listView.scrollTo(listView.getItems().size());
            }
        });

        ObservableList<Item> items = FXCollections.observableArrayList();
        for(Message m: model.messages){
            String timeStamp = LocalDateTime.ofInstant(
                    Instant.ofEpochSecond(m.date),
                    ZoneId.systemDefault())
                    .toString();
            String userName = (m.from_id == model.id.get() ? model.userName.get() : "Me");

            items.add(0,new Item(userName, timeStamp, m.body));
        }

        listView.setItems(items);
        listView.scrollTo(listView.getItems().size());
        getChildren().add(listView);
    }

    private class Item extends GridPane{
        Label nameLbl = new Label();
        Label timeStampLbl = new Label();
        Label bodyLbl = new Label();

        {
            add(nameLbl, 1,1);
            add(timeStampLbl, 2, 2);

            bodyLbl.setMinWidth(270);
            bodyLbl.setMaxWidth(270);
            bodyLbl.setWrapText(true);
            add(bodyLbl, 1, 3);
        }

        Item(String name, String time, String body){
            nameLbl.setText(name);
            timeStampLbl.setText(time);
            bodyLbl.setText(body);
        }
    }
}
