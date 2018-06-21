package com.val.vk.MVC;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;



public class DialogListView extends VBox {
    private DialogListModel model;
    private FindField findField = new FindField();
    private ListView listView = new ListView();

    public DialogListView(DialogListModel model) {
        this.model = model;

        ObservableList<DialogListViewItem> items = FXCollections.observableArrayList();
        for(DialogWrapper d : model.currentDialogWrappers){
            items.add(new DialogListViewItem(d));
        }
        listView.setItems(items);
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);


        model.currentDialogWrappers.addListener(new ListChangeListener<DialogWrapper>() {
            @Override
            public void onChanged(Change<? extends DialogWrapper> c) {
                listView.getItems().clear();
                ObservableList<DialogListViewItem> items =
                        FXCollections.observableArrayList();

                for (DialogWrapper d : model.currentDialogWrappers){
                    items.add(new DialogListViewItem(d));
                }

                listView.setItems(items);
            }
        });

        getChildren().addAll(findField, listView);
    }

    public TextField getFindBoxField() {
        return findField.getField();
    }
    public Button getFindBoxClearButton() {
        return findField.getClearBtn();
    }
    public String findFieldVale() {
        return findField.getValue();
    }
    public ObservableList<DialogListViewItem> getItems() {
        return listView.getItems();
    }
    public ListView getListView() {
        return listView;
    }
    public void load(ObservableList<DialogListViewItem> found) {
        listView.setItems(found);
    }


    public DialogListViewItem getSelectedItem() {
        return (DialogListViewItem) listView.getSelectionModel().getSelectedItem();
    }

    public void clearFindField() {
        findField.getField().clear();
    }
}
