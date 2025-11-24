package models;
import models.Certificate;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class Student extends User {

    private List<Integer> coursesEnrolled;
    private Map<Integer, Map<Integer, Boolean>> progressMap;
    private Map<Integer,List<QuizAttempt>> quizAttempts;
    private List<Certificate> earnedCertificates;

    public Student() {
        this.coursesEnrolled = new ArrayList<>();
        this.progressMap = new HashMap<>();
        this.quizAttempts = new HashMap<>();
        this.earnedCertificates=new ArrayList<>();

    }
    public Student(int id, String username, String pass, String email) {
        super("student", id, username, pass, email);
        this.coursesEnrolled = new ArrayList<>();
        this.progressMap = new HashMap<>();
        this.quizAttempts = new HashMap<>();
        this.earnedCertificates=new ArrayList<>();

    }
    public List<Certificate> getEarnedCertificates(){
        return earnedCertificates;
    }
    public void setEarnedCertificates(List<Certificate> earnedCertificates) {
        this.earnedCertificates = earnedCertificates;
    }
    public List<Integer> getCoursesEnrolled() {
        return new ArrayList<>(coursesEnrolled);
    }
    public void addCourse(int courseId) {
        if (!coursesEnrolled.contains(courseId)) {
            coursesEnrolled.add(courseId);
            progressMap.putIfAbsent(courseId, new HashMap<>());
        }
    }
    public void dropCourse(int courseId) {
        coursesEnrolled.remove(Integer.valueOf(courseId));
        progressMap.remove(courseId);
    }

    public void setLessonProgress(int courseId, int lessonId, boolean progress) {
        Map<Integer, Boolean> lessons = progressMap.get(courseId);
        if (lessons != null) {
            lessons.put(lessonId, progress);
        }
    }

    public boolean getLessonProgress(int courseId, int lessonId) {
        Map<Integer, Boolean> lessons = progressMap.get(courseId);
        if (lessons == null) return false;
        return lessons.getOrDefault(lessonId, false);
    }

    public Map<Integer, Map<Integer, Boolean>> getProgressMap() {
        return progressMap;
    }
    public void addQuizAttempt(int quizId, QuizAttempt attempt) {
        quizAttempts.putIfAbsent(quizId, new ArrayList<>());
        quizAttempts.get(quizId).add(attempt);
    }
    public List<QuizAttempt> getAllQuizAttempts(int quizId) {
        List<QuizAttempt> attempts = quizAttempts.get(quizId);
        if (attempts == null) {
            return new ArrayList<>();
        }
        return attempts;
    }
    public Map<Integer, List<QuizAttempt>> getQuizAttemptsMap() {
        return quizAttempts;
    }

}
