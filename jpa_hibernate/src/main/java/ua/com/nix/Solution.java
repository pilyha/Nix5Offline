package ua.com.nix;

import org.hibernate.Session;
import org.hibernate.query.Query;
import ua.com.nix.model.Lesson;

import java.util.Optional;

public class Solution {

    public Optional<Lesson> getCloserLessonById(Session session, Integer studentId)
    {
        Query<Lesson> getLessonQuery = session.createQuery(
                "select l from Lesson l join " +
                        "l.group g join " +
                        "g.students st where st.id = ?1 and current_timestamp < l.lessonStart order by " +
                        "l.lessonStart",
                Lesson.class);

        getLessonQuery.setMaxResults(1);

        getLessonQuery.setParameter(1, studentId);
        if (getLessonQuery.list().size() == 0) {
            return Optional.empty();
        }
        return Optional.ofNullable(getLessonQuery.getSingleResult());
    }
}