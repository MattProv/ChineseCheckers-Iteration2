package org.example.message;

/**
 * Message class used to send the end of a turn message between the server and the client
 */
public class EndTurnMessage extends Message {
    public EndTurnMessage() {
        super(MessageType.END_TURN);
    }

    @Override
    public String toString() {
        return "END_TURN";
    }
}
