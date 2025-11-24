package models;

import java.util.Map;
import java.util.LinkedHashMap;

public class Quiz {
    private int quizId;
    private int lessonId;
    private int courseId;
    private int passMark;
    private Map<Integer, Question> questions;

    // الفيلد الجديد
    private int totalQ;

    public Quiz(int quizId, int lessonId, int courseId, int passMark) {
        validateQuizId(quizId);
        validateLessonId(lessonId);
        validatePassMark(passMark);
        this.quizId = quizId;
        this.lessonId = lessonId;
        this.courseId = courseId;
        this.passMark = passMark;
        this.questions = new LinkedHashMap<>();
    }

    public Quiz() {}

    private void validateLessonId(int lessonId) {
        if (lessonId < 0)
            throw new IllegalArgumentException("lesson ID must be a positive number!");
    }

    protected void validateQuizId(int quizId) {
        if (quizId < 0)
            throw new IllegalArgumentException("Quiz ID must be a positive number!");
    }

    private void validatePassMark(int passMark) {
        if (passMark < 0 || passMark > 100)
            throw new IllegalArgumentException("pass mark must be more than 0 less than 100!");
    }

    public int getQuizId() { return quizId; }
    public int getPassMark() { return passMark; }
    public Question getQuestion(int questionNum) { return questions.get(questionNum); }
    public Map<Integer, Question> getQuestions() { return questions; }

    public void setQuizId(int quizId) {
        validateQuizId(quizId);
        this.quizId = quizId;
    }

    public void setPassMark(int passMark) {
        validatePassMark(passMark);
        this.passMark = passMark;
    }

    public void addQuestionTo(int questionNum, Question question) {
        if (questions.containsKey(questionNum)) {
            throw new IllegalArgumentException("question number " + questionNum + " already exists!!");
        }
        questions.put(questionNum, question);
    }

    public void removeQuestionFrom(int questionNum) {
        if (!questions.containsKey(questionNum)) {
            throw new IllegalArgumentException("question number " + questionNum + " doesn't exist!!");
        }
        questions.remove(questionNum);
    }

    // الفيلد الجديد + getter + setter
    public int getTotalQ() {
        return questions != null ? questions.size() : totalQ;
    }

    public void setTotalQ(int totalQ) {
        this.totalQ = totalQ;
    }

    public void setQuestions(Map<Integer, Question> questions) {
        this.questions = questions;
    }

    public void setLessonId(int lessonId) {
        this.lessonId = lessonId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getLessonId() { return lessonId; }
    public int getCourseId() { return courseId; }
}
