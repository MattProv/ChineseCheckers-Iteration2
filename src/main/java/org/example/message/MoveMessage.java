package org.example.message;

import org.example.game_logic.Move;
import org.example.game_logic.Node;
import org.example.game_logic.Pawn;

import java.util.Arrays;

public class MoveMessage extends Message {
    private final String message; // the message content

    public MoveMessage(String startX, String startY, String endX, String endY)
    {
        super(MessageType.MOVE);
        this.message = startX + " " + startY + " " + endX + " " + endY;
    }


    public String getMessage()
    {
        return this.message;
    }

    public String toString()
    {
        return Arrays.toString(new String[] {this.message.toString()});
    }

    public String getStart() {
        return message.split(" ")[0] + " " + message.split(" ")[1];
    }

    public String getEnd() {
        return message.split(" ")[2] + " " + message.split(" ")[3];
    }
}
