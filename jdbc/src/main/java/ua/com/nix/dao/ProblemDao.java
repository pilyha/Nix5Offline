package ua.com.nix.dao;

import ua.com.nix.entity.Location;
import ua.com.nix.entity.Problem;

import java.util.List;

public interface ProblemDao {

    List<Problem> get();
    Problem get(Integer id);
}
