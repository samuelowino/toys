package com.kenyajug;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.util.Random;
public class TransformApp extends Application {
    private Parent content(){
        var rectangle = new Rectangle(100,50,Color.BLUE);
        transform(rectangle);
        return new Pane(rectangle);
    }
    private void transform(Rectangle rectangle) {
        rectangle.setTranslateX(new Random().nextInt(80,150));
        rectangle.setTranslateY(new Random().nextInt(150,200));
        rectangle.setScaleX(new Random().nextDouble(1.0,1.5));
        rectangle.setScaleY(new Random().nextDouble(1.0,1.5));
        rectangle.setRotate(new Random().nextInt(20,180));
    }
    @Override
    public void start(Stage stage) throws Exception {
        var scene = new Scene(content(),300,300, Color.GRAY);
        stage.setScene(scene);
        stage.setTitle("Transformer");
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
