package com.hiphurra.myhorse.enums;

public enum YamlFile {

    OWNERS("owners.yml"),
    HORSES("horses.yml"),
    STABLES("stables.yml");


    private final String fileName;

    YamlFile(String fileName) {
        this.fileName = fileName;
    }


    public String getFileName() {
        return fileName;
    }
}
