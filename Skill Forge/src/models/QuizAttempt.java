package models;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.LinkedHashMap;
import java.time.LocalDateTime;

public class QuizAttempt {
    private int quizId;
    private LocalDateTime attemptDate;
    private int score;
    private boolean passed;
    private Map <Integer,Integer> answers;
    private String courseID;

<<<<<<< HEAD
    public QuizAttempt(Quiz quiz,String courseID){
        validateQuiz(quiz);
        this.quiz=quiz;
=======
    public QuizAttempt(int quizId){
        this.quizId=quizId;
>>>>>>> 7f7bf2a3d2ed3d6c928526ca216b1bfd9c8aac57
        this.attemptDate=LocalDateTime.now();
        this.score=0;
        this.passed=false;
        this.answers=new LinkedHashMap<>();
        this.courseID=courseID;
    }
    public void validateQuiz(Quiz quiz){
        if (quiz==null){
            throw new IllegalArgumentException("quiz not found");
        }
    }
    public Quiz getQuiz(){return quiz;}
    public LocalDateTime getAttemptDate(){return attemptDate;}
    public int getScore(){return score;}
    public Map<Integer,Integer> getAnswers(){return answers;}
    public Integer getAnswer(int questionNum){return answers.get(questionNum);}

    public void addAnswer(int questionNum,int ans,Quiz quiz){
        Question q=quiz.getQuestions().get(questionNum);
        if (q==null){
            throw new IllegalArgumentException("there is no question number:"+questionNum);
        }
        q.validateAnsIndex(ans);
        answers.put(questionNum,ans);
    }
    public int getTotalCorrect(Quiz quiz){
        int correct=0;
        for(Question q : quiz.getQuestions().values()){
            Integer ans=getAnswer(q.getQuestionNum());
            if(ans==null) {continue;}

            if (q.getCorrectAnsIndex()==ans)
            {correct++;}
        }
            return correct;
    }
    public void calcScore(Quiz quiz){
        int correct=getTotalCorrect(quiz);
        int total=quiz.getQuestions().size();
        score=(100*correct)/total;

        passed=(score>=quiz.getPassMark());
    }
    public boolean isPassed(){return passed;}
<<<<<<< HEAD

    public String getCourseID() {
        return courseID;
=======
>>>>>>> 7f7bf2a3d2ed3d6c928526ca216b1bfd9c8aac57
    }
}



