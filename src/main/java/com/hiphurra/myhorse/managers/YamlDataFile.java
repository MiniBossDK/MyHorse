package com.hiphurra.myhorse.managers;

import com.hiphurra.myhorse.AbstractHorseOwner;
import com.hiphurra.myhorse.customhorses.CustomAbstractHorse;
import com.hiphurra.myhorse.customhorses.CustomChestedHorse;
import com.hiphurra.myhorse.customhorses.CustomHorse;
import com.hiphurra.myhorse.customhorses.CustomSpecialHorse;
import com.hiphurra.myhorse.stable.AbstractStable;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.AbstractHorse;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class YamlDataFile {

    private final File file;
    private final FileConfiguration yamlFile;

    public YamlDataFile(File file) throws IOException {
        this.file = file;
        this.yamlFile = loadConfig(file);
    }

    private FileConfiguration loadConfig(File file) throws IllegalArgumentException, IOException {
        FileConfiguration yamlFile = YamlConfiguration.loadConfiguration(file);

        save();
        return yamlFile;
    }

    public CustomAbstractHorse<?extends AbstractHorse> getAbstractHorseData(UUID horseId) {
        return null;
    }

    public CustomChestedHorse getChestedHorseData(UUID horseId) {
        return null;
    }

    public CustomSpecialHorse getCustomSpecialHorseData(UUID horseId) {
        return null;
    }

    public void saveHorseData(CustomAbstractHorse<?extends AbstractHorse> customAbstractHorse) {
        String horseId = customAbstractHorse.getOwnerId().toString();

        yamlFile.set(horseId + "." + "name", customAbstractHorse.getName());
        yamlFile.set(horseId + "." + "location", customAbstractHorse.getCurrentLocation());
        yamlFile.set(horseId + "." + "trusted_players", customAbstractHorse.getTrustedPlayers());
        yamlFile.set(horseId + "." + "locked", customAbstractHorse.isLocked());
        yamlFile.set(horseId + "." + "inventory", customAbstractHorse.hasInventory());
        yamlFile.set(horseId + "." + "saddle", customAbstractHorse.hasSaddle());
        yamlFile.set(horseId + "." + "type", customAbstractHorse.getType().toString());

        if(customAbstractHorse instanceof CustomChestedHorse) {
            saveChestedHorseData((CustomChestedHorse) customAbstractHorse);
        }
        else if(customAbstractHorse instanceof CustomSpecialHorse) {
            saveSpecialHorseData((CustomSpecialHorse) customAbstractHorse);
        }
        else {
            saveHorseData((CustomHorse) customAbstractHorse);
        }
    }

    private void saveChestedHorseData(CustomChestedHorse customChestedHorse) {
        String horseId = customChestedHorse.getOwnerId().toString();

    }

    private void saveSpecialHorseData(CustomSpecialHorse customSpecialHorse) {

    }

    private void saveHorseData(CustomHorse customHorse) {

    }

    public void saveHorseOwnerData(AbstractHorseOwner abstractHorseOwner) {
        String ownerId = abstractHorseOwner.getId().toString();

        yamlFile.set(ownerId + "." + "selected-horse", abstractHorseOwner.getSelectedHorse());
        yamlFile.set(ownerId + "." + "selected-horse", abstractHorseOwner.getSelectedHorse());
    }

    public void saveStableData(AbstractStable abstractStable) {

    }

    private void set(String path, Object object) throws IOException {
        yamlFile.set(path, object);
        save();
    }

    private void save() throws IOException {
        yamlFile.save(file);
    }

    public FileConfiguration getYamlFile() {
        return yamlFile;
    }
}
