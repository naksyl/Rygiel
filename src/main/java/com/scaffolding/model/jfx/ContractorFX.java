package com.scaffolding.model.jfx;

import com.scaffolding.model.Contractor;
import javafx.beans.property.SimpleStringProperty;

public class ContractorFX {

    private final Contractor contractor;
    private final SimpleStringProperty name;
    private final SimpleStringProperty address;
    private final SimpleStringProperty person;
    private final SimpleStringProperty email;
    private final SimpleStringProperty phone;

    public ContractorFX(Contractor contractor) {
        this.contractor = contractor;
        name = new SimpleStringProperty(contractor.getName());
        address = new SimpleStringProperty(contractor.getAddress());
        person = new SimpleStringProperty(contractor.getPerson());
        email = new SimpleStringProperty(contractor.getEmail());
        phone = new SimpleStringProperty(contractor.getPhone());
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
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

    public String getPerson() {
        return person.get();
    }

    public SimpleStringProperty personProperty() {
        return person;
    }

    public void setPerson(String person) {
        this.person.set(person);
    }

    public String getEmail() {
        return email.get();
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getPhone() {
        return phone.get();
    }

    public SimpleStringProperty phoneProperty() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public Contractor update() {
        contractor.setName(getName());
        contractor.setAddress(getAddress());
        contractor.setPerson(getPerson());
        contractor.setEmail(getEmail());
        contractor.setPhone(getPhone());
        return contractor;
    }
    public Contractor getContractor() {
        return contractor;
    }
}
