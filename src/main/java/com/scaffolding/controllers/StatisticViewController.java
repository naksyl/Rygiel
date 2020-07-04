package com.scaffolding.controllers;

import com.scaffolding.enums.ApplicationAspect;
import com.scaffolding.interfaces.IApplicationManager;
import com.scaffolding.interfaces.IAspectAware;
import com.scaffolding.interfaces.IStorageManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class StatisticViewController implements IAspectAware, Initializable {
    @FXML
    public Label contractorCount;
    @FXML
    public Label orderCount;
    @FXML
    public Label reportCount;
    @FXML
    public Label missingReports;
    @FXML
    public Label missingBills;
    @FXML
    public Label billCount;
    @FXML
    public Label unpayedBills;

    private IStorageManager storageManager;
    private IApplicationManager applicationManager;

    @Autowired
    public void setApplicationManager(IApplicationManager applicationManager) {
        this.applicationManager = applicationManager;
    }

    @Autowired
    public void setStorageManager(@Lazy IStorageManager storageManager) {
        this.storageManager = storageManager;
    }


    @Override
    public void beforeApplicationContextChange(ApplicationAspect applicationAspect) {

    }

    @Override
    public void onApplicationAspectChanged(ApplicationAspect applicationAspect) {
        if (applicationAspect == ApplicationAspect.STATISTICS) {
            updateLabels();
        }
    }

    private void updateLabels() {
        if(applicationManager.hasOpenedFile()) {
            int contractors = storageManager.getContractorStorage().getItemList().size();
            int orders = storageManager.getOrderStorage().getItemList().size();
            int reports = storageManager.getReportStorage().getItemList().size();
            int bills = storageManager.getBillStorage().getItemList().size();
            int unpayed = storageManager.getBillStorage().getItemList().filtered(e -> e.getBill().getPayed().equals("nieopłacona")).size();

            contractorCount.setText(Integer.toString(contractors));
            orderCount.setText(Integer.toString(orders));
            reportCount.setText(Integer.toString(reports));
            missingReports.setText(Integer.toString(orders - reports));
            missingBills.setText(Integer.toString(orders - bills));
            billCount.setText(Integer.toString(bills));
            unpayedBills.setText(Integer.toString(unpayed));
        } //else clearLabels();
    }

    private void clearLabels() {
        contractorCount.setText("");
        orderCount.setText("");
        reportCount.setText("");
        missingReports.setText("");
        missingBills.setText("");
        billCount.setText("");
        unpayedBills.setText("");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        clearLabels();
    }
}
