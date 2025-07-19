package com.kenyajug;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
public class MainApp extends Application {
	@Override
	public void start(Stage stage) throws Exception {
		var parent = new StackPane(new Text("Hello World 🌎"));
		var scene = new Scene(parent,500,500);
		stage.setScene(scene);
		stage.show();
	}
	public static void main(String[] args) {
		launch(args);
	}
}
