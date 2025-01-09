package org.example.message;

import java.util.Arrays;

public class MoveMessage extends Message {
    private final String[] message; // the message content

    public MoveMessage(final String start, final String end)
    {
        super(MessageType.MOVE);
        this.message = new String[]{start, end};
    }


    public String[] getMessage()
    {
        return this.message;
    }

    public String toString()
    {
        return Arrays.toString(message);
    }

    public String getStart() {
        return message[0];
    }

    public String getEnd() {
        return message[1];
    }
}
