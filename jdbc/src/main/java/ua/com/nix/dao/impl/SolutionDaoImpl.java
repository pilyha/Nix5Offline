package ua.com.nix.dao.impl;

import ua.com.nix.dao.SolutionDao;
import ua.com.nix.entity.Solution;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SolutionDaoImpl implements SolutionDao {
   private final Connection connection;

    public SolutionDaoImpl(Connection connection) {
        this.connection = connection;
    }


    @Override
    public void create(List<Solution> solutions) {
        try(PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO solutions (problem_id, cost) VALUES (?, ?) ON CONFLICT DO NOTHING"))
        {
            for (Solution solution : solutions) {
                preparedStatement.setInt(1,solution.getProblem_id());
                preparedStatement.setInt(2,solution.getCost());

                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            connection.commit();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}