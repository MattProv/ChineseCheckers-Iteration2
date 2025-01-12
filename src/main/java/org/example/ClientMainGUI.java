package org.example;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.example.client.Client;
import org.example.client.GUI.LoginScreen;

public class ClientMainGUI extends Application {
    void showError(String message) {
        Dialog<String> alertDialog = new Dialog<>();

        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        alertDialog.getDialogPane().getButtonTypes().add(okButton);

        alertDialog.setTitle("Information");
        alertDialog.setContentText(message);
        alertDialog.showAndWait();
    }

    @Override
    public void start(Stage primaryStage) {

        Client client = Client.create();

        LoginScreen loginScreen = new LoginScreen();
        Scene secondScreen = new Scene(new BorderPane(), 300, 250);

        loginScreen.setCallbacksHandler(new LoginScreen.CallbacksHandler() {
            @Override
            public void onConnect(String username, String host, int port) {
                if (client.Connect(host, port)) {
                    primaryStage.setScene(secondScreen);
                } else {
                    showError("Failed to connect to the server.");
                }
            }

            @Override
            public void onError(String message)
            {
                Platform.runLater(() -> showError(message));
            }
        });


        Scene scene = new Scene(loginScreen, 300, 250);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Chinese Checkers!");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
