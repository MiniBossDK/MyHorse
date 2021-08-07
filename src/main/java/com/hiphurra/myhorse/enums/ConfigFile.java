package com.hiphurra.myhorse.enums;

public enum ConfigFile {

    OWNERS("owners.yml"),
    HORSES("horses.yml"),
    STABLES("stables.yml");


    private String fileName;

    ConfigFile(String fileName) {
        this.fileName = fileName;
    }


    public String getName() {
        return fileName;
    }
}
