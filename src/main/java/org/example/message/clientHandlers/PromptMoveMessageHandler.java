package org.example.message.clientHandlers;

import org.example.client.GUI.GameScreen;
import org.example.message.MessageHandler;
import org.example.message.MessageSenderPair;
import org.example.message.MessageType;

/**
 * Message handler used to prompt the client to make a move
 */
public class PromptMoveMessageHandler extends MessageHandler {
    private final GameScreen gameScreen;
    public PromptMoveMessageHandler(GameScreen gameScreen) {
        super(MessageType.PROMPT_MOVE);

        this.gameScreen = gameScreen;
    }

    @Override
    public void handle(MessageSenderPair message) {
        gameScreen.promptMove();
    }
}
