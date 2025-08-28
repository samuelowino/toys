package com.owino;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;

public class AnchorView extends AnchorPane {
    public AnchorView(){
        var appTitleLabel = new Label("Anchor App Dashboard");
        var signOutLink = new Hyperlink("Sign out");
        var connectedLabel = new Label("Connected");
        var circleIndicator = new Circle(8d);
        var programStatusLabel = new Label("Program Status");
        var textArea = new TextArea();
        appTitleLabel.setFont(new Font("",17));
        circleIndicator.setFill(Color.GREEN);
        var bottomControls = new HBox(12);
        bottomControls.getChildren().add(connectedLabel);
        bottomControls.getChildren().add(circleIndicator);
        AnchorPane.setTopAnchor(signOutLink,12d);
        AnchorPane.setRightAnchor(signOutLink,12d);
        AnchorPane.setBottomAnchor(bottomControls,12d);
        AnchorPane.setRightAnchor(bottomControls,12d);
        AnchorPane.setBottomAnchor(programStatusLabel,12d);
        AnchorPane.setLeftAnchor(programStatusLabel,12d);
        AnchorPane.setLeftAnchor(textArea,10d);
        AnchorPane.setRightAnchor(textArea,10d);
        AnchorPane.setTopAnchor(textArea,40d);
        AnchorPane.setBottomAnchor(textArea,40d);
        AnchorPane.setTopAnchor(appTitleLabel,12d);
        AnchorPane.setLeftAnchor(appTitleLabel,12d);
        getChildren().add(signOutLink);
        getChildren().add(bottomControls);
        getChildren().add(programStatusLabel);
        getChildren().add(textArea);
        getChildren().add(appTitleLabel);
    }
}
