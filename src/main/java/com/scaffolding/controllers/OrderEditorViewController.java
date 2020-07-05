package com.scaffolding.controllers;

import com.scaffolding.enums.ViewType;
import com.scaffolding.interfaces.IApplicationManager;
import com.scaffolding.interfaces.IStorage;
import com.scaffolding.model.OrderType;
import com.scaffolding.model.Orders;
import com.scaffolding.model.jfx.ContractorFX;
import com.scaffolding.model.jfx.OrderFX;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

@Component
public class OrderEditorViewController implements Initializable {


    @FXML
    public TextField descriptionTextField;
    @FXML
    public TextField addressTextField;
    @FXML
    public ChoiceBox<ContractorFX> contractorChoiceBox;
    @FXML
    public ChoiceBox<OrderType> typeChoiceBox;
    @FXML
    public DatePicker datePicker;
    @FXML
    public Button saveBtn;

    private IApplicationManager applicationManager;
    private IStorage<ContractorFX> contractorStorage;
    private IStorage<OrderFX> orderStorage;
    private ObservableList<OrderType> types = FXCollections.observableList(Arrays.asList(OrderType.values()));
    private OrderFX order;
    private boolean readonly;

    @Autowired
    public void setApplicationManager(@Lazy IApplicationManager applicationManager) {
        this.applicationManager = applicationManager;
    }

    @Autowired
    public void setContractorStorage(@Lazy IStorage<ContractorFX> contractorStorage) {
        this.contractorStorage = contractorStorage;
    }

    @Autowired
    public void setOrderStorage(@Lazy IStorage<OrderFX> orderStorage) {
        this.orderStorage = orderStorage;
    }

    public void newOrder(ContractorFX contractorFX) {
        readonly = false;
        Orders entity = new Orders();
        entity.setContractor(contractorFX.getContractor());
        order = new OrderFX(entity);
        order.setContractorFX(contractorFX);
        applicationManager.showWindow(ViewType.ORDER_EDITOR_VIEW, "Dodaj nowe zlecenie");
        updateContractorChoiceBox();
    }

    public void editOrder(OrderFX order) {
        readonly = false;
        this.order = order;
        ContractorFX contractorFX = contractorStorage.findById(order.getOrder().getContractor().getId());
        this.order.setContractorFX(contractorFX);
        applicationManager.showWindow(ViewType.ORDER_EDITOR_VIEW, "Edytuj zlecenie");
    }

    private void updateContractorChoiceBox() {
        contractorChoiceBox.setItems(contractorStorage.getItemList());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        typeChoiceBox.setItems(types);
        updateContractorChoiceBox();
        bindControls();
        if (readonly) {
            descriptionTextField.setEditable(false);
            addressTextField.setEditable(false);
            datePicker.setEditable(false);
            saveBtn.setText("Ok");
        }
    }

    private void bindControls() {
        descriptionTextField.textProperty().bindBidirectional(order.descriptionProperty());
        addressTextField.textProperty().bindBidirectional(order.addressProperty());
        typeChoiceBox.valueProperty().bindBidirectional(order.typeProperty());
        datePicker.valueProperty().bindBidirectional(order.dateProperty());
        contractorChoiceBox.valueProperty().bindBidirectional(order.contractorFXProperty());
    }

    public void saveAction() {
        if (!readonly) {
            orderStorage.updateItem(order);
        }
        applicationManager.closeActiveWindow();
    }

    public void cancelAction() {
        applicationManager.closeActiveWindow();
    }

    public void showOrder(OrderFX selectedItem) {
        readonly = true;
        order = selectedItem;
        order.setContractorFX(contractorStorage.findById(order.getOrder().getContractor().getId()));
        applicationManager.showWindow(ViewType.ORDER_EDITOR_VIEW, "Podgląd zlecenia [TYLKO DO ODCZYTU]");
    }
}
