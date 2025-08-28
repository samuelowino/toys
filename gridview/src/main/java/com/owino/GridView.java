package com.owino;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class GridView extends GridPane {
    public GridView(){
        var titleLabel = new Label("Support Ticket");
        var emailLabel = new Label("Email");
        var emailField = new TextField();
        var contractLabel = new Label("Contract");
        var contractField = new TextField();
        var priorityLabel = new Label("Priority");
        ObservableList<String> options = FXCollections
                .observableArrayList("Medium","High","Low");
        var priorityPicker = new ComboBox<>(options);
        var severityLabel = new Label("Severity");
        ObservableList<String> severityOptions = FXCollections
                .observableArrayList("HIGH","CRITICAL","MILD","IGNORE");
        var severityPicker = new ComboBox<>(severityOptions);
        var cateooryLabel = new Label("Category");
        ObservableList<String> categories = FXCollections.observableArrayList("BUG","FEATURE","OTHER");
        var categoryPicker = new ComboBox<>(categories);
        var problemLabel = new Label("Problem");
        var problemField = new TextField();
        var descriptionLabel = new Label("Description");
        var descriptionField = new TextArea();
        var pickersHBox = new HBox(12);
        pickersHBox.getChildren().addAll(priorityPicker,severityLabel,severityPicker,cateooryLabel,categoryPicker);
        pickersHBox.setAlignment(Pos.CENTER_LEFT);
        add(titleLabel, 1, 0);
        add(emailLabel,0,1);
        add(emailField,1,1);
        add(contractLabel,2,1,4,1);
        add(contractField,6,1,5,1);
        add(priorityLabel,0,2,1,1);
        add(pickersHBox,1,2,12,1);
        add(problemLabel,0,3);
        add(problemField,1,3);
        add(descriptionLabel,0,4);
        add(descriptionField,1,4);
        setPadding( new Insets(12));
        setHgap(12);
        setVgap(12);
    }
}
