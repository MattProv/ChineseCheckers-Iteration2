package org.example.message.clientHandlers;

import org.example.client.GUI.LobbyScreen;
import org.example.message.MessageHandler;
import org.example.message.MessageSenderPair;
import org.example.message.MessageType;
import org.example.message.UserlistMessage;

public class UserlistMessageHandler extends MessageHandler {
    private final LobbyScreen lobbyScreen;
    public UserlistMessageHandler(final LobbyScreen lobbyScreen) {
        super(MessageType.USERLIST);

        this.lobbyScreen = lobbyScreen;
    }

    @Override
    public void handle(final MessageSenderPair message) {
        UserlistMessage userlistMessage = (UserlistMessage) message.getMessage();

        lobbyScreen.setUsersList(userlistMessage.getMessage());
    }
}
