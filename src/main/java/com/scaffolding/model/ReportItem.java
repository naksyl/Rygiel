package com.scaffolding.model;

import javax.persistence.*;

@Entity
@Table(name = "report_item")
public class ReportItem {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "report_id")
    private Report report;

    private ReportItemType type;
    private String unit;
    private int pieces;
    private double price;

    public ReportItem() {
    }

    public ReportItem(ReportItemType type, String unit, int pieces, double price) {
        this.type = type;
        this.unit = unit;
        this.pieces = pieces;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ReportItemType getType() {
        return type;
    }

    public void setType(ReportItemType type) {
        this.type = type;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getPieces() {
        return pieces;
    }

    public void setPieces(int pieces) {
        this.pieces = pieces;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }
}
