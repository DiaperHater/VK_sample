package com.val.vk;


import com.val.vk.MVC.*;
import com.val.vk.exceptions.EmptyParamException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.swing.*;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main extends Application{

    private String securityToken = "";
    private String userId = "";
    private VkMethods vk;
    private DialogListModel dialogListModel;
    private DialogListView dialogListView;
    private OpenDialogModel openDialogModel;
    private OpenDialogView openDialogView;
    private WriteAndSendMessageWidget writeAndSendMessageWidget;
    private Scene mainScene;
    private Stage primaryStage;
    private CookieManager cookieManager = new CookieManager();

    {
        timeZoneFix();
        CookieHandler.setDefault(cookieManager);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        primaryStage.setOnCloseRequest(e->System.exit(0));

        initNewSession();
    }


    
    public static void main(String[] args) { launch(args); }

    private void timeZoneFix(){
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

    private void login() {
        cookieManager.getCookieStore().removeAll();
        String tokenUrl = Login.getTokenUrl();
        securityToken = extractToken(tokenUrl);
        userId = extractUserId(tokenUrl);
    }

    private void debugModeLoginDontNeed() {
        securityToken = "9bdcf2fa1612d472284d519d6473f7b8e98498362438b03ecf542f74461fe25c7e15c89ba8d311b5e94b8";
        userId = "204437607";
    }

    private String extractUserId(String tokenUrl) {
        Matcher matcher = Pattern.compile(
                "user_id=[^& ]*"
        ).matcher(tokenUrl);
        matcher.find();
        return matcher.group().replace("user_id=", "");
    }

    private String extractToken(String tokenUrl) {
        Matcher matcher = Pattern.compile(
                "access_token=[^&]*"
        ).matcher(tokenUrl);
        matcher.find();
        return matcher.group().replace("access_token=", "");

    }

    private void mvcInit() throws EmptyParamException {

        dialogListModel = new DialogListModel(vk);
        dialogListView = new DialogListView(dialogListModel);
        openDialogModel = new OpenDialogModel(vk);
        openDialogView = new OpenDialogView(openDialogModel);
        writeAndSendMessageWidget = new WriteAndSendMessageWidget();

        new Controller(vk,
                dialogListModel, dialogListView,
                openDialogModel, openDialogView,
                writeAndSendMessageWidget);

    }

    private Scene createAndSetupScene(){
        HBox root = new HBox();

        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(e->logout());
        VBox messageBox = new VBox(logoutButton,
                openDialogView,
                writeAndSendMessageWidget);

        root.getChildren().addAll(dialogListView, messageBox);
        return new Scene(root);
    }

    private void logout() {
        initNewSession();
    }

    private void initNewSession() {
        primaryStage.hide();

        login();


        vk = new VkMethods(new HttpsGetter(), securityToken, userId);
        try {
            mvcInit();
        } catch (EmptyParamException e) {
            e.printStackTrace();
            System.exit(1);
        }
        mainScene = createAndSetupScene();
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    private void showMessageDialog(String message) {
        JOptionPane.showMessageDialog(
                null,
                message,
                "val.inc",
                JOptionPane.PLAIN_MESSAGE
        );
    }


}
