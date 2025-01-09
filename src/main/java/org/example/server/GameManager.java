package org.example.server;

import org.example.Board;
import org.example.GameState;
import org.example.Player;
import org.example.message.GameStateMessage;

import java.util.ArrayList;
import java.util.List;

public final class GameManager {
    private static GameManager instance = new GameManager();
    //SETTINGS
    private int playerCount = 2;

    //RUNTIME
    private final GameState gameState = new GameState();
    private final List<Player> players = new ArrayList<Player>();

    private final GameManagerCallbackHandler gameManagerCallbackHandler = new GameManagerCallbackHandler();

    private GameManager()
    {

    }

    public static GameManager create()
    {
        return instance = new GameManager();
    }

    public static GameManager getInstance()
    {
        return instance;
    }

    public boolean startGame(final List<ServerConnection> users) {
        if (gameState.isRunning()) {
            gameManagerCallbackHandler.onGameNotStarted("Game already running!");
            return false;
        }

        if (users.size() != playerCount) {
            String msg = "Game cannot be started, " + users.size() + " users connected out of " + playerCount + " required!";
            gameManagerCallbackHandler.onGameNotStarted(msg);
            return false;
        }

        if (gameState.getBoard() == null) {
            gameManagerCallbackHandler.onGameNotStarted("No board set!");
            return false;
        }

        gameState.getBoard().generateBoard();
        gameState.setRunning(true);

        synchronizeBoard();

        gameManagerCallbackHandler.onGameStarted();
        return true;
    }

    public boolean setBoard(final Board board) {
        if(gameState.isRunning())
            return false;
        gameState.setBoard(board);
        return true;
    }

    public boolean setPlayerCount(final int playerCount) {
        if(gameState.isRunning()) {
            gameManagerCallbackHandler.onPlayerCountNotChanged(playerCount, "Game already running!");
            return false;
        }
        if(playerCount == 5 || playerCount > 6 || playerCount < 2) {
            gameManagerCallbackHandler.onPlayerCountNotChanged(playerCount, "Invalid player count!");
            return false;
        }
        gameManagerCallbackHandler.onPlayerCountChanged(this.playerCount, playerCount);
        this.playerCount = playerCount;
        return true;
    }

    public boolean makeMove(final Player player, final String start, final String end)
    {
        if(!gameState.isRunning())
            return false;
        //if(currentPlayer != player)
        //    return false;
        gameState.getBoard().move(start, end);
        synchronizeBoard();
        return true;
    }

    public Player getPlayerByConnection(ServerConnection sc)
    {
        for(Player player : players)
        {
            if(player.getOwner() == sc)
                return player;
        }
        return null;
    }

    public void synchronizeBoard()
    {
        System.out.println("Synchronizing board.");
        GameStateMessage gsm = new GameStateMessage(gameState.clone());
        gsm.getGameState().getBoard().showBoard();
        Server.getServer().Broadcast(gsm);
    }
}
