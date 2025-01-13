package org.example.server;

import org.example.GameState;
import org.example.Player;
import org.example.game_logic.Agent;
import org.example.game_logic.Board;
import org.example.game_logic.Rules;
import org.example.message.GameStateMessage;
import org.example.message.UserlistMessage;

import java.util.ArrayList;
import java.util.List;

public final class GameManager {
    private static GameManager instance = new GameManager();
    //LOBBY
    private final List<User> lobby = new ArrayList<>();
    //SETTINGS
    private int playerCount = 2;

    //RUNTIME
    private final GameState gameState = new GameState();
    private List<Agent> agents = new ArrayList<>();
    private int currentTurn = 0;

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

    public void startGame(final List<ServerConnection> users) {
        if (gameState.isRunning()) {
            gameManagerCallbackHandler.onGameNotStarted("Game already running!");
            return;
        }

        if (users.size() != playerCount) {
            String msg = "Game cannot be started, " + users.size() + " users connected out of " + playerCount + " required!";
            gameManagerCallbackHandler.onGameNotStarted(msg);
            return;
        }

        if (gameState.getBoard() == null) {
            gameManagerCallbackHandler.onGameNotStarted("No board set!");
            return;
        }

        gameState.getBoard().generateBoard();
        gameState.getBoard().defineBases();
        this.ruleset.assignBasesToAgents(gameState.getBoard(), agents);
        gameState.setRunning(true);

        synchronizeGameState();

        gameManagerCallbackHandler.onGameStarted();
    }

    public void setBoard(final Board board) {
        if(gameState.isRunning())
            return;
        gameState.setBoard(board);
    }

    public void setPlayerCount(final int playerCount) {
        if(gameState.isRunning()) {
            gameManagerCallbackHandler.onPlayerCountNotChanged(playerCount, "Game already running!");
            return;
        }
        if(playerCount == 5 || playerCount > 6 || playerCount < 2) {
            gameManagerCallbackHandler.onPlayerCountNotChanged(playerCount, "Invalid player count!");
            return;
        }
        gameManagerCallbackHandler.onPlayerCountChanged(this.playerCount, playerCount);
        this.playerCount = playerCount;
    }

    public boolean makeMove(final Agent agent, final String start, final String end)
    {
        if(!gameState.isRunning())
            return false;
        if(agents.get(currentTurn) != agent)
            return false;
        gameState.getBoard().move(start, end);
        synchronizeGameState();
        return true;
    }

    public Player getPlayerByConnection(ServerConnection sc)
    {
        for(Agent agent : agents)
        {
           if(agent.isPlayer())
               return (Player) agent;
        }
        return null;
    }
    public User getUserByConnection(ServerConnection sc)
    {
        for(User user : lobby)
        {
            if(user.getConnection() == sc)
                return user;
        }
        return null;
    }

    public void synchronizeGameState()
    {
        System.out.println("Synchronizing game state.");
        List<String> playerNames = new ArrayList<String>();
        for(Agent agent : agents)
        {
            playerNames.add(agent.isPlayer()?((Player)agent).getOwner().getUsername():"AI");
        }
        String[] playerNamesArray = new String[playerNames.size()];
        playerNamesArray = playerNames.toArray(playerNamesArray);

        GameStateMessage gsm = new GameStateMessage(gameState.clone(), playerNamesArray, currentTurn);
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
        User user = getUserByConnection(sc);
        if(user != null)
            lobby.remove(user);
    }
}
