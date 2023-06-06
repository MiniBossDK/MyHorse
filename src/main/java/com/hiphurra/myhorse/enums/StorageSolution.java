package com.hiphurra.myhorse.enums;

public enum StorageSolution {
    YAML("yaml"),
    SQLITE("sqlite");

    private final String name;

    public StorageSolution(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
