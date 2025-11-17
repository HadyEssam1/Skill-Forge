package service;

import managers.CourseJsonManager;
import models.Course;
import models.Lesson;

import java.util.List;

public class CourseService {

    private CourseJsonManager courseManager;

    public CourseService(CourseJsonManager courseManager) {
        this.courseManager = courseManager;
    }

    // ------------------------- Get All Courses -------------------------
    public List<Course> getAllCourses() {
        List<Course> list = courseManager.getAll();
        courseManager.save();
        return list;
    }

    // ------------------------- Get Course By ID -------------------------
    public Course getCourseById(int courseId) {
        Course c = courseManager.getById(courseId);
        courseManager.save();
        return c;
    }

    // ------------------------- View Lessons for a Course -------------------------
    public List<Lesson> viewLessons(int courseId) {
        Course c = courseManager.getById(courseId);
        courseManager.save();
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