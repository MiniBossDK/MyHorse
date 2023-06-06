package com.hiphurra.myhorse.dao;

import com.hiphurra.myhorse.customhorses.CustomAbstractHorse;
import com.hiphurra.myhorse.managers.HorseDao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CustomHorseDao implements HorseDao {

    @Override
    public Optional<CustomAbstractHorse> get(UUID uuid) {

        return Optional.ofNullable();
    }

    @Override
    public List<CustomAbstractHorse> getAll() {
        return null;
    }

    @Override
    public void save(CustomAbstractHorse customHorse) {

    }

    @Override
    public void update(CustomAbstractHorse customHorse) {

    }

    @Override
    public void delete(CustomAbstractHorse customHorse) {

        customHorse.getId();
    }
}
