package org.example.message;

/**
 * Message class used to prompt the client to make a move
 */
public class PromptMoveMessage extends Message {
    public PromptMoveMessage() {
        super(MessageType.PROMPT_MOVE);
    }

    @Override
    public String toString() {
        return "PromptMoveMessage{}";
    }
}
