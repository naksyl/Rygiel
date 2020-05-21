package com.scaffolding.controllers;

import com.scaffolding.enums.ViewType;
import com.scaffolding.interfaces.IApplicationManager;
import com.scaffolding.interfaces.IContractorStorage;
import com.scaffolding.model.Contractor;
import com.scaffolding.model.jfx.ContractorFX;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class ContractorEditorViewController implements Initializable {

    @FXML public TextField nameTextField;
    @FXML public TextField addressTextField;
    @FXML public TextField personTextField;
    @FXML public TextField emailTextField;
    @FXML public TextField phoneTextField;

    private IApplicationManager applicationManager;
    private IContractorStorage contractorStorage;
    private ContractorFX contractor;

    @Autowired
    public void setApplicationManager(@Lazy IApplicationManager applicationManager) {
        this.applicationManager = applicationManager;
    }

    @Autowired
    public void setContractorStorage(@Lazy IContractorStorage contractorStorage) {
        this.contractorStorage = contractorStorage;
    }

    public void editContractor(ContractorFX contractor) {
        this.contractor = contractor;
        applicationManager.showWindow(ViewType.CONTRACTOR_EDITOR_VIEW, "Edytuj bieżącego kontrahenta");
    }

    public void newContractor() {
        Contractor entity = new Contractor();
        contractor = new ContractorFX(entity);
        applicationManager.showWindow(ViewType.CONTRACTOR_EDITOR_VIEW, "Dodaj nowego kontrahenta");
    }

    private void bindControls() {
        nameTextField.textProperty().bindBidirectional(contractor.nameProperty());
        addressTextField.textProperty().bindBidirectional(contractor.addressProperty());
        personTextField.textProperty().bindBidirectional(contractor.personProperty());
        emailTextField.textProperty().bindBidirectional(contractor.emailProperty());
        phoneTextField.textProperty().bindBidirectional(contractor.phoneProperty());
    }

    public void cancelAction() {
        applicationManager.closeActiveWindow();
    }

    public void saveAction() {
        contractorStorage.updateContractor(contractor);
        applicationManager.closeActiveWindow();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nameTextField.setText("");
        addressTextField.setText("");
        personTextField.setText("");
        emailTextField.setText("");
        phoneTextField.setText("");
        bindControls();
    }
}
