package com.hiphurra.myhorse;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.util.NumberConversions;
import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SalesRecord implements ConfigurationSerializable {

    private final UUID recordId;
    private double price;
    private UUID buyerId;
    private Date date;
    private UUID horseId;
    private boolean hasSeen;
    private static final DateFormat formatter = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");

    public SalesRecord(double price, UUID buyer, Date date, UUID horseId, boolean hasSeen) {
        this.recordId = UUID.randomUUID();
        this.price = price;
        this.buyerId = buyer;
        this.date = date;
        this.horseId = horseId;
        this.hasSeen = hasSeen;
    }

    public SalesRecord(UUID recordId, double price, UUID buyer, Date date, UUID horseId, boolean hasSeen) {
        this.recordId = recordId;
        this.price = price;
        this.buyerId = buyer;
        this.date = date;
        this.horseId = horseId;
        this.hasSeen = hasSeen;
    }

    public UUID getRecordId() {
        return recordId;
    }

    public boolean isSeen() {
        return hasSeen;
    }

    public UUID getHorseId() {
        return horseId;
    }

    public Date getDate() {
        return date;
    }

    public double getPrice() {
        return price;
    }

    public UUID getBuyerId() {
        return buyerId;
    }

    public void setHasSeen(boolean seen) {
        this.hasSeen = seen;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setHorseId(UUID horseId) {
        this.horseId = horseId;
    }

    public void setBuyerId(UUID buyerId) {
        this.buyerId = buyerId;
    }

    @Override
    public @NotNull Map<String, Object> serialize() {

        Map<String, Object> data = new HashMap<>();
        String recordId = this.recordId.toString();

        data.put("id", recordId);
        data.put(recordId + ".horse_id", horseId.toString());
        data.put(recordId + ".buyer", buyerId.toString());
        data.put(recordId + ".price", price);
        data.put(recordId + ".date", formatter.format(date));
        data.put(recordId + ".seen", hasSeen);

        return data;
    }

    @NotNull
    public static SalesRecord deserialize(@NotNull Map<String, Object> args) throws ParseException {
        String recordId = (String) args.get("id");
        return new SalesRecord(
                UUID.fromString(recordId),
                (int) args.get(recordId + ".price"),
                UUID.fromString((String) args.get(recordId + ".buyer")),
                formatter.parse((String) args.get(recordId + ".date")),
                UUID.fromString((String) args.get(recordId + ".horse_id")),
                Boolean.parseBoolean((String) args.get(recordId + ".seen"))
        );
    }
}
