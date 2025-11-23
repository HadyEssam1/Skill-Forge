package models;

import java.security.cert.Certificate;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class Student extends User {

    private List<Integer> coursesEnrolled;
    private Map<Integer, Map<Integer, Boolean>> progressMap;
    private List<Certificate>earnedCertificates;
    private List<QuizAttempt> quizAttempts;

    public Student() {
        this.coursesEnrolled = new ArrayList<>();
        this.progressMap = new HashMap<>();
        this.earnedCertificates=new ArrayList<>();
        this.quizAttempts=new ArrayList<>();
    }

    public Student(int id, String username, String pass, String email) {
        super("student", id, username, pass, email);
        this.coursesEnrolled = new ArrayList<>();
        this.progressMap = new HashMap<>();
        this.earnedCertificates=new ArrayList<>();
        this.quizAttempts=new ArrayList<>();
    }
    public List<QuizAttempt> getQuizAttempts(){return new ArrayList<>(quizAttempts);}
    public List<Certificate> getEarnedCertificates(){
        return new ArrayList<>(earnedCertificates);
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
}
