package com.owino;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import java.util.List;
import java.util.Random;
public class StackView extends StackPane {
    private final Rectangle rectangle1;
    private final Rectangle rectangle2;
    private final Rectangle rectangle3;
    private final Circle circle;
    private final Circle circle2;
    public StackView(){
        rectangle1 = new Rectangle(750,350, Color.RED);
        rectangle2 = new Rectangle(700,300, Color.BLUE);
        rectangle3 = new Rectangle(500,250, Color.GREEN);
        circle = new Circle(100,Color.RED);
        circle2 = new Circle(60,Color.RED);
        getChildren().add(rectangle1);
        getChildren().add(rectangle2);
        getChildren().add(rectangle3);
        getChildren().add(circle);
        getChildren().add(circle2);
    }
    public Color getRandomColor(){
        var colors = List.of(
                Color.ORANGE,
                Color.RED,
                Color.GREEN,
                Color.BLACK,
                Color.GREEN,
                Color.PINK,
                Color.PURPLE,
                Color.BLUE
        );
        var ranIndex = new Random().nextInt(0,colors.size());
        return colors.get(ranIndex);
    }
    public void runColorLoop(){
        var timeline = new Timeline(new KeyFrame(Duration.millis(200), e -> {
            rectangle1.setFill(getRandomColor());
            rectangle2.setFill(getRandomColor());
            rectangle3.setFill(getRandomColor());
            circle.setFill(getRandomColor());
            circle2.setFill(getRandomColor());
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
}
