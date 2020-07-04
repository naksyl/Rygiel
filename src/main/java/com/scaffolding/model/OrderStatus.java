package com.scaffolding.model;

public enum OrderStatus {
    OPENED("otwarte"),
    FINISHED("wykonane"),
    NOT_PAYED("nieopłacone"),
    CLOSED("zamknięte");

    private String name;

    OrderStatus(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
