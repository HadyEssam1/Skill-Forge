package service;
import managers.UserJsonManager;
import models.Course;
import models.Instructor;
import models.Lesson;
import models.User;

import java.util.ArrayList;
import java.util.UUID;

public class InstructorService {

    public Course createCourse(String instructorId, String title, String description) {
        try {
            if (title == null || title.isEmpty() || description == null || description.isEmpty()) {
                System.out.println("All fields must be filled !");
                return null;
            }
            String courseId = UUID.randomUUID().toString();

            Course c = new Course(courseId, title, description, instructorId);
            ArrayList<Course> courses = UserJsonManager.loadFromFiles();
            courses.add(c);
            courses.saveToFile();                          //.....

            ArrayList<User> users = UserJsonManager.loadFromFiles(); //............
            for (User i : users) {
                if (i.getUserId().equals(instructorId) && i instanceof Instructor) {
                    ((Instructor) i).getCreatedCourses().add(courseId);
                }
            }
            userJsonManager.saveUsers(users);
            return course;
        }
        catch (Exception e) {
            System.out.println("Error in creating a course !");
            return null;
        }
    }


    public boolean editCourse(String courseId, String newTitle, String newDescription) {
        try {
            ArrayList<Course> courses = courseJsonManager.loadCourses();

            for (Course c : courses) {
                if (c.getCourseId().equals(courseId)) {
                    c.setTitle(newTitle);
                    c.setDescription(newDescription);
                    courseJsonManager.saveCourses(courses);
                    return true;
                }
            }

            return false;

        }
        catch (Exception e) {
            System.out.println("Error editing course: " + e.getMessage());
            return false;
        }
    }

    public Lesson addLesson(String courseId, String title, String content) {
        try {
            ArrayList<Course> courses = courseJsonManager.loadCourses();

            for (Course c : courses) {
                if (c.getCourseId().equals(courseId)) {

                    String lessonId = UUID.randomUUID().toString();
                    Lesson lesson = new Lesson(lessonId, title, content);

                    c.getLessons().add(lesson);
                    courseJsonManager.saveCourses(courses);
                    return lesson;
                }
            }
            return null;

        } catch (Exception e) {
            System.out.println("Error adding lesson: " + e.getMessage());
            return null;
        }
    }


}