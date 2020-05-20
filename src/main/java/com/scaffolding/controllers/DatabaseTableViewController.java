package com.scaffolding.controllers;

import com.scaffolding.enums.ApplicationAspect;
import com.scaffolding.enums.DatabaseAspect;
import com.scaffolding.interfaces.*;
import com.scaffolding.model.jfx.ContractorFX;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class DatabaseTableViewController implements IAspectAware, IDatabaseAware {

    @FXML public StackPane stackPane;

    @FXML public TableView<ContractorFX> contractorTableView;
    @FXML public TableColumn<ContractorFX, String> contractorNameColumn;
    @FXML public TableColumn<ContractorFX, String> contractorAddressColumn;
    @FXML public TableColumn<ContractorFX, String> contractorPersonColumn;
    @FXML public TableColumn<ContractorFX, String> contractorEmailColumn;
    @FXML public TableColumn<ContractorFX, String> contractorPhoneColumn;

    @FXML TableView tableView;

    private IAspectManager aspectManager;
    private IApplicationManager applicationManager;
    private IStorageManager storageManager;
    private DatabaseAspect databaseAspect;

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
                setupContractorAspect();
                break;
            case TABLE_ORDER:
                databaseAspect = DatabaseAspect.TABLE_ORDER;
                setupOrderAspect();
                break;
            case TABLE_BILL:
                databaseAspect = DatabaseAspect.TABLE_BILL;
                setupBillAspect();
                break;
            case TABLE_REPORT:
                databaseAspect = DatabaseAspect.TABLE_REPORT;
                setupReportAspect();
                break;
            case WELCOME:
            case STATISTICS:
            case OPTIONS:
                databaseAspect = DatabaseAspect.NONE;
                break;
        }
        setupTableView();
    }

    private void setupTableView() {
//        if(databaseAspect != DatabaseAspect.NONE)
//            contractorTableView.getItems().clear();
    }

    private void setupReportAspect() {

        tableView.setPlaceholder(new Label("Brak raportów w bazie danych"));
    }

    private void setupBillAspect() {
        tableView.setPlaceholder(new Label("Brak faktur w bazie danych"));
    }

    private void setupOrderAspect() {
        tableView.setPlaceholder(new Label("Brak zleceń w bazie danych"));
    }

    private void setupContractorAspect() {
        //stackPane.
        contractorTableView.toFront();
        contractorNameColumn.setCellValueFactory(new PropertyValueFactory<ContractorFX, String>("name"));
        contractorAddressColumn.setCellValueFactory(new PropertyValueFactory<ContractorFX, String>("address"));
        contractorPersonColumn.setCellValueFactory(new PropertyValueFactory<ContractorFX, String>("person"));
        contractorEmailColumn.setCellValueFactory(new PropertyValueFactory<ContractorFX, String>("email"));
        contractorPhoneColumn.setCellValueFactory(new PropertyValueFactory<ContractorFX, String>("phone"));
        contractorTableView.setPlaceholder(new Label("Brak kontrahentów w bazie danych"));
        if(applicationManager.hasOpenedFile())
            contractorTableView.setItems(storageManager.getContractorStorage().getContractorList());
    }

    @Override
    public void onDatabaseOpen(File file) {

    }

    @Override
    public void onDatabaseClose() {

    }
}
