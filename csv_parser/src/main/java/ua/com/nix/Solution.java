package ua.com.nix;

import ua.com.nix.csv.CSV;
import ua.com.nix.csv.Mapper;
import ua.com.nix.csv.Parser;
import ua.com.nix.model.User;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class Solution {
    public void run() {
        File csv = new File("csv_parser/src/main/resources/input.csv");
        CSV data = Parser.parse(csv);
        System.out.println("Search by number of row and number of column: " + data.get(0, 0));
        System.out.println("Search by number of row and name of column: " + data.get(0, "age"));
        System.out.println("Search all header: " + Arrays.toString(data.getHeader()));
        System.out.println("*************************");
        List<User> list = Mapper.map(data, User.class);
        System.out.println("List of objects from CSV:");
        list.forEach(System.out::println);
    }
}
