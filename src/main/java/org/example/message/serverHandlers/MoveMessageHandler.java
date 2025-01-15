package org.example.message.serverHandlers;

import org.example.message.MessageHandler;
import org.example.message.MessageSenderPair;
import org.example.message.MessageType;
import org.example.message.MoveMessage;
import org.example.server.GameManager;
import org.example.server.ServerConnection;

/**
 * Message handler that handles the move message and makes the move in the game
 */
public final class MoveMessageHandler extends MessageHandler {

    private final GameManager gameManager;

    public MoveMessageHandler(final GameManager gameManager)  {
        super(MessageType.MOVE);
        this.gameManager = gameManager;
    }

    @Override
    public void handle(final MessageSenderPair message) {
        MoveMessage moveMessage = (MoveMessage) message.getMessage();
        ServerConnection sc = message.getConnection();

        gameManager.makeMoveFromCoordinates(
                gameManager.getPlayerByConnection(sc),
                moveMessage.getStart(),
                moveMessage.getEnd()
        );
    }
}
