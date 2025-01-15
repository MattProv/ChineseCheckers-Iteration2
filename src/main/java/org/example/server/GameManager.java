package org.example.server;

import org.example.GameState;
import org.example.game_logic.*;
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

    private Rules ruleset = new StandardRules();
    private final GameManagerCallbackHandler gameManagerCallbackHandler = new GameManagerCallbackHandler();

    private GameManager()
    {

    }

    public void setRuleset(Rules ruleset) {
        if(gameState.isRunning()) {
            gameManagerCallbackHandler.onRulesNotChanged("Game already running!");
            return;
        }
        this.ruleset = ruleset;
        gameManagerCallbackHandler.onRulesChanged("Rules changed!");
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

        if(ruleset == null) {
            gameManagerCallbackHandler.onGameNotStarted("No ruleset set!");
            return false;
        }

        currentTurn = 0;
        agents.clear();
        for (User user : lobby) {
            agents.add(new Player(user, agents.size()));
        }
        gameState.getBoard().generateBoard();
        gameState.getBoard().defineBases();
        gameState.getBoard().defineNeighbours();
        ruleset.assignBasesToAgents(gameState.getBoard(), agents);
        ruleset.setupBoard(gameState.getBoard(), agents);
        gameState.setRunning(true);

        agents.getFirst().promptMove(gameState.getBoard());

        synchronizeGameState();

        gameManagerCallbackHandler.onGameStarted();
        return true;
    }

    public boolean setBoard(final Board board) {
        if(gameState.isRunning()) {
            gameManagerCallbackHandler.onBoardNotChanged("Game already running!");
            return false;
        }
        gameState.setBoard(board);
        gameManagerCallbackHandler.onBoardChanged("Board set!");

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

    public boolean makeMove(final Agent agent, final Move move) {
        if (!gameState.isRunning()) {
            gameManagerCallbackHandler.onInvalidMove(agent, move, "Game not running!");
            return false;
        }

        if(agent != agents.get(currentTurn))
        {
            gameManagerCallbackHandler.onInvalidMove(agent, move, "Not your turn!");
            return false;
        }

        if (gameState.getBoard().getPawn(move.getStart()) == null) {
            gameManagerCallbackHandler.onInvalidMove(agent, move, "There's no pawn there to move!");
            return false;
        }

        if (gameState.getBoard().getPawn(move.getStart()).getOwner() != agent) {
            gameManagerCallbackHandler.onInvalidMove(agent, move, "You can't move another player's pawns!");
            return false;
        }
        if (ruleset.validateMove(gameState.getBoard(), move)) {
            gameState.getBoard().move(move);
            synchronizeGameState();
            gameManagerCallbackHandler.onValidMove(agent, move, "Valid move!");
            return true;
        }
        else
            System.out.println("Validation failed!");
        gameManagerCallbackHandler.onInvalidMove(agent, move, "Invalid move!");
        return false;
    }

    public Player getPlayerByConnection(ServerConnection sc)
    {
        for(Agent agent : agents)
        {
           if(agent.isPlayer())
           {
                Player player = (Player)agent;
                if(player.getOwner().getConnection() == sc)
                     return player;
           }
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

    public boolean makeMoveFromCoordinates(final Agent agent, Coordinate start, Coordinate end) {
        Move move = new Move (gameState.getBoard().getNode(start), gameState.getBoard().getNode(end) );
        return makeMove(agent, move);
    }


    public void endTurn(Agent agent) {
        if(agent != agents.get(currentTurn))
            return;
        agent.liftLocks();

        if(ruleset.checkWinCondition(agents.get(currentTurn)))
        {
            gameManagerCallbackHandler.onGameEnded(agents.get(currentTurn));
            return;
        }

        currentTurn = (currentTurn + 1) % agents.size();
        synchronizeGameState();

        agents.get(currentTurn).promptMove(gameState.getBoard());
    }
}
