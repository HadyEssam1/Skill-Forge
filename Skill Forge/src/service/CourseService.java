package service;
import managers.CourseJsonManager;
import models.Course;
import models.Lesson;

import java.util.ArrayList;
import java.util.List;


public class CourseService {

    private CourseJsonManager courseJsonManager;
    public CourseService(CourseJsonManager courseJsonManager) {
        this.courseJsonManager = courseJsonManager;
    }


    public List<Course> getAllCourses() {
        List<Course> list = courseJsonManager.getAllCourses();
        courseJsonManager.saveToFile();
        return list;
    }

    public Course getCourseById(int courseId) {
        Course c = courseJsonManager.getCourseId(courseId);
        return c;
    }


    public List<Lesson> viewLessons(int courseId) {

        Course cr = courseJsonManager.getCourseId(courseId);
        if (cr == null) {
            return new ArrayList<>();
        }
        return cr.getLessons();
    }


    public Lesson getLessonById(int courseId, int lessonId) {

        Course cr = courseJsonManager.getLessonId(courseId);
        if (cr == null) return null;

        for (Lesson l : cr.getLessons()) {
            if (l.getLessonId() == lessonId) {
                return l;
            }
        }
        return null;
    }



}
