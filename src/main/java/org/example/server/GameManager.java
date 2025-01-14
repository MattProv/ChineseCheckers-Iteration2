package org.example.server;

import org.example.game_logic.Agent;
import org.example.game_logic.Board;
import org.example.GameState;
import org.example.Player;
import org.example.game_logic.Move;
import org.example.game_logic.Rules;
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
    private List<Agent> agents = new ArrayList<>();
    private Rules ruleset;
    private final GameManagerCallbackHandler gameManagerCallbackHandler = new GameManagerCallbackHandler();

    private GameManager()
    {

    }

    public <T extends Rules> void setRuleset(T ruleset) {
        this.ruleset = ruleset;
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
        gameState.getBoard().defineBases();
        this.ruleset.assignBasesToAgents(gameState.getBoard(), agents);
        this.ruleset.setupBoard(gameState.getBoard(), agents);
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

    public boolean makeMove(final Player player, final Move move) {
        if (!gameState.isRunning())
            return false;
        if (this.ruleset.validateMove(gameState.getBoard(), move)) {
            gameState.getBoard().move(move);
            synchronizeBoard();
            return true;
        }
        return false;
    }

    public boolean makeMoveFromCoordinates(final Player player, String start, String end) {
        String[] parts = start.split(" ");
        String[] partsEnd = end.split(" ");
        int startX = Integer.parseInt(parts[0]);
        int startY = Integer.parseInt(parts[1]);
        int endX = Integer.parseInt(partsEnd[0]);
        int endY = Integer.parseInt(partsEnd[1]);
        Move move = new Move (gameState.getBoard().getNode(startX, startY), gameState.getBoard().getNode(endX, endY) );
        return makeMove(player, move);
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
