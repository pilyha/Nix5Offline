package ua.com.nix.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "teachers")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private Integer age;

    private String email;

    @OneToMany(mappedBy = "teacher")
    private List<Lesson> lessons;

    public Teacher() {
    }

    public Teacher(String firstName, String lastName, Integer age, String email, List<Lesson> lessons) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.lessons = lessons;
    }
}