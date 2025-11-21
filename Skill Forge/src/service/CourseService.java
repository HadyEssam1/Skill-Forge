package service;

import managers.CourseJsonManager;
import models.Course;
import models.Lesson;

import java.util.List;

public class CourseService {

    private final CourseJsonManager courseManager;

    public CourseService(CourseJsonManager courseManager) {
        this.courseManager = courseManager;
    }
    public List<Course> getAllCourses() {
        List<Course> list = courseManager.getAll();
        return list;
    }
    public Course getCourseById(int courseId) {
        Course c = courseManager.getById(courseId);
        return c;
    }
    public List<Lesson> viewLessons(int courseId)  {
        Course c = courseManager.getById(courseId);
        return (c != null) ? c.getLessons() : null;
    }
    public Lesson getLessonById(int courseId, int lessonId) {
        Course c = courseManager.getById(courseId);
        if (c == null || c.getLessons() == null) {
            return null;
        }
        for (Lesson lesson : c.getLessons()) {
            if (lesson.getLessonId() == lessonId) {
                return lesson;
            }
        }
        return null;
    }
}