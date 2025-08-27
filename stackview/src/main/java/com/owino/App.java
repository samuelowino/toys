package com.owino;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        var stackView = new StackView();
        var scene = new Scene(stackView,800,400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("StackPane View");
        primaryStage.setOnShown(e -> stackView.runColorLoop());
        primaryStage.show();
    }
}
