package com.owino;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import java.util.List;
import java.util.Random;
public class TilePaneView extends TilePane {
    public TilePaneView(){
        addEmojis();
    }
    public void addEmojis(){
        List<String> emojis = List.of(
                "😂", "😎", "😍", "🤔",
                "😡", "🥳", "😭", "😴",
                "🤩"
        );
        emojis.forEach(emoji -> applyAttributes(new Label(emoji)));
    }
    public void applyAttributes(Label label){
        label.setMinSize(100,100);
        label.setFont(new Font("",32));
        label.setAlignment(Pos.CENTER);
        label.setBackground(Background.fill(color()));
        getChildren().add(label);
        var timeline = new Timeline(new KeyFrame(Duration.millis(new Random().nextInt(500,3_000)), e -> {
            label.setBackground(Background.fill(color()));
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    public Color color(){
        var colors = List.of(
                Color.ORANGE,
                Color.RED,
                Color.GREEN,
                Color.PURPLE,
                Color.BLACK,
                Color.DARKGRAY,
                Color.PINK
        );
        var ranIndex = new Random().nextInt(0,colors.size());
        return colors.get(ranIndex);
    }
}
