package ua.com.nix.model;

import lombok.Getter;
import lombok.Setter;
import ua.com.nix.annotation.CSVCell;

@Getter
@Setter
public class User {
    @CSVCell("firstName")
    private String firstName;
    @CSVCell("lastName")
    private String lastName;
    @CSVCell("age")
    private int age;
    @CSVCell("sex")
    private Sex sex;


    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                '}';
    }
}
