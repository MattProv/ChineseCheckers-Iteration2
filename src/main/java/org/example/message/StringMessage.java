package org.example.message;

/**
 * Message class used to send string messages between the server and the client
 */
public final class StringMessage extends Message {
    private final String message; // the message content

    public StringMessage(final String message)
    {
        super(MessageType.STRING);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String toString()
    {
        return message;
    }
}