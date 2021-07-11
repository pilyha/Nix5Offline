package ua.com.nix.dao.impl;

import ua.com.nix.dao.RouteDao;
import ua.com.nix.entity.Route;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RouteDaoImpl implements RouteDao {
    private final Connection connection;

    public RouteDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Route> get() {

        List<Route> routes = new ArrayList<>();

        try(PreparedStatement preparedStatement = connection.prepareStatement("select * from routes")) {

            ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    int from_id = resultSet.getInt("from_id");
                    int to_id = resultSet.getInt("to_id");
                    int cost = resultSet.getInt("cost");
                    Route route = new Route(id, from_id,to_id,cost);
                    routes.add(route);
                }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return routes;
    }

    @Override
    public Route get(Integer id) {
        Route route;
        try(
                PreparedStatement preparedStatement = connection.prepareStatement("select * from routes where id = ?")) {
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            route = new Route(
                    resultSet.getInt("id"),
                    resultSet.getInt("from_id"),
                    resultSet.getInt("to_id"),
                    resultSet.getInt("cost")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return route;
    }
}