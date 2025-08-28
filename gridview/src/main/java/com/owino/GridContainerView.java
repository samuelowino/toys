package com.owino;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class GridContainerView extends VBox {
    public GridContainerView(){
        var formView = new GridView();
        var formContainer = new HBox();
        var separator = new Separator();
        var cancelButton = new Button("Cancel");
        var saveButton = new Button("Save");
        cancelButton.setMinWidth(100);
        saveButton.setMinWidth(100);
        var bottomControls = new HBox();
        bottomControls.setSpacing(12);
        bottomControls.getChildren().add(cancelButton);
        bottomControls.getChildren().add(saveButton);
        formContainer.getChildren().add(formView);
        formContainer.setAlignment(Pos.CENTER);
        HBox.setMargin(cancelButton, new Insets(8));
        HBox.setMargin(saveButton, new Insets(8));
        bottomControls.setAlignment(Pos.BOTTOM_RIGHT);
        getChildren().add(formContainer);
        getChildren().add(separator);
        getChildren().add(bottomControls);
        VBox.setVgrow(formContainer, Priority.ALWAYS);
    }
}
