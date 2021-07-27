package ua.com.nix;

import org.hibernate.Session;
import ua.com.nix.model.Lesson;


public class Main {
    public static void main(String[] args) {
        Solution solution = new Solution();
        Session session = Connector.getSessionFactory().openSession();

        int studentId = 3;
        if (solution.getCloserLessonById(session, studentId).isPresent()) {
            Lesson lesson = solution.getCloserLessonById(session, studentId).get();
            System.out.println("Closer lesson by: " + studentId + "is " + lesson.getLessonName() +
                    ", started at " + lesson.getLessonStart() +
                    ", teacher " + lesson.getTeacher().getFirstName() + " " + lesson.getTeacher().getLastName() +
                    ", unit " + lesson.getUnit().getTitle() +
                    ", for group " + lesson.getGroup().getGroupName());
        }
    }




}
