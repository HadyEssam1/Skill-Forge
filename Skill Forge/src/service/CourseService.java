package service;

import managers.CourseJsonManager;
import models.Course;
import models.Lesson;
import models.Quiz;

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
    public Quiz getQuizById(int quizId) throws Exception {
        List<Course> courses = courseManager.getAll();
        for (Course c : courses) {
            for (Lesson l : c.getLessons()) {
                Quiz q = l.getQuiz();
                if (q != null && q.getQuizId() == quizId) {
                    return q;
                }
            }
        }
        return null;
    }
    public Integer getQuizIdByLesson(int courseId, int lessonId) throws Exception {
        Course c = courseManager.getById(courseId);
        if (c == null) throw new Exception("Course not found");

        Lesson lesson = c.getLessonById(lessonId);
        if (lesson == null) throw new Exception("Lesson not found");

        if (lesson.getQuiz() == null)
            throw new Exception("Lesson has no quiz");

        return lesson.getQuiz().getQuizId();
    }

}