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
        var gridContainerView = new GridContainerView();
        var scene = new Scene(gridContainerView,900,400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Grid View");
        primaryStage.show();
    }
}
