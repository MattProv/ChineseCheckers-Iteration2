package org.example.server;

import org.example.game_logic.Agent;
import org.example.game_logic.Board;
import org.example.GameState;
import org.example.Player;
import org.example.game_logic.Rules;
import org.example.message.GameStateMessage;
import org.example.message.UserlistMessage;

import java.util.ArrayList;
import java.util.List;

public final class GameManager {
    private static GameManager instance = new GameManager();
    //LOBBY
    private final List<User> lobby = new ArrayList<User>();
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

    public void synchronizeUsers()
    {
        System.out.println("Synchronizing users.");
        List<String> usernames = new ArrayList<String>();
        for(User user : lobby)
        {
            usernames.add(user.getUsername());
        }
        String[] usernamesArray = new String[usernames.size()];
        usernamesArray = usernames.toArray(usernamesArray);
        Server.getServer().Broadcast(new UserlistMessage(usernamesArray));
    }

    public void addUser(final User user)
    {
        lobby.add(user);
    }

    public void removeUser(ServerConnection sc)
    {
        for(User user : lobby)
        {
            if(user.getConnection() == sc)
            {
                lobby.remove(user);
                return;
            }
        }
    }
}
