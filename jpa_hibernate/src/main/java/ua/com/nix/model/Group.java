package ua.com.nix.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "groups")
public class Group {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_group",nullable = false)
    private String groupName;

    @OneToMany(mappedBy = "group")
    private List<Student> students;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToMany(mappedBy = "group")
    private List<Lesson> lessons;

    public Group(String groupName, List<Student> students, Course course, List<Lesson> lessons) {
        this.groupName = groupName;
        this.students = students;
        this.course = course;
        this.lessons = lessons;
    }

    public Group() {

    }
}
