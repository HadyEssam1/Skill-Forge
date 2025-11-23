package models;

import java.util.ArrayList;
import java.util.List;

public class Lesson {
    private int lessonId;
    private String title;
    private String content;
    private List<String> resources;
    private Quiz quiz;

    public Lesson() {}

    public Lesson(int lessonId, String title, String content) {
        validateId(lessonId);
        validateTitle(title);
        validateContent(content);
        this.lessonId = lessonId;
        this.title = title;
        this.content = content;
        this.resources = new ArrayList<>();
        this.quiz=null;
    }

    public int getLessonId() { return lessonId; }

    public void setLessonId(int lessonId) {
        validateId(lessonId);
        this.lessonId = lessonId;
    }

    public String getTitle() { return title; }

    public void setTitle(String title) {
        validateTitle(title);
        this.title = title;
    }

    public String getContent() { return content; }

    public void setContent(String content) {
        validateContent(content);
        this.content = content;
    }

    public List<String> getResources() { return resources; }

    public void setResources(List<String> resources) {
        if (resources == null)
            this.resources = new ArrayList<>();
        else
            this.resources = resources;
    }
    public Quiz getQuiz(){return quiz;}
    public void setQuiz(Quiz quiz){
        if(quiz==null){throw new IllegalArgumentException("quiz cannot be null");}
        if (quiz.getLessonId()!= lessonId){throw new IllegalArgumentException("this quiz does not belong to this lesson!!");}
        this quiz=quiz;
    }
    public removeQuiz(){this.quiz=null;}

    private void validateId(int lessonId) {
        if (lessonId <= 0)
            throw new IllegalArgumentException("Lesson ID must be positive.");
    }

    private void validateTitle(String title) {
        if (title == null || title.trim().isEmpty())
            throw new IllegalArgumentException("Lesson title cannot be empty.");
    }

    private void validateContent(String content) {
        if (content == null || content.trim().isEmpty())
            throw new IllegalArgumentException("Lesson content cannot be empty.");
    }
    
}
