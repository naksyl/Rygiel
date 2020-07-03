package com.scaffolding.model;

public enum ReportItemType {
    SCAFFOLDING_ASSEMBLY("Montaż rusztowania"),
    SCAFFOLDING_DISSAMBLY("Demontaż rusztowania");

    private String name;
    ReportItemType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
