package ua.com.nix.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "course_name",nullable = false)
    private String courseName;


    public Course() {
    }

    public Course(String courseName) {
        this.courseName = courseName;
    }
}
