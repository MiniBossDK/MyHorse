package com.hiphurra.myhorse;

import java.util.Date;
import java.util.UUID;

public class SalesRecord {

    private final UUID recordId;
    private double price;
    private String buyerName;
    private Date date;
    private String horseName;
    private boolean hasSeen;

    public SalesRecord(double price, String buyerName, Date date, String horseName, boolean hasSeen) {
        this.recordId = UUID.randomUUID();
        this.price = price;
        this.buyerName = buyerName;
        this.date = date;
        this.horseName = horseName;
        this.hasSeen = hasSeen;
    }

    public SalesRecord(UUID recordId, double price, String buyerName, Date date, String horseName, boolean hasSeen) {
        this.recordId = recordId;
        this.price = price;
        this.buyerName = buyerName;
        this.date = date;
        this.horseName = horseName;
        this.hasSeen = hasSeen;
    }

    public UUID getRecordId() {
        return recordId;
    }

    public boolean isSeen() {
        return hasSeen;
    }

    public String getHorseName() {
        return horseName;
    }

    public Date getDate() {
        return date;
    }

    public double getPrice() {
        return price;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setHasSeen(boolean seen) {
        this.hasSeen = seen;
    }

    public void setHorseName(String horseName) {
        this.horseName = horseName;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }
}
