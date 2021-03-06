package com.scaffolding.controllers;

import com.scaffolding.enums.ApplicationAspect;
import com.scaffolding.enums.ViewType;
import com.scaffolding.factories.ResourceMapper;
import com.scaffolding.interfaces.IApplicationManager;
import com.scaffolding.interfaces.IAspectAware;
import com.scaffolding.interfaces.IDatabaseAware;
import com.scaffolding.interfaces.IViewManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class MainViewController implements Initializable, IAspectAware, IDatabaseAware {

    @FXML
    public Button editOrderBtn;
    @FXML
    public Button deleteOrderBtn;
    @FXML
    public Button editReportBtn;
    @FXML
    public Button editBillBtn;
    @FXML
    public Button editContractorBtn;
    @FXML
    public Button deleteContractorBtn;
    @FXML
    public Button newOrderBtn;
    @FXML
    public Button editReportReportBtn;
    @FXML
    public Button deleteReportReportBtn;
    @FXML
    public Button editBillReportBtn;
    @FXML
    public TitledPane contractorAccordion;
    @FXML
    public TitledPane orderAccordion;
    @FXML
    public TitledPane reportAccordion;
    @FXML
    public TitledPane billAccordiom;
    @FXML
    public Button deleteBillBillBtn;
    @FXML
    public Button markBillBtn;
    public Button showOrderBtn;
    public Button showReportBtn;
    public Button showBillBtn;
    @FXML
    private HBox aspectBox;

    @FXML
    private Accordion accordion;

    private IApplicationManager applicationManager;
    private IViewManager viewManager;

    private DatabaseTableViewController tableViewController;

    private ViewType currentViewType = ViewType.WELCOME_VIEW;

    @Autowired
    public void setApplicationManager(@Lazy IApplicationManager applicationManager) {
        this.applicationManager = applicationManager;
    }

    @Autowired
    public void setViewManager(@Lazy IViewManager viewManager) {
        this.viewManager = viewManager;
    }

    @Autowired
    public void setTableViewController(@Lazy DatabaseTableViewController tableViewController) {
        this.tableViewController = tableViewController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupAccordion();
    }

    @Override
    public void beforeApplicationContextChange(ApplicationAspect applicationAspect) {
        ViewType newView = ResourceMapper.getAspectViewType(applicationAspect);
        if (currentViewType != newView) {
            Parent parent = viewManager.createView(newView);
            HBox.setHgrow(parent, Priority.ALWAYS);
            aspectBox.getChildren().set(1, parent);
            currentViewType = newView;
        }
    }

    @Override
    public void onApplicationAspectChanged(ApplicationAspect applicationAspect) {

    }

    private void setupAccordion() {
        accordion.expandedPaneProperty().addListener((observableValue, oldPane, newPane) -> {
            if (newPane != null) {
                System.out.println(newPane.getText());
                int aspectNumber = accordion.getPanes().indexOf(newPane);
                ApplicationAspect applicationAspect = ApplicationAspect.WELCOME;
                switch (aspectNumber) {
                    case 0:
                        if (applicationManager.hasOpenedFile())
                            applicationAspect = ApplicationAspect.STATISTICS;
                        break;
                    case 1:
                        applicationAspect = ApplicationAspect.TABLE_CONTRACTOR;
                        break;
                    case 2:
                        applicationAspect = ApplicationAspect.TABLE_ORDER;
                        break;
                    case 3:
                        applicationAspect = ApplicationAspect.TABLE_REPORT;
                        break;
                    case 4:
                        applicationAspect = ApplicationAspect.TABLE_BILL;
                        break;
                    case 5:
                        applicationAspect = ApplicationAspect.OPTIONS;
                        break;
                }
                applicationManager.setApplicationAspect(applicationAspect);
            } else {
                applicationManager.setApplicationAspect(ApplicationAspect.WELCOME);
            }
        });
        accordion.setExpandedPane(accordion.getPanes().get(0));
    }

    public void closeApplication() {
        applicationManager.close();
    }

    public void openFile() {
        applicationManager.openFile();
    }

    public void editContractorButton() {
        tableViewController.editActiveContractor();
    }

    public void deleteContractorButton() {
        tableViewController.deleteActiveContractor();
    }

    public void newContractorButton() {
        tableViewController.addNewContractor();
    }

    public void newOrderButton() {
        tableViewController.addNewOrder();
    }

    public void newOrderButton2() {
        newOrderButton();
    }

    public void editOrderButton() {
        tableViewController.editOrder();
    }

    public void deleteOrderButton() {
        tableViewController.deleteOrder();
    }

    public void deleteBillButton(ActionEvent actionEvent) {

    }

    public void editBillButton(ActionEvent actionEvent) {
        tableViewController.createBill();
    }

    public void deleteReportButton(ActionEvent actionEvent) {
        tableViewController.deleteReport();
    }

    public void editReportButton(ActionEvent actionEvent) {
        tableViewController.editReport();
    }

    public void newFileButton(ActionEvent actionEvent) {
        applicationManager.newFile();
    }

    public void disableOrderButtons(boolean showOrder, boolean editOrder, boolean deleteOrder,
                                    boolean editReport, boolean editBill) {
        showOrderBtn.setDisable(showOrder);
        editOrderBtn.setDisable(editOrder);
        deleteOrderBtn.setDisable(deleteOrder);
        editReportBtn.setDisable(editReport);
        editBillBtn.setDisable(editBill);
    }

    public void disableContractorButton(boolean editContractor, boolean deleteContractor,
                                        boolean newOrder) {
        editContractorBtn.setDisable(editContractor);
        deleteContractorBtn.setDisable(deleteContractor);
        newOrderBtn.setDisable(newOrder);
    }

    public void disableReportButtons(boolean showReport, boolean editReport, boolean deleteReport,
                                     boolean editBillBtn) {
        showReportBtn.setDisable(showReport);
        editReportReportBtn.setDisable(editReport);
        deleteReportReportBtn.setDisable(deleteReport);
//        editBillReportBtn.setDisable(editBillBtn);
    }

    public void disableBillButtons(boolean showBill, boolean editBill, boolean deleteBill, boolean markBill) {
        showBillBtn.setDisable(showBill);
//        editBillBillBtn.setDisable(editBill);
        deleteBillBillBtn.setDisable(deleteBill);
        markBillBtn.setDisable(markBill);
    }

    public void markAsPayedButton(ActionEvent actionEvent) {
        tableViewController.closeOrder();
    }

    @Override
    public void onDatabaseOpen(File file) {
        contractorAccordion.setDisable(false);
        orderAccordion.setDisable(false);
        reportAccordion.setDisable(false);
        billAccordiom.setDisable(false);
    }

    @Override
    public void onDatabaseClose() {
        contractorAccordion.setDisable(true);
        orderAccordion.setDisable(true);
        reportAccordion.setDisable(true);
        billAccordiom.setDisable(true);
    }

    public void showOrderButton(ActionEvent actionEvent) {
        tableViewController.showOrder();
    }

    public void showReportButton(ActionEvent actionEvent) {
        tableViewController.showReport(null);
    }

    public void showBillButton(ActionEvent actionEvent) {
        tableViewController.showBill();
    }

    public void createReportButton(ActionEvent actionEvent) {
        tableViewController.createReport();
    }

    public void createBillButton(ActionEvent actionEvent) {
        tableViewController.createBill();
    }
}
