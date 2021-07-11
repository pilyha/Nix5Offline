package ua.com.nix.dao.impl;

import ua.com.nix.dao.LocationDao;
import ua.com.nix.entity.Location;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LocationDaoImpl implements LocationDao {
    private final Connection connection;
    public LocationDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Location> get() {

        List<Location> locations = new ArrayList<>();

        try(PreparedStatement preparedStatement = connection.prepareStatement("select * from locations")) {

                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    Location location = new Location(id, name);
                    locations.add(location);
                }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return locations;
    }

    @Override
    public Location get(Integer id) {
        Location location;
        try(
            PreparedStatement preparedStatement = connection.prepareStatement("select * from locations where id = ?")) {
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            location = new Location(
                    resultSet.getInt("id"),
                    resultSet.getString("name")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return location;
    }
}