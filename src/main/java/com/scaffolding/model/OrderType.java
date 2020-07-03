package com.scaffolding.model;

public enum OrderType {
    ASSEMBLY("Montaż"),
    DISASSEMBLY("Demonataż"),
    REVIEW("Przegląd");

    private String name;

    OrderType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
