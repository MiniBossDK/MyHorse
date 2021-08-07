package com.hiphurra.myhorse;

import com.hiphurra.myhorse.enums.ConfigFile;
import com.hiphurra.myhorse.managers.ConfigManager;
import com.hiphurra.myhorse.stable.StableData;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Horse;
import org.jetbrains.annotations.Nullable;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;

public class OwnerData {

    private final MyHorse plugin;
    private final ConfigManager ownerConfig;
    private final UUID ownerId;

    private final String pattern = "HH:mm:ss dd-MM-yyyy";

    private static final String CURRENT_HORSE = ".CurrentHorse";
    private static final String LAST_LOGIN = ".LastLogin";
    private static final String BUYING = ".buying";
    private static final String AREA_MODE = ".AreaMode";
        private static final String AREA_MODE_STATE =  AREA_MODE + ".state";
        private static final String AREA_MODE_LOCATION = AREA_MODE + ".location";
    private static final String SALES_RECORDS = ".SalesRecords";
        private static final String SALES_RECORDS_HORSENAME = ".horsename";
        private static final String SALES_RECORDS_BUYER = ".buyer";
        private static final String SALES_RECORDS_PRICE = ".price";
        private static final String SALES_RECORDS_DATE = ".date";
        private static final String SALES_RECORDS_SEEN = ".seen";


    public OwnerData(MyHorse plugin, UUID ownerId) {
        this.plugin = plugin;
        this.ownerConfig = plugin.getConfigManager(ConfigFile.OWNERS);
        this.ownerId = ownerId;
    }

    public void setCurrentHorseIdentifier(@Nullable UUID horseIdentifier) {
        if(horseIdentifier != null) setOwnersConfig(getOwnerId().toString() + CURRENT_HORSE, horseIdentifier.toString());
    }

    public @Nullable UUID getCurrentHorseIdentifier() throws IllegalArgumentException {
        String horseIdentifier = getOwnersConfig().getString(getOwnerId().toString() + CURRENT_HORSE);
        if(horseIdentifier != null) return UUID.fromString(horseIdentifier);
        else return null;
    }

    public List<UUID> getAllTrustedPlayers() {
        List<UUID> trustedPlayers = new ArrayList<>();
        for (UUID horseId : getHorses().keySet()) {
            trustedPlayers.addAll(new HorseData(plugin, horseId).getTrustedPlayers());
        }
        return trustedPlayers;
    }

    public void removeTrustOnAllHorses(UUID playerId) {
        for (UUID uuid : getHorses().keySet()) {
            new HorseData(plugin, uuid).removeTrustedPlayer(playerId);
        }
    }

    public void addTrustOnAllHorses(UUID playerId) {
        for (UUID uuid : getHorses().keySet()) {
            HorseData horseData = new HorseData(plugin, uuid);
            horseData.addTrustedPlayer(playerId);
        }
    }

    public void setLastLogin(Date lastLogin) {
        DateFormat formatter = new SimpleDateFormat(pattern);

        if (getOwnersConfig().contains(getOwnerId().toString())) {
            setOwnersConfig(getOwnerId().toString() + LAST_LOGIN, formatter.format(lastLogin));
        }
    }

    public Date getLastLoginDate() throws ParseException {
        return new SimpleDateFormat(pattern).parse(String.valueOf(getOwnersConfig().get(getOwnerId().toString() + LAST_LOGIN)));
    }

    // TODO - Could be nice if this was smaller and more elegant
    public Map<UUID, String> getHorses() throws IllegalArgumentException {
        ConfigManager horseConfig = plugin.getConfigManager(ConfigFile.HORSES);

        if(horseConfig != null) {
            Map<UUID, String> horses = new HashMap<>();
            for (String horseId : horseConfig.getKeys(false)) {
                HorseData horseData = new HorseData(plugin, UUID.fromString(horseId));
                UUID horseOwner = horseData.getOwner();
                if(horseOwner.equals(getOwnerId())) {
                    String horseName = horseData.getName();
                    if(horseName == null) continue;
                    horses.put(horseData.getHorseId(), horseName);
                }
            }
            return horses;
        }
        return Collections.emptyMap();
    }

    public boolean hasHorseId(UUID horseId) {
        return getHorses().containsKey(horseId);
    }

    public boolean hasHorseName(String name) {
        return getHorses().containsValue(name);
    }

    public List<SalesRecord> getSalesRecords() {
        List<SalesRecord> salesRecords = new ArrayList<>();
        ConfigurationSection section = getOwnersConfig().getConfigurationSection(getOwnerId().toString() + SALES_RECORDS);
        if(section == null) return Collections.emptyList();
        Set<String> uuids = section.getKeys(false);
        for (String uuid : uuids) {
            try {
                salesRecords.add(new SalesRecord(
                        UUID.fromString(uuid),
                        getOwnersConfig().getDouble(getOwnerId().toString() + SALES_RECORDS + "." + uuid + SALES_RECORDS_PRICE),
                        getOwnersConfig().getString(getOwnerId().toString() + SALES_RECORDS + "." + uuid + SALES_RECORDS_BUYER),
                        new SimpleDateFormat(pattern).parse(getOwnersConfig().getString(getOwnerId().toString() + SALES_RECORDS + "." + uuid + SALES_RECORDS_DATE)),
                        getOwnersConfig().getString(getOwnerId().toString()  + SALES_RECORDS + "." + uuid + SALES_RECORDS_HORSENAME),
                        getOwnersConfig().getBoolean(getOwnerId().toString() + SALES_RECORDS + "." + uuid + SALES_RECORDS_SEEN)
                ));
            } catch(ParseException exception) {
                plugin.logDebug(Level.SEVERE, "Could not parse dateformat: " + getOwnersConfig().getString(getOwnerId().toString() + SALES_RECORDS + "." + uuid + SALES_RECORDS_DATE) + "!");
            } catch (IllegalArgumentException exception) {
                plugin.logDebug(Level.SEVERE, "Could not parse UUID: " + uuid);
            }
        }
        return salesRecords;
    }

    public void setSalesRecord(SalesRecord salesRecord) {
        DateFormat formatter = new SimpleDateFormat(pattern);

        setOwnersConfig(getOwnerId().toString() + SALES_RECORDS + "." + salesRecord.getRecordId() + SALES_RECORDS_HORSENAME, salesRecord.getHorseName());
        setOwnersConfig(getOwnerId().toString() + SALES_RECORDS + "." + salesRecord.getRecordId() + SALES_RECORDS_PRICE,     salesRecord.getPrice());
        setOwnersConfig(getOwnerId().toString() + SALES_RECORDS + "." + salesRecord.getRecordId() + SALES_RECORDS_BUYER,     salesRecord.getBuyerName());
        setOwnersConfig(getOwnerId().toString() + SALES_RECORDS + "." + salesRecord.getRecordId() + SALES_RECORDS_DATE,      formatter.format(salesRecord.getDate()));
        setOwnersConfig(getOwnerId().toString() + SALES_RECORDS + "." + salesRecord.getRecordId() + SALES_RECORDS_SEEN,      salesRecord.isSeen());

    }

    public void deleteSalesRecord(UUID recordId) {
        for (SalesRecord salesRecord  : getSalesRecords()) {
            if(!salesRecord.getRecordId().equals(recordId)) {
                setSalesRecord(salesRecord);
            }
        }
    }

    public void setBuying(boolean buying) {
        setOwnersConfig(getOwnerId().toString() + BUYING, buying);
    }

    public boolean isBuying() {
        return getOwnersConfig().getBoolean(getOwnerId().toString() + BUYING);
    }

    public void setAreaMode(boolean areaMode) {
        setOwnersConfig(getOwnerId().toString() + AREA_MODE_STATE, areaMode);
    }

    public boolean isDefiningArea() {
        return getOwnersConfig().getBoolean(getOwnerId().toString() + AREA_MODE_STATE);
    }

    public void setStartLocation(Location location) {
        setOwnersConfig(getOwnerId().toString() + AREA_MODE_LOCATION, location);
    }

    public Location getStartLocation() {
        return getOwnersConfig().getLocation(getOwnerId().toString() + AREA_MODE_LOCATION);
    }

    public StableData getStableData() {
        return new StableData(plugin, getOwnerId());
    }

    private ConfigManager getOwnersConfig() {
        return ownerConfig;
    }

    private void setOwnersConfig(String path, Object value) {
        getOwnersConfig().set(path, value);
    }

    public UUID getOwnerId() {
        return ownerId;
    }
}
