package com.val.vk.MVC;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.VBox;



public class DialogListView extends VBox {
    private DialogListModel model;
    private FindField findField = new FindField();
    private ListView listView = new ListView();

    public DialogListView(DialogListModel model) {
        this.model = model;
        addModelListener();

        findFieldSetup();
        listViewSetup();

        getChildren().addAll(findField, listView);
    }

    private void addModelListener() {
        model.currentDialogWrappers.addListener(new ListChangeListener<DialogWrapper>() {
            @Override
            public void onChanged(Change<? extends DialogWrapper> c) {
                listView.getItems().clear();
                ObservableList<DialogListViewItem> items =
                        FXCollections.observableArrayList();

                for (DialogWrapper d : model.currentDialogWrappers){

                    DialogListViewItem singleItem = new DialogListViewItem(d);
                    items.add(singleItem);

                    d.bodyProperty.addListener((observable, oldValue, newValue) -> {
                        if(!oldValue.equals(newValue)){
                            singleItem.setBody(newValue);
                        }
                    });
                }

                listView.setItems(items);
            }
        });
    }

    private void findFieldSetup() {
        findField.getField().setOnKeyReleased(this::onFindAction);
        findField.getClearBtn().setOnAction(this::onClearButtonPressedAction);
    }

    private void listViewSetup() {
        ObservableList<DialogListViewItem> items = FXCollections.observableArrayList();
        for(DialogWrapper d : model.currentDialogWrappers){
            DialogListViewItem singleItem = new DialogListViewItem(d);
            items.add(singleItem);

            d.bodyProperty.addListener((observable, oldValue, newValue) -> {
                if(!oldValue.equals(newValue)){
                    singleItem.setBody(newValue);
                    moveItemOnTopOfTheListView(singleItem);
                }
            });
        }
        listView.setItems(items);

        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    private void moveItemOnTopOfTheListView(DialogListViewItem item) {
        int index = listView.getItems().indexOf(item);
        listView.getItems().remove(index);
        listView.getItems().add(0,item);
        listView.getSelectionModel().selectFirst();
    }

    private void onClearButtonPressedAction(ActionEvent event) {
        model.reset();
        findField.clear();
    }

    private void onFindAction(Event event) {
        model.findInUserName(findField.getText());
    }

    public ListView getListView() {
        return listView;
    }

    public DialogListViewItem getSelectedItem() {
        return (DialogListViewItem) listView.getSelectionModel().getSelectedItem();
    }

    public int getUserIdOfTopItem() {
        return ((DialogListViewItem)listView.getItems().get(0)).getUserId();
    }

    public String getUserNameOfTopItem() {
        return model.currentDialogWrappers.get(0).user;
    }
}
