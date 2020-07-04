package com.scaffolding.model;

import javax.persistence.*;

@Entity
@Table(name = "report_item")
public class ReportItem {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "report_id")
    private Report report;

    private ReportItemType type;
    private double pieces;
    private double price;

    public ReportItem() {
    }

    public ReportItem(ReportItemType type,double pieces) {
        this.type = type;
        this.pieces = pieces;
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

    public double getPieces() {
        return pieces;
    }

    public void setPieces(double pieces) {
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

    @Override
    public String toString() {
        return type.toString() + " " + pieces+ type.getUnit();
    }
}
