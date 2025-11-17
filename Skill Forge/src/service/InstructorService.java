package service;

import managers.CourseJsonManager;
import managers.UserJsonManager;
import models.*;

import java.util.List;
import java.util.ArrayList;

public class InstructorService {

    private CourseJsonManager courseManager;
    private UserJsonManager userManager;

    public InstructorService(CourseJsonManager courseManager, UserJsonManager userManager) {
        this.courseManager = courseManager;
        this.userManager = userManager;
    }

    private int generateCourseId() {
        return courseManager.getAll().size() + 1;
    }

    public Course createCourse(int instructorId, String title, String description) {
        int newId = generateCourseId();
        Course c = new Course(newId, title, description, instructorId);
        courseManager.add(c);

        User u = userManager.getById(instructorId);
        if (u instanceof Instructor) {
            Instructor inst = (Instructor) u;
            inst.assignCourse(newId);
            userManager.save();
        }
        courseManager.save();
        return c;
    }

    public void editCourse(int courseId, String newTitle, String newDescription) {
        Course c = courseManager.getById(courseId);
        if (c == null) return;

        c.setTitle(newTitle);
        c.setDescription(newDescription);

        courseManager.save();
    }

    public void deleteCourse(int instructorId, int courseId) {
        Course c = courseManager.getById(courseId);
        if (c == null) return;

        courseManager.delete(c);
        courseManager.save();

        User u = userManager.getById(instructorId);
        if (u instanceof Instructor) {
            Instructor inst = (Instructor) u;
            inst.leaveCourse(courseId);
            userManager.save();
        }
    }

    public void addLesson(int courseId, String title, String content) throws Exception {
        Course c = courseManager.getById(courseId);
        if (c == null)
            throw new Exception("Course is not exit");
        int lessonId = c.getLessons().size() + 1;
        Lesson l = new Lesson(lessonId, title, content);
        c.addLesson(l);
        courseManager.save();
    }

    public void editLesson(int courseId, int lessonId, String newTitle, String newContent) throws Exception {
        Course c = courseManager.getById(courseId);
        if (c == null)
            throw new Exception("Course is not exit");
        models.Lesson l = c.getLessonById(lessonId);
        if (l == null)
            throw new Exception("lesson is not exit");
        l.setTitle(newTitle);
        l.setContent(newContent);

        courseManager.save();
    }

    public void deleteLesson(int courseId, int lessonId) {
        Course c = courseManager.getById(courseId);
        if (c == null) return;

        c.getLessons().removeIf(l -> l.getLessonId() == lessonId);

        courseManager.save();
    }

    public List<Course> getInstructorCourses(int instructorId) {
        User u = userManager.getById(instructorId);
        if (!(u instanceof Instructor)) return new ArrayList<>();

        Instructor inst = (Instructor) u;

        List<Course> result = new ArrayList<>();
        for (int id : inst.getCoursesTeaching()) {
            Course c = courseManager.getById(id);
            if (c != null) result.add(c);
        }

        return result;
    }

    public List<Student> viewEnrolledStudents(int courseId) {
        Course c = courseManager.getById(courseId);
        if (c == null) return new ArrayList<>();

        List<Student> result = new ArrayList<>();

        for (Integer studentid : c.getStudentIds()) {
            User u = userManager.getById(studentid);
            if (u instanceof Student) {
                result.add((Student) u);
            }
        }

        return result;
    }
}
