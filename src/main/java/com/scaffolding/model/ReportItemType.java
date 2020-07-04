package com.scaffolding.model;

public enum ReportItemType {
    SCAFFOLDING_ASSEMBLY("Montaż rusztowania"),
    SCAFFOLDING_DISASSEMBLY("Demontaż rusztowania"),
    SCAFFOLDING_REVIEW("Przegląd rusztowania"),
    WORKING_HOUR("Roboczogodzina");

    private String name;

    ReportItemType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
    public String getUnit() {
        switch (this) {
            case SCAFFOLDING_ASSEMBLY:
            case SCAFFOLDING_DISASSEMBLY: return "m²";
            default: return "h";
        }
    }
}
