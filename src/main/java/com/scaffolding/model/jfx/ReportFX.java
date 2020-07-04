package com.scaffolding.model.jfx;

import com.scaffolding.model.Report;
import javafx.beans.property.SimpleObjectProperty;

import java.time.LocalDate;

public class ReportFX {

    private final Report report;
    private final SimpleObjectProperty<LocalDate> date;
    private final SimpleObjectProperty<ContractorFX> contractor;
    private final SimpleObjectProperty<OrderFX> order;

    public ReportFX(Report report) {
        this.report = report;
        date = new SimpleObjectProperty<>(report.getDate());
        contractor = new SimpleObjectProperty<>();
        order = new SimpleObjectProperty<>();
    }

    public Report getReport() {
        return report;
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

    public Report update() {
        report.setOrder(getOrder().getOrder());
        report.setDate(getDate());
        return report;
    }
}
