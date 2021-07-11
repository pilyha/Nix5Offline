package ua.com.nix.dao;

import ua.com.nix.entity.Location;

import java.util.List;

public interface LocationDao {

    List<Location> get();
    Location get(Integer id);
}
