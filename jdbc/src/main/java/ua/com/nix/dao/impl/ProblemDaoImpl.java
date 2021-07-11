package ua.com.nix.dao.impl;

import ua.com.nix.dao.ProblemDao;
import ua.com.nix.entity.Problem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProblemDaoImpl implements ProblemDao {
    Connection connection;

    public ProblemDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Problem> get() {

        List<Problem> problems = new ArrayList<>();

        try(PreparedStatement preparedStatement = connection.prepareStatement("select * from problems left join solutions on problems.id = solutions.problem_id where solutions.* is null")) {

            ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    int from_id = resultSet.getInt("from_id");
                    int to_id = resultSet.getInt("to_id");
                    Problem problem = new Problem(id, from_id,to_id);
                    problems.add(problem);
                }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return problems;
    }

    @Override
    public Problem get(Integer id) {
        Problem problem;
        try(
                PreparedStatement preparedStatement = connection.prepareStatement("select * from problems where id = ?")) {
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            problem= new Problem(
                    resultSet.getInt("id"),
                    resultSet.getInt("from_id"),
                    resultSet.getInt("to_id")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return problem;
    }
}