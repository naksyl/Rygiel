package com.scaffolding.model.jfx;

import com.scaffolding.model.Bill;
import javafx.beans.property.SimpleObjectProperty;

import java.time.LocalDate;

public class BillFX {

    private final Bill bill;
    private final SimpleObjectProperty<LocalDate> date;
    private final SimpleObjectProperty<ContractorFX> contractor;
    private final SimpleObjectProperty<OrderFX> order;
    private final SimpleObjectProperty<ReportFX> report;
    private final SimpleObjectProperty<String> total;
    private final SimpleObjectProperty<String> number;
    private final SimpleObjectProperty<String> status;

    public Bill getBill() {
        return bill;
    }

    public LocalDate getDate() {
        return date.get();
    }

    public SimpleObjectProperty<LocalDate> dateProperty() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date.set(date);
    }

    public ContractorFX getContractor() {
        return contractor.get();
    }

    public SimpleObjectProperty<ContractorFX> contractorProperty() {
        return contractor;
    }

    public void setContractor(ContractorFX contractor) {
        this.contractor.set(contractor);
    }

    public OrderFX getOrder() {
        return order.get();
    }

    public SimpleObjectProperty<OrderFX> orderProperty() {
        return order;
    }

    public void setOrder(OrderFX order) {
        this.order.set(order);
    }

    public ReportFX getReport() {
        return report.get();
    }

    public SimpleObjectProperty<ReportFX> reportProperty() {
        return report;
    }

    public void setReport(ReportFX report) {
        this.report.set(report);
    }

    public String getTotal() {
        return total.get();
    }

    public SimpleObjectProperty<String> totalProperty() {
        return total;
    }

    public void setTotal(Double total) {
        this.total.set(String.valueOf(total)+"zł");
    }

    public String getNumber() {
        return number.get();
    }

    public SimpleObjectProperty<String> numberProperty() {
        return number;
    }

    public void setNumber(String number) {
        this.number.set(number);
    }

    public String getStatus() {
        return status.get();
    }

    public SimpleObjectProperty<String> statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public BillFX(Bill bill) {
        this.bill = bill;
        date = new SimpleObjectProperty<>(bill.getDate());
        contractor = new SimpleObjectProperty<>();
        order = new SimpleObjectProperty<>();
        report = new SimpleObjectProperty<>();
        total = new SimpleObjectProperty<>();
        number = new SimpleObjectProperty<>();
        status = new SimpleObjectProperty<>(bill.getPayed());
        setTotal(bill.getTotalPrice());
    }

    public Bill update() {
        bill.setDate(getDate());
        bill.setNumber(getNumber());
        bill.setPayed(getStatus());
        return bill;
    }
}
