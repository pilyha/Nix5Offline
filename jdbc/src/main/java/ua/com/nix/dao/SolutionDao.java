package ua.com.nix.dao;

import ua.com.nix.entity.Problem;
import ua.com.nix.entity.Solution;

import java.util.List;

public interface SolutionDao {

    void create(List<Solution> solutions);
}
