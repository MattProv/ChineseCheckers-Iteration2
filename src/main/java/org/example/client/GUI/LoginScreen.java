package org.example.client.GUI;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.example.Config;

public class LoginScreen extends BorderPane {

    public abstract static class CallbacksHandler {
        public void onConnect(String username, String ip, int port)
        {
            System.out.println("Username: " + username);
            System.out.println("IP: " + ip);
            System.out.println("Port: " + port);
        }

        public boolean validateUsername(String username)
        {
            return !username.isEmpty();
        }

        public boolean validateIP(String ip)
        {
            return !ip.isEmpty();
        }

        public void onError(String message)
        {
            System.out.println("Error: " + message);
        }
    }

    private CallbacksHandler callbacksHandler;

    public LoginScreen() {
        super();

        VBox centerColumn = new VBox();

        Label helloWorld = new Label("Hello, World!");

        TextField usernameTextField = new TextField();
        usernameTextField.setPromptText("Enter your username");

        TextField ipTextField = new TextField();
        ipTextField.setPromptText("Enter server IP");

        TextField portTextField = new TextField();
        portTextField.setPromptText("Enter server port");
        portTextField.setText(Integer.toString(Config.PORT));

        Button connectButton = new Button("Connect");
        connectButton.setOnAction(_ -> {
            String username = usernameTextField.getText();
            String ip = ipTextField.getText();
            String portString = portTextField.getText();

            int port;

            try
            {
                port = Integer.parseInt(portString);
                if (port < 0 || port > 65535)
                {
                    callbacksHandler.onError("Invalid port number. Please enter a number between 0 and 65535.");
                    return;
                }
            }
            catch (NumberFormatException ex)
            {
                callbacksHandler.onError("Invalid port number. Please enter a number between 0 and 65535.");
                return;
            }

            if(!callbacksHandler.validateIP(ip))
            {
                callbacksHandler.onError("Invalid IP. Please enter a valid IP.");
                return;
            }

            if(!callbacksHandler.validateUsername(username))
            {
                callbacksHandler.onError("Invalid username. Please enter a valid username.");
                return;
            }

            callbacksHandler.onConnect(username, ip, port);
        });

        centerColumn.getChildren().addAll(helloWorld, usernameTextField, ipTextField, portTextField, connectButton);

        setCenter(centerColumn);
    }

    public void setCallbacksHandler(CallbacksHandler callbacksHandler)
    {
        this.callbacksHandler = callbacksHandler;
    }
}
