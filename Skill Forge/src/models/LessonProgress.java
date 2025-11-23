package models;

public class LessonProgress {
    private int lessonId;
    private int attempts;
    private boolean completed;
    private boolean passed;
    private int lessonsCompleted;

    public LessonProgress(int lessonId) {
        this.lessonId = lessonId;
        this.attempts = 0;
        this.completed = false;
        this.passed = false;
        this.lessonsCompleted=0;
    }

    public int getLessonId() {
        return lessonId;
    }

    public void setLessonId(int lessonId) {
        this.lessonId = lessonId;
    }

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }


    public void markCompleted(){
        this.completed = true;
        this.lessonsCompleted++;
    }

    public void incrementAttempts(){
        this.attempts++;
    }

    public void resetProgress(){
        this.attempts=0;
        this.passed=false;
        this.completed=false;
    }
}
