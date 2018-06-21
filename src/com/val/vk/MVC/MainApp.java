package com.val.vk.MVC;

import com.val.vk.HttpsGetter;
import com.val.vk.Message;
import com.val.vk.VkMethods;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        timeZoneFix();

    }

    public static void timeZoneFix(){
        if(System.getProperty("os.name").equals("Windows XP")){
            int oneHourInMilliseconds = 3600000;
            int currentOffset = TimeZone.getDefault().getRawOffset();

            if (currentOffset >= 43200000){//+12 so next should be -12
                System.setProperty("user.timezone", "GMT-12");
                return;
            }
            int fixedOffset = (currentOffset + oneHourInMilliseconds) / oneHourInMilliseconds;
            String gmt = "GMT" + (fixedOffset < 0 ? "-":"+") + fixedOffset;
            System.setProperty("user.timezone", gmt);
            TimeZone.setDefault(null);
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}
