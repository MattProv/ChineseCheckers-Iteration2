package org.example.client.GUI;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.example.GameState;
import org.example.game_logic.Coordinate;

import java.util.Objects;

/**
 * The main game screen
 */
public class GameScreen extends BorderPane {

    /**
     * Prompt the user to make a move
     */
    public void promptMove() {
        Platform.runLater(() -> enableEndTurnButton(true));
    }

    /**
     * Callbacks handler for the game screen
     */
    public static abstract class CallbacksHandler {
        public abstract void onQuit();
        public abstract void onEndTurn();
        public abstract void onMove(Coordinate start, Coordinate end);
    }

    // CALLBACKS HANDLER
    private CallbacksHandler callbacksHandler;

    // GAME STATE
    private final GameState gameState;

    // GUI TO CHANGE
    // VBOXES (USERS LIST, PLAYERS LIST)
    private final VBox usersList;
    private final VBox playersList;

    // LABELS
    private final Label serverMessage;

    // BOARD PANE
    private final BoardPane boardPane;

    //BUTTONS
    Button moveButton;
    Button endTurnButton;

    //LAST MOVE COORDINATES
    private Coordinate start;
    private Coordinate end;

    /**
     * Generate the game screen
     * @param gameState the game state
     */
    public GameScreen(GameState gameState) {
        super();
        this.gameState = gameState;

        //user list
        usersList = new VBox();
        Label usersListLabel = new Label("Users");

        VBox usersListWrapper = new VBox(usersListLabel, new Separator(), usersList);
        usersListWrapper.setMinWidth(100);
        usersListWrapper.setPadding(new Insets(10, 10, 10, 10));

        //players list
        playersList = new VBox();
        Label playersListLabel = new Label("Players");

        VBox playersListWrapper = new VBox(playersListLabel, new Separator(), playersList);
        playersListWrapper.setMinWidth(100);
        playersListWrapper.setPadding(new Insets(10, 10, 10, 10));

        //left side
        VBox leftSide = new VBox(playersListWrapper, usersListWrapper);
        setLeft(leftSide);

        //center
        boardPane = new BoardPane();
        boardPane.setPadding(new Insets(10, 10, 10, 10));
        boardPane.setCallbacksHandler(new BoardPane.CallbacksHandler() {
            @Override
            public void onMoveSelected(Coordinate start, Coordinate end) {
                GameScreen.this.start = start;
                GameScreen.this.end = end;

                enableMoveButton(true);
            }

            @Override
            public void onMoveCancelled() {
                enableMoveButton(false);
            }
        });
        setCenter(boardPane);

        //server message
        serverMessage = new Label();
        serverMessage.setPadding(new Insets(5, 5, 5, 5));

        GridPane buttons = getButtons();

        setBottom(new VBox(buttons, serverMessage));

        widthProperty().addListener(Observable -> updateBoard());

        heightProperty().addListener(Observable -> updateBoard());

        getStylesheets().add(Objects.requireNonNull(getClass().getResource("/gamestyles.css")).toExternalForm());
    }

    /**
     * Generate the buttons
     * @return the buttons
     */
    private GridPane getButtons() {
        Button redrawButton = new Button("Redraw");
        redrawButton.setOnAction(ActionEvent -> updateBoard());

        moveButton = new Button("Move");
        moveButton.setDisable(true);
        moveButton.setOnAction(ActionEvent -> {
            callbacksHandler.onMove(start, end);
            boardPane.cancelMove();
        });

        endTurnButton = new Button("End turn");
        endTurnButton.setDisable(true);
        endTurnButton.setOnAction(ActionEvent -> callbacksHandler.onEndTurn());

        Button quitButton = new Button("Quit");
        quitButton.setOnAction(ActionEvent -> callbacksHandler.onQuit());

        GridPane buttons = new GridPane();
        buttons.add(redrawButton, 0, 0);
        buttons.add(moveButton, 1, 0);
        buttons.add(endTurnButton, 2, 0);
        buttons.add(quitButton, 3, 0);

        buttons.setAlignment(javafx.geometry.Pos.CENTER);
        buttons.setHgap(10);
        buttons.setPadding(new Insets(10, 10, 10, 10));

        return buttons;
    }

    /**
     * Enable the move button
     * @param enable if the button should be enabled
     */
    public void enableMoveButton(boolean enable) {
        moveButton.setDisable(!enable);
    }

    /**
     * Enable the end turn button
     * @param enable if the button should be enabled
     */
    public void enableEndTurnButton(boolean enable) {
        endTurnButton.setDisable(!enable);
    }

    /**
     * Show a message from the server
     * @param message the message
     */
    public void showServerMessage(String message) {
        Platform.runLater(() -> serverMessage.setText(message));
    }

    /**
     * Redraw the board
     */
    public void updateBoard() {
        if(gameState.getBoard() == null) {
            return;
        }
        boardPane.updateBoard(gameState.getBoard());
    }

    /**
     * Update the player list
     * @param players the players
     * @param turn the turn, to bold the current player
     */
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

    /**
     * Update the user list
     * @param allUsers the users
     */
    public void updateAllUsers(String[] allUsers) {
        Platform.runLater(() -> {
            usersList.getChildren().clear();
            for (String user : allUsers) {
                usersList.getChildren().add(new Label(user));
            }
        });
    }

    /**
     * Set the callbacks handler
     * @param callbacksHandler the callbacks handler
     */
    public void setCallbacksHandler(CallbacksHandler callbacksHandler) {
        this.callbacksHandler = callbacksHandler;
    }
}
