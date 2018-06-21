package com.val.vk;

import com.val.vk.exceptions.EmptyTokenUrlException;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class Login {
    private static final String REDIRECT_URL = "https://oauth.vk.com/blank.html";
    private static final String VK_AUTH_URL = "https://oauth.vk.com/authorize?client_id=5490057&display=page&redirect_uri=https://oauth.vk.com/blank.html&scope=friends,messages,offline&response_type=token&v=5.52";
    private static String tokenUrl;
    private static final Stage stage = new Stage();


    public static String getTokenUrl() throws EmptyTokenUrlException{
        login();
        return tokenUrl;
    }

    private static void login(){
        final WebView view = new WebView();
        final WebEngine engine = view.getEngine();
        engine.load(VK_AUTH_URL);

        stage.setScene(new Scene(view));
        engine.locationProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.startsWith(REDIRECT_URL)){
                    tokenUrl = newValue;
                    stage.close();
                }
            }
        });

        stage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });
        stage.showAndWait();
    }
}
