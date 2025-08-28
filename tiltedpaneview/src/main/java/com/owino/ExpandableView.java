package com.owino;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
public class ExpandableView extends VBox {
    public ExpandableView(){
        var keywordsLabel = new Label("Keywords");
        var keywordsField = new TextField();
        var advancedOptions = new VBox();
        var allKeywordsLabel = new Label("All Keywords");
        var allKeywordsCheckbox = new CheckBox();
        var domainsLabel = new Label("Domains");
        var domainsField = new TextField();
        advancedOptions.getChildren().add(allKeywordsLabel);
        advancedOptions.getChildren().add(allKeywordsCheckbox);
        advancedOptions.getChildren().add(domainsLabel);
        advancedOptions.getChildren().add(domainsField);
        VBox.setMargin(advancedOptions,new Insets(12,0,12,0));
        var expandableView = new TitledPane("Advanced Options",advancedOptions);
        var searchButton = new Button("Search");
        getChildren().add(keywordsLabel);
        getChildren().add(keywordsField);
        getChildren().add(expandableView);
        getChildren().add(searchButton);
        setPadding(new Insets(12));
    }
}
