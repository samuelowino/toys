package com.owino;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import java.util.List;
import java.util.Random;
public class AbsView extends Pane {
    private final Circle leftEyeBall;
    private final Circle rightEyeBall;
    private final Arc mouth;
    public AbsView(){
        leftEyeBall = new Circle(4,Color.RED);
        rightEyeBall = new Circle(4,Color.RED);
        var leftEye = new Circle(24,Color.BLUE);
        var rightEye = new Circle(24,Color.BLUE);
        var head = new Circle(90, Color.ORANGE);
        mouth = new Arc(80, 80, 50, 30, 180, 180);
        mouth.setFill(Color.WHITE);
        head.setLayoutX(300);
        head.setLayoutY(100);
        leftEye.setLayoutX(260);
        leftEye.setLayoutY(85);
        leftEyeBall.setLayoutX(260);
        leftEyeBall.setLayoutY(85);
        rightEye.setLayoutX(340);
        rightEye.setLayoutY(85);
        rightEyeBall.setLayoutX(340);
        rightEyeBall.setLayoutY(85);
        mouth.setLayoutX(220);
        mouth.setLayoutY(60);
        getChildren().add(head);
        getChildren().add(leftEye);
        getChildren().add(rightEye);
        getChildren().add(leftEyeBall);
        getChildren().add(rightEyeBall);
        getChildren().add(mouth);
    }
    public Color getRandomColor(){
        var colors = List.of(
                Color.ORANGE,
                Color.RED,
                Color.WHITE,
                Color.BLACK,
                Color.PINK
        );
        var ranIndex = new Random().nextInt(0,colors.size());
        return colors.get(ranIndex);
    }
    public void animateEyes(){
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(800), e -> {
            var color = getRandomColor();
            var radius = new Random().nextDouble(4,12);
            var layoutY = new Random().nextInt(75,100);
            leftEyeBall.setFill(color);
            rightEyeBall.setFill(color);
            leftEyeBall.setRadius(radius);
            rightEyeBall.setRadius(radius);
            rightEyeBall.setLayoutY(layoutY);
            leftEyeBall.setLayoutY(layoutY);
            mouth.setRadiusX(new Random().nextDouble(10,50));
            mouth.setRadiusY(new Random().nextDouble(10,30));
            mouth.setStartAngle(0);
            mouth.setStartAngle(180);
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
}
