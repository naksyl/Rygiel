package com.scaffolding.model;

import javax.persistence.*;

@Entity
@Table(name = "rates")
public class Rates {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private ReportItemType id;

    private String unit;
    private double price;

    public Rates() {
    }

    public Rates(String unit, double price) {
        this.unit = unit;
        this.price = price;
    }

    public ReportItemType getId() {
        return id;
    }

    public void setId(ReportItemType id) {
        this.id = id;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
