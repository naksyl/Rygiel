package com.scaffolding.model.jfx;

import com.scaffolding.model.Orders;
import com.scaffolding.model.OrderStatus;
import com.scaffolding.model.OrderType;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;

public class OrderFX {

    private final Orders order;
    private final SimpleStringProperty address;
    private final SimpleStringProperty description;
    private final SimpleStringProperty contractor;
    private final SimpleObjectProperty<LocalDate> date;
    private final SimpleObjectProperty<OrderStatus> status;
    private final SimpleObjectProperty<OrderType> type;
    private final SimpleObjectProperty<ContractorFX> contractorFX;

    public OrderFX(Orders order) {
        this.order = order;
        address = new SimpleStringProperty(order.getAddress());
        description = new SimpleStringProperty(order.getDescription());
        contractor = new SimpleStringProperty(order.getContractor().getName());
        date = new SimpleObjectProperty<>(order.getDate());
        status = new SimpleObjectProperty<>(order.getStatus());
        type = new SimpleObjectProperty<>(order.getType());
        contractorFX = new SimpleObjectProperty<>();
    }

    public Orders getOrder() {
        return order;
    }

    public String getContractor() {
        return contractor.get();
    }

    public SimpleStringProperty contractorProperty() {
        return contractor;
    }

    public String getAddress() {
        return address.get();
    }

    public SimpleStringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public String getDescription() {
        return description.get();
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public LocalDate getDate() {
        return date.get();
    }

    public Property<LocalDate> dateProperty() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date.set(date);
    }

    public OrderStatus getStatus() {
        return status.get();
    }

    public SimpleObjectProperty<OrderStatus> statusProperty() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status.set(status);
    }

    public OrderType getType() {
        return type.get();
    }

    public SimpleObjectProperty<OrderType> typeProperty() {
        return type;
    }

    public void setType(OrderType type) {
        this.type.set(type);
    }

    public ContractorFX getContractorFX() {
        return contractorFX.get();
    }

    public SimpleObjectProperty<ContractorFX> contractorFXProperty() {
        return contractorFX;
    }

    public void setContractorFX(ContractorFX contractorFX) {
        this.contractorFX.setValue(contractorFX);
    }

    public Orders update() {
        if (getContractorFX() != null)
            order.setContractor(getContractorFX().getContractor());
        order.setAddress(getAddress());
        order.setDescription(getDescription());
        order.setDate(getDate());
        order.setStatus(getStatus());
        order.setType(getType());
        return order;
    }
}
