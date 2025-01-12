package org.example;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.example.client.Client;
import org.example.client.ClientCallbacksHandler;
import org.example.client.GUI.LobbyScreen;
import org.example.client.GUI.LoginScreen;
import org.example.message.CommandMessage;
import org.example.message.Commands;
import org.example.message.clientHandlers.GameStateMessageHandler;
import org.example.message.clientHandlers.StringMessageGUIHandler;
import org.example.message.clientHandlers.UserlistMessageHandler;

import java.util.Objects;

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
        GameState gameState = new GameState();

        primaryStage.setMinHeight(400);
        primaryStage.setMinWidth(400);

        LoginScreen loginScreen = new LoginScreen();
        Scene loginScene = new Scene(loginScreen, 400, 400);

        LobbyScreen lobbyScreen = new LobbyScreen();
        Scene secondScreen = new Scene(lobbyScreen, 400, 400);

        loginScreen.setCallbacksHandler(new LoginScreen.CallbacksHandler() {
            @Override
            public void onConnect(String username, String host, int port) {
                if (client.Connect(host, port)) {
                    client.send(new org.example.message.UsernameMessage(username));
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

        lobbyScreen.setCallbacksHandler(new LobbyScreen.CallbacksHandler() {
            @Override
            public void onGameStart() {
                client.send(new CommandMessage(Commands.START_GAME, new String[0]));
            }

            @Override
            public void onChangePlayerCount(int playerCount) {
                client.send(new CommandMessage(Commands.SET_PLAYER_COUNT, new String[]{ "", Integer.toString(playerCount)}));
            }

            @Override
            public void onLeaveGame() {
                client.Disconnect();
            }

            @Override
            public void onError(String message) {
                Platform.runLater(() -> showError(message));
            }
        });

        client.clientCallbacksHandler = new ClientCallbacksHandler() {
            @Override
            public void onDisconnect() {
                System.out.println("Disconnected");
                Platform.runLater(() -> primaryStage.setScene(loginScene));
            }

            @Override
            public void onSocketError() {
                System.out.println("Error: Received a null message.");
                Platform.runLater(() -> {
                    showError("Disconnected from the server.");
                    primaryStage.setScene(loginScene);
                });
            }
        };
        client.AddHandler(new GameStateMessageHandler(gameState));
        client.AddHandler(new UserlistMessageHandler(lobbyScreen));
        client.AddHandler(new StringMessageGUIHandler(lobbyScreen));

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                client.Disconnect();

                Platform.exit();
                System.exit(0);
            }
        });

        primaryStage.setScene(loginScene);
        primaryStage.setTitle("Chinese Checkers!");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
