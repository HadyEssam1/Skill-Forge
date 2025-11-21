package service;

import managers.UserJsonManager;
import managers.CourseJsonManager;
import models.Student;
import models.Course;
import models.Lesson;

import java.util.List;
import java.util.ArrayList;

public class StudentService {

    private UserJsonManager userManager;
    private CourseJsonManager courseManager;

    public StudentService(UserJsonManager userManager, CourseJsonManager courseManager) {
        this.userManager = userManager;
        this.courseManager = courseManager;
    }

    public boolean enrollInCourse(int studentId, int courseId) throws Exception {
        Student st = (Student) userManager.getById(studentId);
        Course cr = courseManager.getById(courseId);
        if (st == null || cr == null) return false;

        cr.enrollStudent(st.getUserId());
        st.addCourse(courseId);

        courseManager.save();
        userManager.save();
        return true;
    }

    public boolean dropCourse(int studentId, int courseId) throws Exception {
        Student st = (Student) userManager.getById(studentId);
        Course cr = courseManager.getById(courseId);

        if (st == null || cr == null) return false;
        cr.removeStudent(st.getUserId());
        st.dropCourse(courseId);

        courseManager.save();
        userManager.save();
        return true;
    }

    public List<Course> viewEnrolledCourses(int studentId) throws Exception {
        Student st = (Student) userManager.getById(studentId);
        if (st == null)
            throw new Exception("Student2 not Found ");

        List<Course> result = new ArrayList<>();
        for (int id : st.getCoursesEnrolled()) {
            Course c = courseManager.getById(id);
            if (c != null) result.add(c);
        }

        return result;
    }

    public List<Course> viewAvailableCourses(int studentId) throws Exception {
        Student st = (Student) userManager.getById(studentId);
        if (st == null)
            throw new Exception("Student1 not Found ");
        List<Course> all = new ArrayList<>(courseManager.getAll());
        List<Integer> enrolledIds = st.getCoursesEnrolled();
        all.removeIf(c -> enrolledIds.contains(c.getCourseId()));
        all.removeIf(c->c.getStatus()== Course.CourseStatus.PENDING);
        all.removeIf(c->c.getStatus()== Course.CourseStatus.REJECTED);
        return all;
    }

    public boolean setLessonProgress(int studentId, int courseId, int lessonId, boolean done) throws Exception {
        Student st = (Student) userManager.getById(studentId);
        Course cr = courseManager.getById(courseId);

        if (st == null || cr == null) return false;

        Lesson target = cr.getLessonById(lessonId);
        if (target == null) return false;

        st.setLessonProgress(courseId, lessonId, done);
        userManager.save();
        return true;
    }

    public boolean getLessonProgress(int studentId, int courseId, int lessonId) {
        Student st = (Student) userManager.getById(studentId);
        if (st == null) return false;
        return st.getLessonProgress(courseId, lessonId);
    }

    public List<Course> searchEnrolledCourses(int studentId, String keyword) throws Exception {
        List<Course> enrolled = viewEnrolledCourses(studentId);
        if (enrolled == null) return null;

        keyword = keyword.toLowerCase();
        List<Course> result = new ArrayList<>();

        for (Course c : enrolled) {
            if (c.getTitle().toLowerCase().contains(keyword) ||
                    c.getDescription().toLowerCase().contains(keyword)) {
                result.add(c);
            }
        }

        return result;
    }

    public List<Course> searchAvailableCourses(int studentId, String keyword) throws Exception {
        List<Course> available = viewAvailableCourses(studentId);
        if (available == null) return null;

        keyword = keyword.toLowerCase();
        List<Course> result = new ArrayList<>();

        for (Course c : available) {
            if (c.getTitle().toLowerCase().contains(keyword) ||
                    c.getDescription().toLowerCase().contains(keyword)) {
                result.add(c);
            }
        }

        return result;
    }
}
