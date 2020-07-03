package com.scaffolding.controllers;

import com.scaffolding.enums.ApplicationAspect;
import com.scaffolding.enums.DatabaseAspect;
import com.scaffolding.interfaces.*;
import com.scaffolding.model.jfx.ContractorFX;
import com.scaffolding.model.jfx.OrderFX;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class DatabaseTableViewController implements IAspectAware, IDatabaseAware, Initializable {

    @FXML
    public TableView<ContractorFX> contractorTableView;
    @FXML
    public TableColumn<ContractorFX, String> contractorNameColumn;
    @FXML
    public TableColumn<ContractorFX, String> contractorAddressColumn;
    @FXML
    public TableColumn<ContractorFX, String> contractorPersonColumn;
    @FXML
    public TableColumn<ContractorFX, String> contractorEmailColumn;
    @FXML
    public TableColumn<ContractorFX, String> contractorPhoneColumn;

    @FXML
    public TableView<OrderFX> orderTableView;
    @FXML
    public TableColumn<OrderFX, String> orderDescriptionColumn;
    @FXML
    public TableColumn<OrderFX, String> orderDateColumn;
    @FXML
    public TableColumn<OrderFX, String> orderContractorColumn;
    @FXML
    public TableColumn<OrderFX, String> orderAddressColumn;
    @FXML
    public TableColumn<OrderFX, String> orderStatusColumn;
    @FXML
    public TableColumn<OrderFX, String> orderTypeColumn;


    private IAspectManager aspectManager;
    private IApplicationManager applicationManager;
    private IStorageManager storageManager;

    private ContractorEditorViewController contractorEditorViewController;
    private OrderEditorViewController orderEditorViewController;
    private DatabaseAspect databaseAspect;

    @Autowired
    public void setContractorEditorViewController(ContractorEditorViewController contractorEditorViewController) {
        this.contractorEditorViewController = contractorEditorViewController;
    }

    @Autowired
    public void setOrderEditorViewController(OrderEditorViewController orderEditorViewController) {
        this.orderEditorViewController = orderEditorViewController;
    }

    @Autowired
    public void setApplicationManager(IApplicationManager applicationManager) {
        this.applicationManager = applicationManager;
    }

    @Autowired
    public void setStorageManager(IStorageManager storageManager) {
        this.storageManager = storageManager;
    }

    @Autowired
    public void setAspectManager(@Lazy IAspectManager aspectManager) {
        this.aspectManager = aspectManager;
    }

    @Override
    public void beforeApplicationContextChange(ApplicationAspect applicationAspect) {

    }

    @Override
    public void onApplicationAspectChanged(ApplicationAspect applicationAspect) {
        switch (applicationAspect) {
            case TABLE_CONTRACTOR:
                databaseAspect = DatabaseAspect.TABLE_CONTRACTOR;
                updateContractorAspect();
                break;
            case TABLE_ORDER:
                databaseAspect = DatabaseAspect.TABLE_ORDER;
                updateOrderAspect();
                break;
            case TABLE_BILL:
                databaseAspect = DatabaseAspect.TABLE_BILL;
                updateBillAspect();
                break;
            case TABLE_REPORT:
                databaseAspect = DatabaseAspect.TABLE_REPORT;
                updateReportAspect();
                break;
            case WELCOME:
            case STATISTICS:
            case OPTIONS:
                databaseAspect = DatabaseAspect.NONE;
                break;
        }
    }

    private void updateOrderAspect() {
        storageManager.getOrderStorage().updateFromDatabase();
        orderTableView.setItems(storageManager.getOrderStorage().getItemList());
        orderTableView.toFront();
    }

    private void updateBillAspect() {

    }

    private void updateReportAspect() {
    }

    private void updateContractorAspect() {
        storageManager.getContractorStorage().updateFromDatabase();
        contractorTableView.setItems(storageManager.getContractorStorage().getItemList());
        contractorTableView.toFront();
    }


    @Override
    public void onDatabaseOpen(File file) {

    }

    @Override
    public void onDatabaseClose() {

    }

    public void addNewContractor() {
        contractorEditorViewController.newContractor();
    }

    public void editActiveContractor() {
        ContractorFX activeContractorFX = contractorTableView.getSelectionModel().getSelectedItem();
        if (activeContractorFX != null)
            contractorEditorViewController.editContractor(activeContractorFX);
    }

    public void deleteActiveContractor() {
        ContractorFX activeContractorFX = contractorTableView.getSelectionModel().getSelectedItem();
        if (activeContractorFX != null)
            storageManager.getContractorStorage().deleteItem(activeContractorFX);
    }

    public void addNewOrder() {
        ContractorFX activeContractorFX = contractorTableView.getSelectionModel().getSelectedItem();
        if (activeContractorFX != null)
            orderEditorViewController.newOrder(activeContractorFX);
        else if (contractorTableView.getItems().size() > 0)
            orderEditorViewController.newOrder(contractorTableView.getItems().get(0));
        else
            applicationManager.showWarning("Brak kontrahentów w bazie danych.\nZlecenie nie może istnieć bez kontrahenta");

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        contractorNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        contractorAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        contractorPersonColumn.setCellValueFactory(new PropertyValueFactory<>("person"));
        contractorEmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        contractorPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        contractorTableView.setPlaceholder(new Label("Brak kontrahentów w bazie danych"));

        orderAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        orderContractorColumn.setCellValueFactory(new PropertyValueFactory<>("contractor"));
        orderDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        orderDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        orderStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        orderTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        orderTableView.setPlaceholder(new Label("Brak zleceń w bazie danych"));

        if (applicationManager.hasOpenedFile()) {
            contractorTableView.setItems(storageManager.getContractorStorage().getItemList());
            orderTableView.setItems(storageManager.getOrderStorage().getItemList());
        }
    }

    public void editOrder() {
        if (orderTableView.getItems().size() > 0) {
            OrderFX activeOrderFX = orderTableView.getSelectionModel().getSelectedItem();
            if (activeOrderFX != null)
                orderEditorViewController.editOrder(activeOrderFX);
            else
                applicationManager.showWarning("Nie wybrano zlecenia do edycji");
        } else
            applicationManager.showWarning("Brak zleceń w bazie danych");
    }

    public void deleteOrder() {
        if (orderTableView.getItems().size() > 0) {
            OrderFX activeOrderFX = orderTableView.getSelectionModel().getSelectedItem();
            if (activeOrderFX != null)
                storageManager.getOrderStorage().deleteItem(activeOrderFX);
            else applicationManager.showWarning("Nie wybrano zlecenia do usunięcia");
        } else applicationManager.showWarning("Brak zleceń w bazie danych");
    }
}
