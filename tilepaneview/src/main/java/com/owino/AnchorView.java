package com.owino;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;

public class AnchorView extends AnchorPane{
    public AnchorView(){
        var tileView = new TilePaneView();
        var refreshLink = new Hyperlink("Expand");
        refreshLink.setFont(new Font("",32));
        AnchorPane.setRightAnchor(refreshLink,6d);
        AnchorPane.setBottomAnchor(refreshLink,6d);
        AnchorPane.setTopAnchor(tileView,5d);
        AnchorPane.setBottomAnchor(tileView,5d);
        AnchorPane.setRightAnchor(tileView,1d);
        AnchorPane.setLeftAnchor(tileView,1d);
        getChildren().add(tileView);
        getChildren().add(refreshLink);
        refreshLink.setOnAction(e -> tileView.addEmojis());
    }
}
