package com.scaffolding.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "bill")
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH},
            fetch = FetchType.EAGER,
            mappedBy = "bill")
    private Orders order;

    private String number;
    private LocalDate date;
    private String payed;

    public Bill() {
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getPayed() {
        return payed;
    }

    public void setPayed(String payed) {
        this.payed = payed;
    }

    public double getTotalPrice() {
        double p = 0;
        List<ReportItem> items = order.getReport().getItems();
        if (items != null) {
            for (ReportItem item : items) {
                p += item.getPrice();
            }
        }
        return p;
    }
}
