package com.owino;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        var absoluteView = new AbsView();
        var scene = new Scene(absoluteView, 800,500);
        scene.setFill(Color.BLACK);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Absolute Positioning");
        primaryStage.setOnShown(e -> absoluteView.animateEyes());
        primaryStage.show();
    }
}
