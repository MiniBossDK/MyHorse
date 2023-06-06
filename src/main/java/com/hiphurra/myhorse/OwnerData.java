package com.hiphurra.myhorse;

import com.hiphurra.myhorse.managers.YamlDataFile;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;

public class OwnerData {

    static private OwnerData instance;

    private final static String pattern = "HH:mm:ss dd-MM-yyyy";

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


    OwnerData() {
        instance = this;
    }

    static public OwnerData instance() {
        return instance;
    }

    public void setCurrentHorseIdentifier(UUID ownerId, @Nullable UUID horseIdentifier) {
        if(horseIdentifier != null) setOwnersConfig(ownerId.toString() + CURRENT_HORSE, horseIdentifier.toString());
    }

    public @Nullable UUID getCurrentHorseIdentifier(UUID ownerId) throws IllegalArgumentException {
        String horseIdentifier = getOwnersConfig().getString(ownerId.toString() + CURRENT_HORSE);
        if(horseIdentifier != null) return UUID.fromString(horseIdentifier);
        else return null;
    }

    public List<UUID> getAllTrustedPlayers(UUID ownerId) {
        List<UUID> trustedPlayers = new ArrayList<>();
        for (UUID horseId : getHorses(ownerId).keySet()) {
            trustedPlayers.addAll(HorseData.getTrustedPlayers(horseId));
        }
        return trustedPlayers;
    }

    public void removeTrustOnAllHorses(UUID ownerId, UUID playerId) {
        for (UUID uuid : getHorses(ownerId).keySet()) {
            HorseData.removeTrustedPlayer(uuid, playerId);
        }
    }

    public void addTrustOnAllHorses(UUID ownerId, UUID playerId) {
        for (UUID uuid : getHorses(ownerId).keySet()) {
            HorseData.addTrustedPlayer(uuid, playerId);
        }
    }

    public void setLastLogin(UUID ownerId, Date lastLogin) {
        DateFormat formatter = new SimpleDateFormat(pattern);

        if (getOwnersConfig().contains(ownerId.toString())) {
            setOwnersConfig(ownerId.toString() + LAST_LOGIN, formatter.format(lastLogin));
        }
    }

    public Date getLastLoginDate(UUID ownerId) throws ParseException {
        return new SimpleDateFormat(pattern).parse(String.valueOf(getOwnersConfig().get(ownerId.toString() + LAST_LOGIN)));
    }

    // TODO - Could be nice if this was smaller and more elegant
    public Map<UUID, String> getHorses(UUID ownerId) throws IllegalArgumentException {

        if(horseConfig != null) {
            Map<UUID, String> horses = new HashMap<>();
            for (String horseIdString : horseConfig.getKeys(false)) {
                UUID horseId = UUID.fromString(horseIdString);
                UUID horseOwnerId = HorseData.getOwner(horseId);
                if(horseOwnerId.equals(ownerId)) {
                    String horseName = HorseData.getName(horseId);
                    if(horseName == null) continue;
                    horses.put(horseId, horseName);
                }
            }
            return horses;
        }
        return Collections.emptyMap();
    }

    public boolean hasHorseFromId(UUID ownerId, UUID horseId) {
        return getHorses(ownerId).containsKey(horseId);
    }

    public boolean hasHorseFromName(UUID ownerId, String name) {
        return getHorses(ownerId).containsValue(name);
    }

    public List<SalesRecord> getSalesRecords(UUID ownerId) {
        List<SalesRecord> salesRecords = new ArrayList<>();
        ConfigurationSection section = getOwnersConfig().getConfigurationSection(ownerId.toString() + SALES_RECORDS);
        if(section == null) return Collections.emptyList();
        Set<String> uuids = section.getKeys(false);
        for (String uuid : uuids) {
            try {
                salesRecords.add(new SalesRecord(
                        UUID.fromString(uuid),
                        getOwnersConfig().getDouble(ownerId.toString() + SALES_RECORDS + "." + uuid + SALES_RECORDS_PRICE),
                        getOwnersConfig().getString(ownerId.toString() + SALES_RECORDS + "." + uuid + SALES_RECORDS_BUYER),
                        new SimpleDateFormat(pattern).parse(getOwnersConfig().getString(ownerId.toString() + SALES_RECORDS + "." + uuid + SALES_RECORDS_DATE)),
                        getOwnersConfig().getString(ownerId.toString()  + SALES_RECORDS + "." + uuid + SALES_RECORDS_HORSENAME),
                        getOwnersConfig().getBoolean(ownerId.toString() + SALES_RECORDS + "." + uuid + SALES_RECORDS_SEEN)
                ));
            } catch(ParseException exception) {
                plugin.logDebug(Level.SEVERE, "Could not parse dateformat: " + getOwnersConfig().getString(ownerId.toString() + SALES_RECORDS + "." + uuid + SALES_RECORDS_DATE) + "!");
            } catch (IllegalArgumentException exception) {
                plugin.logDebug(Level.SEVERE, "Could not parse UUID: " + uuid);
            }
        }
        return salesRecords;
    }

    public void setSalesRecord(UUID ownerId, SalesRecord salesRecord) {
        DateFormat formatter = new SimpleDateFormat(pattern);

        setOwnersConfig(ownerId.toString() + SALES_RECORDS + "." + salesRecord.getRecordId() + SALES_RECORDS_HORSENAME, salesRecord.getHorseName());
        setOwnersConfig(ownerId.toString() + SALES_RECORDS + "." + salesRecord.getRecordId() + SALES_RECORDS_PRICE,     salesRecord.getPrice());
        setOwnersConfig(ownerId.toString() + SALES_RECORDS + "." + salesRecord.getRecordId() + SALES_RECORDS_BUYER,     salesRecord.getBuyerName());
        setOwnersConfig(ownerId.toString() + SALES_RECORDS + "." + salesRecord.getRecordId() + SALES_RECORDS_DATE,      formatter.format(salesRecord.getDate()));
        setOwnersConfig(ownerId.toString() + SALES_RECORDS + "." + salesRecord.getRecordId() + SALES_RECORDS_SEEN,      salesRecord.isSeen());

    }

    public void deleteSalesRecord(UUID ownerId, UUID recordId) {
        for (SalesRecord salesRecord : getSalesRecords(ownerId)) {
            if(!salesRecord.getRecordId().equals(recordId)) {
                setSalesRecord(ownerId, salesRecord);
            }
        }
    }

    public void setBuying(UUID ownerId, boolean buying) {
        setOwnersConfig(ownerId.toString() + BUYING, buying);
    }

    public boolean isBuying(UUID ownerId) {
        return getOwnersConfig().getBoolean(ownerId.toString() + BUYING);
    }

    public void setAreaMode(UUID ownerId, boolean areaMode) {
        setOwnersConfig(ownerId.toString() + AREA_MODE_STATE, areaMode);
    }

    public boolean isDefiningArea(UUID ownerId) {
        return getOwnersConfig().getBoolean(ownerId.toString() + AREA_MODE_STATE);
    }

    public void setStartLocation(UUID ownerId, Location location) {
        setOwnersConfig(ownerId.toString() + AREA_MODE_LOCATION, location);
    }

    public Location getStartLocation(UUID ownerId) {
        return getOwnersConfig().getLocation(ownerId.toString() + AREA_MODE_LOCATION);
    }

    private FileConfiguration getOwnersConfig() {
        return YamlDataFile.instance().loadConfig(com.hiphurra.myhorse.enums.YamlFile.OWNERS, );
    }

    private void setOwnersConfig(String path, Object value) {
        getOwnersConfig().set(path, value);
    }

    private void loadOwnerConfig() {
        YamlConfiguration.loadConfiguration(new File(getDataFolder(), configFile.getFileName()));
    }
}
