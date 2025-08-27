package com.owino;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.Random;

public class CustomersView extends VBox {
    private final TableView<Customer> customersTable;
    public CustomersView(){
        var mainContainer = new VBox();
        var topControls = new HBox();
        var topRightControls = new HBox();
        var refreshButton = new Button("Refresh");
        refreshButton.setMinWidth(100d);
        refreshButton.setOnAction(action -> reloadTable());
        var signOutLink = new Hyperlink("Sign out");
        topRightControls.setAlignment(Pos.BOTTOM_RIGHT);
        HBox.setHgrow(topRightControls, Priority.ALWAYS);
        topRightControls.getChildren().add(signOutLink);
        topControls.getChildren().add(refreshButton);
        topControls.getChildren().add(topRightControls);
        customersTable = new TableView<>();
        var firstNameColumn = new TableColumn<Customer,String>("First Name");
        firstNameColumn.setCellValueFactory(
                cellData -> new ReadOnlyStringWrapper(cellData.getValue().firstName())
        );
        var secondNameColumn = new TableColumn<Customer,String>("Last Name");
        secondNameColumn.setCellValueFactory(
                cellData -> new ReadOnlyStringWrapper(cellData.getValue().lastName())
        );
        var ordersColumn = new TableColumn<Customer, Double>("Orders");
        ordersColumn.setCellValueFactory(
                cellData -> Bindings.createObjectBinding(() -> cellData.getValue().orders())
        );
        customersTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        customersTable.getColumns().clear();
        customersTable.getColumns().add(firstNameColumn);
        customersTable.getColumns().add(secondNameColumn);
        customersTable.getColumns().add(ordersColumn);
        var separator = new Separator();
        var bottomButtonContainer = new HBox();
        var closeButton = new Button("Close");
        closeButton.setMinWidth(100d);
        bottomButtonContainer.getChildren().add(closeButton);
        bottomButtonContainer.setAlignment(Pos.BOTTOM_RIGHT);
        mainContainer.getChildren().add(topControls);
        mainContainer.getChildren().add(customersTable);
        mainContainer.getChildren().add(separator);
        mainContainer.getChildren().add(bottomButtonContainer);
        VBox.setMargin(topControls, new Insets(18d));
        VBox.setMargin(customersTable, new Insets(18));
        VBox.setMargin(bottomButtonContainer, new Insets(18));
        getChildren().add(mainContainer);
    }
    private void reloadTable() {
        updateCustomersTable(CustomersView.customersList());
    }
    public void updateCustomersTable(ObservableList<Customer> customerList){
        customersTable.setItems(customerList);
        customersTable.refresh();
    }
    public static ObservableList<Customer> customersList() {
        var customers =  List.of(
                new Customer("Lucas","Cage",Double.parseDouble(String.format("%.2f",new Random().nextDouble(1000,100_000)))),
                new Customer("Bruce","Lee",Double.parseDouble(String.format("%.2f",new Random().nextDouble(1000,100_000)))),
                new Customer("Mark","Hamil",Double.parseDouble(String.format("%.2f",new Random().nextDouble(1000,100_000)))),
                new Customer("Tom","Cruise",Double.parseDouble(String.format("%.2f",new Random().nextDouble(1000,100_000))))
        );
        return FXCollections.observableArrayList(customers);
    }
}
