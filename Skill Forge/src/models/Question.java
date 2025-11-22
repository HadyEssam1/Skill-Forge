package models;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class Question {
    private int questionNum;
    private int quizId;
    private String text;
    private List<String> choices;
    private int correctAnsIndex;


    public Question(int questionNum,int quizId,String text){
        validateQuestionNum(questionNum);
        validateQuizId(quizId);
        validateText(text);

        this.questionNum=questionNum;
        this.quizId=quizId;
        this.text=text;
        this.choices=new ArrayList<>();
    }

    private void validateQuestionNum(int questionNum){
        if (questionNum < 0)
            throw new IllegalArgumentException("question number  must be a positive number!");
        }

    private void validateText(String text){
        if (text == null || text.isEmpty())
            throw new IllegalArgumentException("this text cannot be empty!!");}

    private void validateCorrectAnsIndex(int correctAnsIndex){
        if (correctAnsIndex<0||correctAnsIndex>(choices.size()-1))
            throw new IllegalArgumentException("correct answer must be one of teh choices!!");
    }

    public int getQuestionNum(){return questionNum;}
    public int getCorrectAnsIndex(){return correctAnsIndex;}
    public String getQText(){return text;}
    
    public void setQuestionNum(int questionNum){
        validateQuestionNum(questionNum);
        this.questionNum=questionNum;
    }
    public void setQText(String text){
        validateText(text);
        this.text=text;
    }
    public void setCorrectAnsIndex(int index){
        validateCorrectAnsIndex(index);
        this.correctAnsIndex=index;
    }
    public void addChoice(String choice){
        validateText(choice);
        choices.add(choices.size(),choice);
    }

    public String getChoice(int index){return choices.get(index);}
    public List<String> getChoices(){return choices;}

    public void removeChoice(int index){
        if(index>(choices.size()-1)||index<0){
          throw new IllegalArgumentException("choice index"+index +"does'nt exist!!");  
        }
        choices.remove(index);
    }
}

