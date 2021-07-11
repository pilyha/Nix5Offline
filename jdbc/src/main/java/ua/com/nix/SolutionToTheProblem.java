package ua.com.nix;

import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.*;
import ua.com.nix.dao.impl.*;
import ua.com.nix.entity.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SolutionToTheProblem {
    public void run() {
        Properties props = new Properties();
        try (InputStream input = SolutionToTheProblem.class.getResourceAsStream("/jdbc.properties")) {
            props.load(input);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        String url = props.getProperty("url");

        try (Connection connection = DriverManager.getConnection(url, props)) {
            connection.setAutoCommit(false);
            LocationDaoImpl locationDao = new LocationDaoImpl(connection);
            RouteDaoImpl routeDao = new RouteDaoImpl(connection);
            ProblemDaoImpl problemDao = new ProblemDaoImpl(connection);
            SolutionDaoImpl solutionDao = new SolutionDaoImpl(connection);

            List<Solution> solutions = new ArrayList<>();
            List<Location> locations = locationDao.get();
            List<Problem> problems = problemDao.get();
            List<Route> routes = routeDao.get();

            DefaultDirectedWeightedGraph<String, DefaultEdge> graph = new DefaultDirectedWeightedGraph<>(DefaultEdge.class);
            for (Location location : locations) {
                graph.addVertex(location.getName());
            }
            for (Location location : locations) {
                for (Route route : routes) {
                    DefaultEdge edge = graph.addEdge(
                            locationDao.get(route.getFrom_id()).getName(),
                            locationDao.get(route.getTo_id()).getName());
                    if (edge != null) {
                        graph.setEdgeWeight(edge, route.getCost());
                    }
                }
            }

            for (Problem problem : problems) {
                    DijkstraShortestPath<String, DefaultEdge> dijkstraShortestPath = new DijkstraShortestPath<>(graph);
                    int cost = (int) dijkstraShortestPath.getPath(
                            locationDao.get(problem.getFrom_id()).getName(),
                            locationDao.get(problem.getTo_id()).getName())
                            .getWeight();
                    solutions.add(new Solution(problem.getId(), cost));
            }
            solutionDao.create(solutions);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}




