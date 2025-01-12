package org.example;

//generate default javafx window with helloword

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ClientMainGUI extends Application {
    @Override
    public void start(Stage primaryStage) {
        Label helloWorld = new Label("Hello, World!");
        StackPane root = new StackPane();
        root.getChildren().add(helloWorld);
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.setTitle("Hello World!");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
