package com.owino;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        var customersView = new CustomersView();
        var scene = new Scene(customersView,800,500);
        stage.setScene(scene);
        stage.setTitle("VBox and HBox App");
        stage.setOnShown(e -> customersView.updateCustomersTable(CustomersView.customersList()));
        stage.show();
    }
}
