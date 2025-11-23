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

    public QuizAttempt(int quizId){
        this.quizId=quizId;
        this.attemptDate=LocalDateTime.now();
        this.score=0;
        this.passed=false;
        this.answers=new LinkedHashMap<>();
    }
    public void validateQuiz(Quiz quiz){
        if (quiz==null){
            throw new IllegalArgumentException("quiz not found");
        }
    }
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
    }



