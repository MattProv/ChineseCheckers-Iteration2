package org.example.message;

import org.example.game_logic.Coordinate;

/**
 * Message class used to send move messages between the server and the client
 */
public class MoveMessage extends Message {
    // the start and end coordinates of the move
    private final Coordinate start;
    private final Coordinate end;

    public MoveMessage(Coordinate start, Coordinate end) {
        super(MessageType.MOVE);
        this.start = start;
        this.end = end;
    }

    public String toString()
    {
        return start.toString() + " " + end.toString();
    }

    public Coordinate getStart() {
        return start;
    }

    public Coordinate getEnd() {
        return end;
    }
}
