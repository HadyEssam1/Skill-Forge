package models;

public class LessonProgress {
    private int lessonId;
    private int attempts;
    private Integer quizScore;          // to be null and not equal any number
    private boolean completed;
    private boolean passed;

    public LessonProgress(int lessonId) {
        this.lessonId = lessonId;
        this.attempts = 0;
        this.quizScore = null;
        this.completed = false;
        this.passed = false;
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

    public Integer getQuizScore() {
        return quizScore;
    }

    public void setQuizScore(Integer quizScore) {
        this.quizScore = quizScore;
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
    }

    public void incrementAttempts(){
        this.attempts++;
    }

    public void recordScore(int score){
        this.quizScore=score;
        if (score>=50){
            this.passed = true;
            this.completed = true;
        }
        else{
            this.passed=false;
            this.completed=false;
        }
    }

    public void resetProgress(){
        this.quizScore=null;
        this.attempts=0;
        this.passed=false;
        this.completed=false;
    }
}
