package org.example.client.GUI;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Objects;

// TODO: Add variants selecting
public class LobbyScreen extends BorderPane {

    public void setCallbacksHandler(CallbacksHandler callbacksHandler) {
        this.callbacksHandler = callbacksHandler;
    }

    public abstract static class CallbacksHandler {
        public abstract void onGameStart();
        public abstract void onChangePlayerCount(int playerCount);
        public abstract void onLeaveGame();
        public abstract void onError(String message);
    }

    private CallbacksHandler callbacksHandler;

    private final VBox usersList;
    private final Label serverMessage;
    public LobbyScreen() {
        super();

        //userlist
        usersList = new VBox();
        Label usersListLabel = new Label("Users");

        VBox usersListWrapper = new VBox(usersListLabel, new Separator(), usersList);
        usersListWrapper.setMinWidth(100);
        usersListWrapper.setPadding(new Insets(10, 10, 10, 10));

        //options
        GridPane options = new GridPane();
        options.setAlignment(Pos.CENTER);
        options.setHgap(10);
        options.setVgap(10);
        options.setPadding(new Insets(10, 10, 10, 10));

        Label playerCountLabel = new Label("Player count");
        TextField playerCountField = new TextField();
        playerCountField.setPromptText("Enter player count");
        Button updatePlayerCountButton = new Button("Update player count");
        updatePlayerCountButton.setOnAction(e -> {
            try {
                int playerCount = Integer.parseInt(playerCountField.getText());
                callbacksHandler.onChangePlayerCount(playerCount);
            } catch (NumberFormatException ex) {
                callbacksHandler.onError("Invalid player count");
            }
        });

        options.add(playerCountLabel, 0, 0);
        options.add(playerCountField, 1, 0);
        options.add(updatePlayerCountButton, 2, 0);

        Button startGameButton = new Button("Start game");
        startGameButton.setOnAction(e -> callbacksHandler.onGameStart());

        Button leaveGameButton = new Button("Leave game");
        leaveGameButton.setOnAction(e -> callbacksHandler.onLeaveGame());

        VBox optionsWrapper = new VBox(options, startGameButton, leaveGameButton);
        optionsWrapper.setAlignment(Pos.TOP_CENTER);

        //server message
        serverMessage = new Label();
        serverMessage.setPadding(new Insets(5, 5, 5, 5));

        //layout
        HBox layout = new HBox(usersListWrapper, optionsWrapper);

        getStylesheets().add(Objects.requireNonNull(getClass().getResource("/lobbystyles.css")).toExternalForm());
        setCenter(layout);
        setBottom(serverMessage);
    }

    public void setUsersList(String[] users) {
        Platform.runLater(() -> {
            usersList.getChildren().clear();
            for (String user : users) {
                usersList.getChildren().add(new Label(user));
            }
        });
    }

    public void showServerMessage(String message) {
        Platform.runLater(() ->serverMessage.setText(message));
    }
}
