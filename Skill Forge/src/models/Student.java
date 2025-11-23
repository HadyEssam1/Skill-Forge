package models;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class Student extends User {

    private List<Integer> coursesEnrolled;
    private Map<Integer, Map<Integer, Boolean>> progressMap;
    private Map<Integer,List<QuizAttempts>> quizAttempts;

    public Student() {
        this.coursesEnrolled = new ArrayList<>();
        this.progressMap = new HashMap<>();
        this.quizAttempts = new HashMap<>();

    }

    public Student(int id, String username, String pass, String email) {
        super("student", id, username, pass, email);
        this.coursesEnrolled = new ArrayList<>();
        this.progressMap = new HashMap<>();
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
    public QuizAttempt takeQuiz(Quiz quiz){
        if (quiz==null){
            throw new IllegalArgumentException("this quiz doesn't exist!");
        }
        QuizAttempt attempt=new QuizAttempt(quiz);

        List<QuizAttempt> prevAttempts=quizAttempts.get(quiz.getQuizId());
        if (prevAttempts==null){
            prevAttempts=new ArrayList<>();
            quizAttempts.put(quiz.getQuizId(),prevAttempts);
        }
        prevAttempts.add(attempt);
        return attempt;
    }
    public List <QuizAttempts> getAllQuizAttempts(int quizId){
        return quizAttempts.getOrDefault(quizId,new ArrayList<>());
    }
    public QuizAttempt getBestAttempt(int quizId){
        List<QuizAttempt> attempts=quizAttempts.get(quizId);
        if (attempts==null){return null;}
        QuizAttempt best= attempts.get(0);
        for (QuizAttempt a: attempts){
            if (a.getScore> best.getScore){
                best=a;
            }
        }
        return best;
    }
    public boolean hasPassed(int quizId){
        best = getBestAttempt(quizId);
        return best.isPassed();
    }
    
}
