package com.scaffolding.controllers;

import com.scaffolding.enums.ApplicationAspect;
import com.scaffolding.enums.DatabaseAspect;
import com.scaffolding.interfaces.*;
import com.scaffolding.model.OrderStatus;
import com.scaffolding.model.jfx.BillFX;
import com.scaffolding.model.jfx.ContractorFX;
import com.scaffolding.model.jfx.OrderFX;
import com.scaffolding.model.jfx.ReportFX;
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


    @FXML
    public TableView<ReportFX> reportTableView;
    @FXML
    public TableColumn<ReportFX, String> reportDateColumn;
    @FXML
    public TableColumn<ReportFX, String> reportContractorColumn;
    @FXML
    public TableColumn<ReportFX, String> reportOrderColumn;
    @FXML
    public TableView<BillFX> billTableView;
    @FXML
    public TableColumn<BillFX, String> billDateColumn;
    @FXML
    public TableColumn<BillFX, String> billContractorColumn;
    @FXML
    public TableColumn<BillFX, String> billOrderColumn;
    @FXML
    public TableColumn<BillFX, String> billTotalColumn;
    @FXML
    public TableColumn<BillFX, String> billStatusColumn;


    private IAspectManager aspectManager;
    private IApplicationManager applicationManager;
    private IStorageManager storageManager;

    private ContractorEditorViewController contractorEditorViewController;
    private OrderEditorViewController orderEditorViewController;
    private ReportEditorViewController reportEditorViewController;
    private BillEditorViewController billEditorViewController;
    private MainViewController mainViewController;

    private DatabaseAspect databaseAspect;

    @Autowired
    public void setMainViewController(@Lazy MainViewController mainViewController) {
        this.mainViewController = mainViewController;
    }

    @Autowired
    public void setBillEditorViewController(BillEditorViewController billEditorViewController) {
        this.billEditorViewController = billEditorViewController;
    }

    @Autowired
    public void setReportEditorViewController(ReportEditorViewController reportEditorViewController) {
        this.reportEditorViewController = reportEditorViewController;
    }

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
        if (applicationManager.hasOpenedFile()) {
            storageManager.getOrderStorage().updateFromDatabase();
            orderTableView.setItems(storageManager.getOrderStorage().getItemList());
            orderTableView.getSelectionModel().selectFirst();
        }
        orderTableView.toFront();
    }

    private void updateBillAspect() {
        if (applicationManager.hasOpenedFile()) {
            storageManager.getBillStorage().updateFromDatabase();
            billTableView.setItems(storageManager.getBillStorage().getItemList());
            billTableView.getSelectionModel().selectFirst();
        }
    }

    private void updateReportAspect() {
        if (applicationManager.hasOpenedFile()) {
            storageManager.getReportStorage().updateFromDatabase();
            reportTableView.setItems(storageManager.getReportStorage().getItemList());
            reportTableView.getSelectionModel().selectFirst();
        }
        reportTableView.toFront();
    }

    private void updateContractorAspect() {
        if (applicationManager.hasOpenedFile()) {
            storageManager.getContractorStorage().updateFromDatabase();
            contractorTableView.setItems(storageManager.getContractorStorage().getItemList());
            contractorTableView.getSelectionModel().selectFirst();
        }
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
        contractorTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                boolean hasOrder = newValue.getContractor().getOrders().size() > 0;
                mainViewController.disableContractorButton(false, hasOrder, false);

            } else {
                mainViewController.disableContractorButton(true, true, true);
            }
        });

        orderAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        orderContractorColumn.setCellValueFactory(new PropertyValueFactory<>("contractor"));
        orderDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        orderDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        orderStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        orderTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        orderTableView.setPlaceholder(new Label("Brak zleceń w bazie danych"));
        orderTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                OrderStatus status = newValue.getOrder().getStatus();
                boolean disableEdit = status != OrderStatus.OPENED;
                boolean disableDelete = status != OrderStatus.OPENED;
                boolean disableReport = status == OrderStatus.CLOSED || status == OrderStatus.NOT_PAYED;
                boolean disableBilll = status == OrderStatus.OPENED;
                mainViewController.disableOrderButtons(false, disableEdit, disableDelete, false, disableBilll);

            } else {
                mainViewController.disableOrderButtons(true, true, true, true, true);
            }
            ;
        });

        reportContractorColumn.setCellValueFactory(new PropertyValueFactory<>("contractor"));
        reportDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        reportOrderColumn.setCellValueFactory(new PropertyValueFactory<>("order"));
        reportTableView.setPlaceholder(new Label("Brak raportów w bazie danych"));
        reportTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                boolean hasBill = newValue.getReport().getOrder().getBill() != null;
                boolean hasPayedBill = newValue.getReport().getOrder().getStatus() == OrderStatus.CLOSED;
                mainViewController.disableReportButtons(false, hasBill, hasBill, hasPayedBill);

            } else {
                mainViewController.disableReportButtons(true, true, true, true);
            }
        });

        billContractorColumn.setCellValueFactory(new PropertyValueFactory<>("contractor"));
        billDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        billOrderColumn.setCellValueFactory(new PropertyValueFactory<>("order"));
        billStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        billTotalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));
        billTableView.setPlaceholder(new Label("Brak faktur w bazie danych"));
        billTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                boolean payed = newValue.getStatus().equals("opłacona");
                mainViewController.disableBillButtons(false, payed, payed, payed);
            } else {
                mainViewController.disableBillButtons(true, true, true, true);
            }
        });
        if (applicationManager.hasOpenedFile()) {
            contractorTableView.setItems(storageManager.getContractorStorage().getItemList());
            orderTableView.setItems(storageManager.getOrderStorage().getItemList());
            reportTableView.setItems(storageManager.getReportStorage().getItemList());
            billTableView.setItems(storageManager.getBillStorage().getItemList());
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

    public void editReport() {
        if (databaseAspect == DatabaseAspect.TABLE_REPORT) {
            reportEditorViewController.editReport(reportTableView.getSelectionModel().getSelectedItem());
        } else if (databaseAspect == DatabaseAspect.TABLE_ORDER) {
            OrderFX orderFX = orderTableView.getSelectionModel().getSelectedItem();
            if (orderFX != null) {
                if (orderFX.getStatus() == OrderStatus.OPENED) {
                    reportEditorViewController.newReport(orderFX);
                } else if (orderFX.getStatus() == OrderStatus.FINISHED) {
                    reportEditorViewController.editReport(orderFX.getReportFX());
                }
            } else applicationManager.showWarning("Nie wybrano zlecenia.");
        }
    }

    public void deleteReport() {
        if (reportTableView.getItems().size() > 0) {
            ReportFX selected = reportTableView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                if (selected.getOrder().getStatus().ordinal() <= 1) {
                    storageManager.getReportStorage().deleteItem(selected);
                } else applicationManager.showWarning("Faktura została wystawiona. Brak możliwości edycji");
            } else applicationManager.showWarning("Nie wybrano raportu do usunięcia");
        } else applicationManager.showWarning("Brak raportów w bazie danych");
    }

    public void editBill() {
        OrderFX orderFX;
        switch (databaseAspect) {
            case TABLE_ORDER:
                orderFX = orderTableView.getSelectionModel().getSelectedItem();
                if (orderFX != null) {
                    if (orderFX.getReportFX() != null) {
                        if (orderFX.getBillFX() != null) {
                            if (orderFX.getBillFX().getBill().getPayed().equals("opłacona"))
                                applicationManager.showWarning("Faktura została opłacona. Brak możliwości edycji");
                            else
                                billEditorViewController.editBill(orderFX.getBillFX());
                        } else
                            billEditorViewController.newBill(orderFX);
                    } else
                        applicationManager.showWarning("Brak raportu do wybrnego zlecenia. Faktura jest wystawaina na podstawie raportu.");
                } else
                    applicationManager.showWarning("Nie wybrano zlecenia");
                break;
            case TABLE_REPORT:
                orderFX = reportTableView.getSelectionModel().getSelectedItem().getOrder();
                if (orderFX != null) {
                        if (orderFX.getBillFX() != null) {
                            if (orderFX.getBillFX().getBill().getPayed().equals("opłacona"))
                                showBill();
                            else
                                billEditorViewController.editBill(orderFX.getBillFX());
                        } else
                            billEditorViewController.newBill(orderFX);
                } else
                    applicationManager.showWarning("Nie wybrano raportu");
                break;
        }
    }

    public void closeOrder() {
        if (databaseAspect == DatabaseAspect.TABLE_BILL) {
            BillFX bill = billTableView.getSelectionModel().getSelectedItem();
            if (bill != null) {
                bill.setStatus("opłacona");
                bill.getBill().setPayed("opłacona");
                bill.getOrder().setStatus(OrderStatus.CLOSED);
                bill.getOrder().getOrder().setStatus(OrderStatus.CLOSED);
                bill.update();
                bill.getOrder().update();
                storageManager.getOrderStorage().updateItem(bill.getOrder());
                storageManager.getBillStorage().updateItem(bill);
            }
        }
    }

    public void showOrder() {
        orderEditorViewController.showOrder(orderTableView.getSelectionModel().getSelectedItem());
    }

    public void showReport(ReportFX reportFX) {
        if (reportFX != null)
        reportEditorViewController.showReport(reportFX);
        else {

        reportEditorViewController.showReport(reportTableView.getSelectionModel().getSelectedItem());
        }
    }

    public void createReport() {
        OrderFX orderFX = orderTableView.getSelectionModel().getSelectedItem();
        if (orderFX != null) {
            if (orderFX.getOrder().getReport() == null)
                editReport();
            else showReport(orderFX.getReportFX());
        }
    }

    public void createBill() {
        OrderFX orderFX = null;
        if (databaseAspect == DatabaseAspect.TABLE_ORDER) {
            orderFX = orderTableView.getSelectionModel().getSelectedItem();
        } else if (databaseAspect == DatabaseAspect.TABLE_REPORT) {
            orderFX = reportTableView.getSelectionModel().getSelectedItem().getOrder();
        }
        if (orderFX != null) {
            if (orderFX.getOrder().getBill() == null)
                editBill();
            else  billEditorViewController.showBill(orderFX.getBillFX());
        }
    }

    public void showBill() {
        BillFX billfx = null;
        switch (databaseAspect) {
            case TABLE_ORDER:
                billfx = storageManager.getBillStorage().findById(orderTableView.getSelectionModel()
                        .getSelectedItem().getOrder().getBill().getId());
                break;
            case TABLE_REPORT:
                billfx = storageManager.getBillStorage().findById(reportTableView.getSelectionModel()
                        .getSelectedItem().getReport().getOrder().getBill().getId());
                break;
            case TABLE_BILL:
                billfx = billTableView.getSelectionModel().getSelectedItem();
                break;
            default:
                billfx = null;
        }
        if (billfx == null)
            editBill();
        else
            billEditorViewController.showBill(billfx);
    }
}
