package ua.com.nix;

import ua.com.nix.csv.CSV;
import ua.com.nix.csv.Mapper;
import ua.com.nix.csv.Parser;
import ua.com.nix.model.User;
import java.util.Arrays;
import java.util.List;

public class Solution {
    private final String FILE_NAME = "input.csv";
    public void run() {
        Mapper mapper = new Mapper();
        Parser parser = new Parser();
        CSV data = parser.parse(FILE_NAME);
        System.out.println("Search by number of row and number of column: " + data.get(0, 0));
        System.out.println("Search by number of row and name of column: " + data.get(0, "firstName"));
        System.out.println("Search all header: " + Arrays.toString(data.getHeader()));
        System.out.println("*************************");
        List<User> list = mapper.map(data, User.class);
        System.out.println("List of objects from CSV:");
        list.forEach(System.out::println);
    }
}
