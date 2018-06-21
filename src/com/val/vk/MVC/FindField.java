package com.val.vk.MVC;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class FindField extends HBox {
    private final TextField field = new TextField();
    private final Button clearBtn = new Button("X");

    public FindField() {
        getChildren().addAll(field, clearBtn);
        setHgrow(field, Priority.ALWAYS);
    }

    public Button getClearBtn() {
        return clearBtn;
    }

    public TextField getField() {
        return field;
    }

    public String getText() {
        return field.getText();
    }

    public void clear() {
        field.clear();
    }
}
