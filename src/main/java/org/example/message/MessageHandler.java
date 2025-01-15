package org.example.message;

/**
 * Abstract class used to handle messages between the server and the client of specific types
 */
public abstract class MessageHandler
{
    // The type of the message to handle
    private final MessageType messageType;

    public MessageHandler(final MessageType messageType)
    {
        this.messageType = messageType;
    }

    /**
     * Method to get the message type
     * @return The message type
     */
    public MessageType getMessageType()
    {
        return messageType;
    }

    /**
     * Method to handle the message
     * @param message The message to handle
     */
    public abstract void handle(MessageSenderPair message);
}