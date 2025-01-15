package org.example.client.GUI;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.example.game_logic.BoardType;
import org.example.game_logic.RulesType;

import java.util.Arrays;
import java.util.Objects;

/**
 * Lobby screen used to display the lobby and game options
 */
public class LobbyScreen extends BorderPane {

    /**
     * Callbacks handler used to handle the user input
     */
    public void setCallbacksHandler(CallbacksHandler callbacksHandler) {
        this.callbacksHandler = callbacksHandler;
    }

    /**
     * Callbacks handler used to handle the user input
     */
    public abstract static class CallbacksHandler {
        public abstract void onGameStart();
        public abstract void onChangePlayerCount(int playerCount);
        public abstract void onLeaveGame();
        public abstract void onError(String message);
        public abstract void onChangeBoardType(BoardType boardType);
        public abstract void onChangeRulesType(RulesType rulesType);
    }

    // Callbacks handler used to handle the user input
    private CallbacksHandler callbacksHandler;

    // GUI elements used to display the lobby
    private final VBox usersList;
    private final Label serverMessage;

    /**
     * Generates the lobby screen
     */
    public LobbyScreen() {
        super();

        // generate the userlist
        usersList = new VBox();
        Label usersListLabel = new Label("Users");

        VBox usersListWrapper = new VBox(usersListLabel, new Separator(), usersList);
        usersListWrapper.setMinWidth(100);
        usersListWrapper.setPadding(new Insets(10, 10, 10, 10));

        // SETTINGS
        // generate the options
        GridPane options = new GridPane();
        options.setAlignment(Pos.CENTER);
        options.setHgap(10);
        options.setVgap(10);
        options.setPadding(new Insets(10, 10, 10, 10));

        // player count

        Label playerCountLabel = new Label("Player count");
        TextField playerCountField = new TextField();
        playerCountField.setPromptText("Enter player count");
        Button updatePlayerCountButton = new Button("Update");
        updatePlayerCountButton.setOnAction(e -> {
            try {
                int playerCount = Integer.parseInt(playerCountField.getText());
                callbacksHandler.onChangePlayerCount(playerCount);
            } catch (NumberFormatException ex) {
                callbacksHandler.onError("Invalid player count");
            }
        });

        // board type

        Label boardTypeLabel = new Label("Board type");
        ObservableList<String> boardTypes = FXCollections.observableArrayList(Arrays.stream(BoardType.values()).map(Enum::name).toArray(String[]::new));
        ComboBox<String> boardTypeComboBox = new ComboBox<>(boardTypes);
        boardTypeComboBox.setValue(BoardType.STANDARD.name());
        Button updateBoardTypeButton = new Button("Update");
        updateBoardTypeButton.setOnAction(e -> {
            try {
                BoardType boardType = BoardType.valueOf(boardTypeComboBox.getValue());
                callbacksHandler.onChangeBoardType(boardType);
            } catch (IllegalArgumentException ex) {
                callbacksHandler.onError("Invalid board type");
            }
        });

        // rules type

        Label rulesTypeLabel = new Label("Rules type");
        ObservableList<String> rulesTypes = FXCollections.observableArrayList(Arrays.stream(RulesType.values()).map(Enum::name).toArray(String[]::new));
        ComboBox<String> rulesTypeComboBox = new ComboBox<>(rulesTypes);
        rulesTypeComboBox.setValue(RulesType.STANDARD.name());
        Button updateRulesTypeButton = new Button("Update");
        updateRulesTypeButton.setOnAction(e -> {
            try {
                RulesType rulesType = RulesType.valueOf(rulesTypeComboBox.getValue());
                callbacksHandler.onChangeRulesType(rulesType);
            } catch (IllegalArgumentException ex) {
                callbacksHandler.onError("Invalid rules type");
            }
        });

        // add elements to the grid

        options.add(playerCountLabel, 0, 0);
        options.add(playerCountField, 1, 0);
        options.add(updatePlayerCountButton, 2, 0);

        options.add(boardTypeLabel, 0, 1);
        options.add(boardTypeComboBox, 1, 1);
        options.add(updateBoardTypeButton, 2, 1);

        options.add(rulesTypeLabel, 0, 2);
        options.add(rulesTypeComboBox, 1, 2);
        options.add(updateRulesTypeButton, 2, 2);

        // buttons

        Button startGameButton = new Button("Start game");
        startGameButton.setOnAction(e -> callbacksHandler.onGameStart());

        Button leaveGameButton = new Button("Leave game");
        leaveGameButton.setOnAction(e -> callbacksHandler.onLeaveGame());

        VBox optionsWrapper = new VBox(options, startGameButton, leaveGameButton);
        optionsWrapper.setAlignment(Pos.TOP_CENTER);
        optionsWrapper.setSpacing(10);

        //server message
        serverMessage = new Label();
        serverMessage.setPadding(new Insets(5, 5, 5, 5));

        //layout
        HBox layout = new HBox(usersListWrapper, optionsWrapper);

        getStylesheets().add(Objects.requireNonNull(getClass().getResource("/lobbystyles.css")).toExternalForm());
        setCenter(layout);
        setBottom(serverMessage);
    }

    /**
     * Sets the users list
     * @param users the users list
     */
    public void setUsersList(String[] users) {
        Platform.runLater(() -> {
            usersList.getChildren().clear();
            for (String user : users) {
                usersList.getChildren().add(new Label(user));
            }
        });
    }

    /**
     * Sets the server message
     * @param message the message
     */
    public void showServerMessage(String message) {
        Platform.runLater(() ->serverMessage.setText(message));
    }
}
