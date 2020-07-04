package com.scaffolding.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "report")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "report")
    private Orders order;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "report", cascade = CascadeType.ALL)
    private List<ReportItem> items;

    private LocalDate date;

    public Report() {
        date = LocalDate.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Orders getOrder() {
        return order;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }

    public List<ReportItem> getItems() {
        if (items == null) items = new ArrayList<>();
        return items;
    }

    public void setItems(List<ReportItem> items) {
        this.items = items;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void addReportItem(ReportItem item) {
        if (items == null) {
            items = new ArrayList<>();
        }
        item.setReport(this);
        items.add(item);
    }
}
