package org.example.server;

import org.example.message.Message;
import org.example.message.MessageHandler;
import org.example.message.MessageSenderPair;
import org.example.message.MessageType;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Class representing the server.
 * Singleton class.
 */
public final class Server {
    // Singleton instance
    private static Server instance;

    // List of message handlers
    private final List<MessageHandler> messageHandlers = new ArrayList<>();

    // Listener thread
    private Thread listenerThread;
    // Socket used by the server
    private ServerSocket serverSocket;

    // Message queue used to store messages and their senders
    private final Queue<MessageSenderPair> messageQueue = new LinkedList<>();

    private final List<ServerConnection> connections = new ArrayList<>();

    // Callbacks handler
    public ServerCallbacksHandler serverCallbacksHandler = new ServerCallbacksHandler();

    // Running flag
    private boolean running = false;

    private Server()
    {

    }

    /**
     * Creates the server.
     * @return The server.
     */
    public static Server create()
    {
        instance = new Server();

        return instance;
    }

    /**
     * Gets the server instance.
     * @return The server instance.
     */
    public static Server getServer()
    {
        return instance;
    }

    /**
     * Binds the server to a port.
     * @param port The port to bind to.
     */
    public void Bind(int port)
    {
        System.out.println("Binding at port " + port);
        try {
            serverSocket = new ServerSocket(port);

            System.out.println("Server bound");

            running = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Starts listening for incoming connections.
     */
    public void Listen()
    {
        if(serverSocket == null || !serverSocket.isBound())
            return;

        listenerThread = new Thread(() -> {
            while(running)
            {
                try {
                    ServerConnection sc = new ServerConnection(serverSocket.accept());
                    connections.add(sc);
                    serverCallbacksHandler.onNewConnection(sc);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        listenerThread.start();
    }

    /**
     * Disconnects a connection.
     * @param sc The connection to disconnect.
     */
    public void Disconnect(ServerConnection sc)
    {
        try {
            sc.getSocket().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        connections.remove(sc);
        serverCallbacksHandler.onConnectionClosed(sc);
    }

    /**
     * Shuts down the server.
     */
    public void Shutdown()
    {
        running = false;
        for(ServerConnection sc : connections)
        {
            Disconnect(sc);
        }
    }

    /**
     * Gets the message handlers of a specific type.
     * @param type The type of the message.
     * @return The list of message handlers.
     */
    public List<MessageHandler> getMessageHandlersOfType(final MessageType type) {
        List<MessageHandler> handlers = new ArrayList<>();
        for (MessageHandler messageHandler : messageHandlers) {
            if(messageHandler.getMessageType() == type) {
                handlers.add(messageHandler);
            }
        }

        return handlers;
    }

    /**
     * Sends a message to a connection.
     * @param message The message to send.
     * @param sc The connection to send the message to.
     */
    public void Send(final Message message, final ServerConnection sc)
    {
        try {
            sc.send(message);
            serverCallbacksHandler.onMessageSent(sc, message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends a message to a list of connections.
     * @param message The message to send.
     * @param recipients The list of connections to send the message to.
     */
    public void Send(final Message message, final List<ServerConnection> recipients)
    {
        for(ServerConnection sc : recipients)
        {
            Send(message, sc);
        }
    }

    /**
     * Broadcasts a message to all connections.
     * @param message The message to broadcast.
     */
    public void Broadcast(final Message message)
    {
        Send(message, connections);
    }

    /**
     * Adds a message handler.
     * @param handler The message handler to add.
     */
    public void AddHandler(final MessageHandler handler)
    {
        messageHandlers.add(handler);
    }

    /**
     * Handles the messages in the message queue.
     */
    public void HandleMessages()
    {
        while(!messageQueue.isEmpty())
        {
            MessageSenderPair messageSenderPair = messageQueue.poll();

            for(MessageHandler messageHandler : getMessageHandlersOfType(messageSenderPair.getMessageType()))
            {
                messageHandler.handle(messageSenderPair);
            }
        }
    }

    /**
     * Gets the connections.
     * @return The connections.
     * @see ServerConnection
     */
    public List<ServerConnection> getConnections() {
        return connections;
    }

    /**
     * Adds a message to the message queue.
     * @param message The message to add.
     * @param sc The connection that sent the message.
     */
    public void AddMessageToQueue(final Message message, final ServerConnection sc)
    {
        messageQueue.add(new MessageSenderPair(message, sc));
        serverCallbacksHandler.onMessageReceived(sc, message);
    }

    /**
     * Gets the message queue.
     * @return The message queue.
     */
    public boolean isRunning() {
        return running;
    }
}
