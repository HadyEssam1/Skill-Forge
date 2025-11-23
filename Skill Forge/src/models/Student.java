package models;

import java.security.cert.Certificate;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class Student extends User {

    private List<Integer> coursesEnrolled;
    private Map<Integer, Map<Integer, Boolean>> progressMap;
<<<<<<< HEAD
    private List<Certificate>earnedCertificates;
    private List<QuizAttempt> quizAttempts;
=======
    private Map<Integer,List<QuizAttempt>> quizAttempts;
>>>>>>> 7f7bf2a3d2ed3d6c928526ca216b1bfd9c8aac57

    public Student() {
        this.coursesEnrolled = new ArrayList<>();
        this.progressMap = new HashMap<>();
<<<<<<< HEAD
        this.earnedCertificates=new ArrayList<>();
        this.quizAttempts=new ArrayList<>();
=======
        this.quizAttempts = new HashMap<>();
>>>>>>> 7f7bf2a3d2ed3d6c928526ca216b1bfd9c8aac57
    }
    public Student(int id, String username, String pass, String email) {
        super("student", id, username, pass, email);
        this.coursesEnrolled = new ArrayList<>();
        this.progressMap = new HashMap<>();
<<<<<<< HEAD
        this.earnedCertificates=new ArrayList<>();
        this.quizAttempts=new ArrayList<>();
    }
    public List<QuizAttempt> getQuizAttempts(){return new ArrayList<>(quizAttempts);}
    public List<Certificate> getEarnedCertificates(){
        return new ArrayList<>(earnedCertificates);
=======
        this.quizAttempts = new HashMap<>();

>>>>>>> 7f7bf2a3d2ed3d6c928526ca216b1bfd9c8aac57
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

    public void setQuizScore(int courseId, int lessonId, int score){
        quizAttempts.putIfAbsent(courseId,new HashMap<>());
        quizAttempts.get(courseId).put(lessonId,score);
    }
    public Integer getQuizScore(int courseId, int lessonId){
        if (!quizAttempts.containsKey(courseId)) return null;
        return quizAttempts.get(courseId).getOrDefault(lessonId, null);
    }

    public boolean hasCompletedLesson(int courseId, int lessonId){
        if (!progressMap.containsKey(courseId)) return false;
        return progressMap.get(courseId).getOrDefault(lessonId,false);
    }

}
