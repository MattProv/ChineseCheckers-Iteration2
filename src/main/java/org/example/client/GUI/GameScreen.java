package org.example.client.GUI;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.example.GameState;

import java.util.Objects;

public class GameScreen extends BorderPane {

    private final GameState gameState;

    private final VBox usersList;
    private final VBox playersList;

    private final Label serverMessage;

    private final BoardPane boardPane;

    public GameScreen(GameState gameState) {
        super();
        this.gameState = gameState;

        //userlist
        usersList = new VBox();
        Label usersListLabel = new Label("Users");

        VBox usersListWrapper = new VBox(usersListLabel, new Separator(), usersList);
        usersListWrapper.setMinWidth(100);
        usersListWrapper.setPadding(new Insets(10, 10, 10, 10));

        //playerslist
        playersList = new VBox();
        Label playersListLabel = new Label("Players");

        VBox playersListWrapper = new VBox(playersListLabel, new Separator(), playersList);
        playersListWrapper.setMinWidth(100);
        playersListWrapper.setPadding(new Insets(10, 10, 10, 10));

        //leftside
        VBox leftSide = new VBox(playersListWrapper, usersListWrapper);
        setLeft(leftSide);

        //center
        boardPane = new BoardPane();
        boardPane.setPadding(new Insets(10, 10, 10, 10));
        setCenter(boardPane);

        //server message
        serverMessage = new Label();
        serverMessage.setPadding(new Insets(5, 5, 5, 5));
        setBottom(serverMessage);

        Button btn = new Button("Refresh");
        btn.setOnAction(e -> {
            System.out.println("Refresh button clicked");
            updateBoard();
        });
        setBottom(btn);

        widthProperty().addListener(observable ->
        {
            updateBoard();
        });

        heightProperty().addListener(observable ->
        {
            updateBoard();
        });

        getStylesheets().add(Objects.requireNonNull(getClass().getResource("/gamestyles.css")).toExternalForm());
    }

    public void showServerMessage(String message) {
        Platform.runLater(() ->serverMessage.setText(message));
    }

    public void updateBoard() {
        if(gameState.getBoard() == null) {
            return;
        }
        boardPane.updateBoard(gameState.getBoard());
    }

    public void updatePlayerList(String[] players, int turn) {
        Platform.runLater(() -> {
            playersList.getChildren().clear();
            for (String player : players) {
                Label playerLabel = new Label(player);
                if (players[turn].equals(player)) {
                    playerLabel.setStyle("-fx-font-weight: bold");
                }
                playersList.getChildren().add(playerLabel);
            }
        });
    }

    public void updateAllUsers(String[] allUsers) {
        Platform.runLater(() -> {
            usersList.getChildren().clear();
            for (String user : allUsers) {
                usersList.getChildren().add(new Label(user));
            }
        });
    }
}
