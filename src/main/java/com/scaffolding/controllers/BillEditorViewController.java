package com.scaffolding.controllers;

import com.scaffolding.enums.ViewType;
import com.scaffolding.interfaces.IApplicationManager;
import com.scaffolding.interfaces.IStorageManager;
import com.scaffolding.model.Bill;
import com.scaffolding.model.OrderStatus;
import com.scaffolding.model.Report;
import com.scaffolding.model.ReportItem;
import com.scaffolding.model.jfx.BillFX;
import com.scaffolding.model.jfx.OrderFX;
import com.scaffolding.model.jfx.ReportFX;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class BillEditorViewController implements Initializable {

    @FXML
    public Label assemblyCountLabel;
    @FXML
    public Label dissassemblyCountLabel;
    @FXML
    public Label reviewCountLabel;
    @FXML
    public Label workhoursCountLabel;
    @FXML
    public Label assemblyTotalLabel;
    @FXML
    public Label disassemblyTotalLabel;
    @FXML
    public Label reviewTotalLabel;
    @FXML
    public Label workhoursTotalLabel;
    @FXML
    public Label totalLabel;
    @FXML public Button saveBtn;

    private IApplicationManager applicationManager;
    private IStorageManager storageManager;

    private BillFX billFX;

    private final double assemblyPrice = 8.5;
    private final double disassemblyPrice = 5.2;
    private final double reviewPrice = 28;
    private final double workhourPrice = 40;

    private boolean readonly;

    @Autowired
    public void setStorageManager(IStorageManager storageManager) {
        this.storageManager = storageManager;
    }

    @Autowired
    public void setApplicationManager(@Lazy IApplicationManager applicationManager) {
        this.applicationManager = applicationManager;
    }

    public void cancelAction(ActionEvent actionEvent) {
        applicationManager.closeActiveWindow();
    }

    public void saveAction(ActionEvent actionEvent) {
        if (!readonly) {
            int bills = storageManager.getBillStorage().getItemList().size();
            billFX.setNumber(Integer.toString(bills));
            billFX.getOrder().setBillFX(billFX);
            billFX.getOrder().setStatus(OrderStatus.NOT_PAYED);
            storageManager.getBillStorage().updateItem(billFX);
            storageManager.getOrderStorage().updateItem(billFX.getOrder());
            storageManager.getReportStorage().updateItem(billFX.getReport());
        }
        applicationManager.closeActiveWindow();
    }

    public void editBill(BillFX reportFX) {

    }

    public void newBill(OrderFX orderFX) {
        Bill bill = new Bill();
        bill.setOrder(orderFX.getOrder());
        billFX = new BillFX(bill);
        billFX.setContractor(orderFX.getContractorFX());
        billFX.setOrder(orderFX);
        billFX.setReport(orderFX.getReportFX());
        //calculateBill(bill);
        applicationManager.showWindow(ViewType.BILL_EDITOR_VIEW, "Dodaj nową fakturę");
    }

    private void calculateBill(Bill bill) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Report report = billFX.getReport().getReport();
        double assembliesCount = 0;
        double assembliesTotal = 0;
        double disassemblyCount = 0;
        double disassemblyTotal = 0;
        double reviewCount = 0;
        double reviewTotal = 0;
        double hourCount = 0;
        double hourTotal = 0;

        for (ReportItem item : report.getItems()) {
            switch (item.getType()) {
                case SCAFFOLDING_ASSEMBLY:
                    assembliesCount += item.getPieces();
                    item.setPrice(item.getPieces() * assemblyPrice);
                    assembliesTotal += item.getPrice();
                    break;
                case SCAFFOLDING_DISASSEMBLY:
                    disassemblyCount += item.getPieces();
                    item.setPrice(item.getPieces() * disassemblyPrice);
                    disassemblyTotal += item.getPrice();
                    break;
                case SCAFFOLDING_REVIEW:
                    reviewCount += item.getPieces();
                    item.setPrice(item.getPieces() * reviewPrice);
                    reviewTotal += item.getPrice();
                    break;
                case WORKING_HOUR:
                    hourCount += item.getPieces();
                    item.setPrice(item.getPieces() * workhourPrice);
                    hourTotal += item.getPrice();
                    break;
            }
        }

        assemblyCountLabel.setText(Double.toString(assembliesCount));
        assemblyTotalLabel.setText(Double.toString(assembliesTotal));
        dissassemblyCountLabel.setText(Double.toString(disassemblyCount));
        disassemblyTotalLabel.setText(Double.toString(disassemblyTotal));
        reviewCountLabel.setText(Double.toString(reviewCount));
        reviewTotalLabel.setText(Double.toString(reviewTotal));
        workhoursCountLabel.setText(Double.toString(hourCount));
        workhoursTotalLabel.setText(Double.toString(hourTotal));

        double sum = assembliesTotal + disassemblyTotal + reviewTotal + hourTotal;
        totalLabel.setText(Double.toString(sum) + "zł") ;
        billFX.setTotal(sum);
        if (readonly)
            saveBtn.setText("Ok");
    }

    public void showBill(BillFX billfx) {
        this.billFX = billfx;
        readonly = true;
        applicationManager.showWindow(ViewType.BILL_EDITOR_VIEW, "Podgląd faktury [TYLKO DO ODCZYTU]");
    }
}
