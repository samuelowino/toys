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
        var tilePaneView = new AnchorView();
        var scene = new Scene(tilePaneView,1300,1000);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Tile Pane View");
        primaryStage.show();
    }
}
