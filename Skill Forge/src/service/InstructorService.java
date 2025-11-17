package service;
import managers.CourseJsonManager;
import managers.UserJsonManager;
import models.*;

import java.util.ArrayList;
import java.util.List;

public class InstructorService {
    private UserJsonManager userJsonManager;
    private CourseJsonManager courseJsonManager;

    public InstructorService(UserJsonManager userJsonManager, CourseJsonManager courseJsonManager){
        this.userJsonManager = userJsonManager;
        this.courseJsonManager = courseJsonManager;
    }

    private int generateCourseId() {
        return courseJsonManager.getAllCousres().size() + 1;
    }

    public Course createCourse(String instructorId, String title, String description) {
        try {

            Course c = new Course(courseId, title, description, instructorId);
            ArrayList<Course> courses = courseJsonManager.loadFromJson();
            courses.add(c);
            courseJsonManager.saveToJson();                          //.....

            ArrayList<User> users = userJsonManager.loadFromJson(); //............
            for (User i : users) {
                if (i.getUserId().equals(instructorId) && i instanceof Instructor) {
                    ((Instructor) i).getCreatedCourses().add(courseId);
                }
            }
            userJsonManager.saveToJson();
            return c;
        }
        catch (Exception e) {
            System.out.println("Error in creating a course !");
            return null;
        }
    }

    public Course editCourse(int courseId, String newTitle, String newDescription) {
        try {
            ArrayList<Course> courses = courseJsonManager.loadFromJson();

            for (Course c : courses) {
                if (c.getCourseId()==courseId) {
                    c.setTitle(newTitle);
                    c.setDescription(newDescription);
                    courseJsonManager.saveToJson();
                    return c;
                }
            }
            return null;
        }
        catch (Exception e) {
            System.out.println("Error editing course: " + e.getMessage());
            return null;
        }
    }

    public boolean deleteCourse(int instructorId, int courseId) {

        Instructor inst = (Instructor) userJsonManager.findById(instructorId);
        if (inst == null) return false;

        Course cr = courseJsonManager.findById(courseId);
        if (cr == null) return false;

        if (cr.getInstructorId() != instructorId) {                   // byshof el course dah bta3 el instructor wala la
            System.out.println("Instructor does not own this course.");
            return false;
        }
        inst.getCreatedCourses().remove(Integer.valueOf(courseId));

        List<Course> allCourses = courseJsonManager.getAll();
        allCourses.removeIf(c -> c.getCourseId() == courseId);

        courseJsonManager.saveToJson();
        userJsonManager.saveToJson();
        return true;
    }

//    public Lesson addLesson(int lessonId, String title, String content) {
//        try {
//            Lesson lesson = new Lesson(lessonId, title, content);
//
//            ArrayList<Course> courses = courseJsonManager.loadCourses();
//
//            for (Course c : courses) {
//                if (c.getCourseId().equals()) {
//
//
//                    l.getLessons().add(lesson);
//                    courseJsonManager.saveCourses(courses);
//                    return lesson;
//                }
//            }
//            return null;
//
//        } catch (Exception e) {
//            System.out.println("Error adding lesson: " + e.getMessage());
//            return null;
//        }
//    }

    public void addLesson(int courseId, String title, String content) {
        try {
            Course c = courseJsonManager.findById(courseId);
            if (c == null) return;

            int lessonId = c.getLessons().size() + 1;
            models.Lesson l = new models.Lesson(lessonId, title, content);

            c.getLessons().add(l);
            courseJsonManager.saveToJson();
        }
        catch (Exception e){
            System.out.println("Error adding lesson: " + e.getMessage());
            return ;
        }

    }

    public void editLesson(int courseId, int lessonId, String newTitle, String newContent) {
        Course c = courseJsonManager.getCourseId(courseId);
        if (c == null) return;

        models.Lesson l = c.getLessonId(lessonId);
        if (l == null) return;

        l.setTitle(newTitle);
        l.setContent(newContent);

        courseJsonManager.saveToFile();
    }

    public void deleteLesson(int courseId, int lessonId) {
        Course c = courseJsonManager.findById(courseId);
        if (c == null) return;

        c.getLessons().removeIf(l -> l.getLessonId() == lessonId);
        courseJsonManager.saveToJson();
    }

    public List<Course>courses getInstructorCourses( int instructorId){

        ArrayList<Course>allCourses = courseJsonManager.getAll();
        List<Course>instructorCourses = new ArrayList<>();

        for (Course c : allCourses){
            if (instructorId == c.getInstructorId()){
                instructorCourses.add(c);
            }
        }
        return instructorCourses;
    }

    public List<Student> viewEnrolledStudents(int courseId) {
        Course c = courseJsonManager.findById(courseId);
        if (c == null) return new ArrayList<>();

        List<Student> result = new ArrayList<>();

        for (Student studentId : c.getStudents()) {
            User u = userJsonManager.findById(studentId.getUserId());
            if (u instanceof Student) {
                result.add((Student) u);
            }
        }
        return result;
    }






}