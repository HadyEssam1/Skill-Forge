package service;

import managers.UserJsonManager;
import managers.CourseJsonManager;
import models.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
public class StudentService {

    private UserJsonManager userManager;
    private CourseJsonManager courseManager;
    private CourseService courseService;
    public StudentService(UserJsonManager userManager, CourseJsonManager courseManager,CourseService courseService) {
        this.userManager = userManager;
        this.courseManager = courseManager;
        this.courseService=courseService;
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
    public QuizAttempt takeQuiz(int studentId, int quizId, Map<Integer,Integer> answers) throws Exception
    {
        Student st = (Student) userManager.getById(studentId);
        if (st == null)
            throw new IllegalArgumentException("Student not found.");

        Quiz quiz = courseService.getQuizById(quizId);
        if (quiz == null)
            throw new IllegalArgumentException("Quiz not found!");

        QuizAttempt attempt = new QuizAttempt(quizId);
        for (Map.Entry<Integer, Integer> entry : answers.entrySet()) {
            attempt.addAnswer(entry.getKey(), entry.getValue(), quiz);
        }
        attempt.calcScore(quiz);
        st.addQuizAttempt(quiz.getQuizId(), attempt);
        if (attempt.isPassed()) {
            st.setLessonProgress(quiz.getCourseId(), quiz.getLessonId(), true);
        }
        userManager.save();

        return attempt;
    }

    public QuizAttempt getBestAttempt(int studentId,int quizId){
        Student st = (Student) userManager.getById(studentId);
        if (st == null)
            throw new IllegalArgumentException("Student not found.");
        List<QuizAttempt> attempts=st.getAllQuizAttempts(quizId);
        if (attempts == null || attempts.isEmpty())
            return null;
        QuizAttempt best= attempts.get(0);
        for (QuizAttempt a: attempts){
            if (a.getScore() > best.getScore()){
                best=a;
            }
        }
        return best;
    }
    public boolean hasPassed(int studentId,int quizId){
        QuizAttempt best = getBestAttempt(studentId,quizId);
        if (best == null) return false;
        return best.isPassed();
    }
}
