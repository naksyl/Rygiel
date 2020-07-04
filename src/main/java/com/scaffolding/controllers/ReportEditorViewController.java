package com.scaffolding.controllers;

import com.scaffolding.enums.ViewType;
import com.scaffolding.interfaces.IApplicationManager;
import com.scaffolding.interfaces.IStorage;
import com.scaffolding.model.OrderStatus;
import com.scaffolding.model.Report;
import com.scaffolding.model.ReportItem;
import com.scaffolding.model.ReportItemType;
import com.scaffolding.model.jfx.ContractorFX;
import com.scaffolding.model.jfx.OrderFX;
import com.scaffolding.model.jfx.ReportFX;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class ReportEditorViewController implements Initializable {
    @FXML
    public Label contractorLabel;
    @FXML
    public TextField piecesTextField;
    @FXML
    public ToggleGroup itemTypeToggleGroup;
    @FXML
    public ListView<ReportItem> itemListView;
    @FXML
    public Label orderLabel;

    @FXML
    public DatePicker datePicker;
    @FXML
    public RadioButton radio0;
    @FXML
    public RadioButton radio1;
    @FXML
    public RadioButton radio2;
    @FXML
    public RadioButton radio3;
    @FXML
    public Button removeItemBtn;
    @FXML
    public Button addItemBtn;
    @FXML
    public Button saveBtn;

    private ReportFX reportFX;
    private IApplicationManager applicationManager;
    private ObservableList<ReportItem> itemObservableList;
    private IStorage<ReportFX> reportStorage;
    private IStorage<OrderFX> orderStorage;
    private boolean readonly;

    @Autowired
    public void setOrderStorage(@Lazy IStorage<OrderFX> orderStorage) {
        this.orderStorage = orderStorage;
    }

    @Autowired
    public void setReportStorage(@Lazy IStorage<ReportFX> reportStorage) {
        this.reportStorage = reportStorage;
    }

    @Autowired
    public void setApplicationManager(@Lazy IApplicationManager applicationManager) {
        this.applicationManager = applicationManager;
    }

    public void saveAction(ActionEvent actionEvent) {
        if (!readonly) {
            reportFX.getReport().setItems(itemObservableList);
            reportFX.setDate(datePicker.getValue());
            reportFX.getOrder().setStatus(OrderStatus.FINISHED);
            reportFX.getOrder().getOrder().setReport(reportFX.getReport());
            reportStorage.updateItem(reportFX);
            orderStorage.updateItem(reportFX.getOrder());
        }
        applicationManager.closeActiveWindow();
    }

    public void cancelAction(ActionEvent actionEvent) {
        applicationManager.closeActiveWindow();
    }

    public void removeItemButon(ActionEvent actionEvent) {
        if (itemListView.getItems().size() > 0) {
            ReportItem item = itemListView.getSelectionModel().getSelectedItem();
            if (item != null) {
                itemObservableList.removeAll(item);
            } else applicationManager.showWarning("Nie wybrano żadnego elementu raportu");
        } else applicationManager.showWarning("Lista nie zawiera żadnych elementów");
    }

    public void addItemButton(ActionEvent actionEvent) {
        ReportItemType type = ReportItemType.values()[(int) itemTypeToggleGroup.getSelectedToggle().getUserData()];
        if (!piecesTextField.getText().equals("")) {
            try {
                double pieces = Double.parseDouble(piecesTextField.getText());
                ReportItem item = new ReportItem(type, pieces);
                item.setReport(reportFX.getReport());
                itemObservableList.addAll(item);
            } catch (Exception e) {
                e.printStackTrace();
                applicationManager.showWarning(piecesTextField.getText() + " nie jest liczbą");
            }
        } else applicationManager.showWarning("Nie wprowadzono ilości");
    }

    private void loadItems() {
        itemObservableList.addAll(reportFX.getReport().getItems());
        itemListView.setItems(itemObservableList);
        contractorLabel.setText(reportFX.getOrder().getContractor());
        orderLabel.setText(reportFX.getOrder().getDescription());
        datePicker.valueProperty().setValue(reportFX.getDate());
    }

    public void newReport(OrderFX orderFX) {
        Report report = new Report();
        report.setOrder(orderFX.getOrder());
        reportFX = new ReportFX(report);
        reportFX.setOrder(orderFX);
        reportFX.setContractor(new ContractorFX(orderFX.getOrder().getContractor()));
        applicationManager.showWindow(ViewType.REPORT_EDITOR_VIEW, "Dodaj nowy raport");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        radio0.setUserData(0);
        radio1.setUserData(1);
        radio2.setUserData(2);
        radio3.setUserData(3);
        itemObservableList = FXCollections.observableArrayList();
        loadItems();
        if (readonly) {
            addItemBtn.setDisable(true);
            removeItemBtn.setDisable(true);
            saveBtn.setText("Ok");
        }
    }

    public void editReport(ReportFX reportFX) {
        readonly = false;
        this.reportFX = reportFX;
        applicationManager.showWindow(ViewType.REPORT_EDITOR_VIEW, "Edytuj raport");
    }

    public void showReport(ReportFX selectedItem) {
        readonly = true;
        this.reportFX = selectedItem;
        applicationManager.showWindow(ViewType.REPORT_EDITOR_VIEW, "Podgląd raportu [TYLKO DO ODCZYTU]");
    }
}
