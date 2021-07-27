package ua.com.nix.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "lessons")
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "lesson_name")
    private String lessonName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id")
    private Unit unit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @OneToMany(mappedBy = "lesson")
    private List<Grade> grades;

    @Column(name = "start_lesson", nullable = false)
    private LocalDateTime lessonStart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    public Lesson() {
    }

    public Lesson(String lessonName, Unit unit, Teacher teacher, List<Grade> grades, LocalDateTime lessonStart, LocalDateTime lessonEnd, Group group) {
        this.lessonName = lessonName;
        this.unit = unit;
        this.teacher = teacher;
        this.grades = grades;
        this.lessonStart = lessonStart;
        this.group = group;
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "id=" + id +
                ", lessonName='" + lessonName + '\'' +
                ", unit=" + unit +
                ", teacher=" + teacher.getFirstName() + teacher.getLastName() +
                ", grades=" + grades +
                ", lessonStart=" + lessonStart +
                ", group=" + group +
                '}';
    }
}
