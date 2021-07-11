package ua.com.nix.dao;

import ua.com.nix.entity.Problem;
import ua.com.nix.entity.Route;

import java.util.List;

public interface RouteDao {

    List<Route> get();
    Route get(Integer id);
}
