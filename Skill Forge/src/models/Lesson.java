package models;
import java.util.List;
import java.util.ArrayList;

public class Lesson {
    private int lessonId;
    private String title;
    private String content;
    private List<String>resources;

    public Lesson(int lessonId, String title, String content) {
        validateLesson(lessonId,title);
        this.lessonId=lessonId;
        this.title=title;
        this.content=content;
        this.resources=new ArrayList<>();
    }
    public Lesson(int lessonId, String title, String content, List<String> resources) {
        validateLesson(lessonId,title);
        this.lessonId=lessonId;
        this.title=title;
        this.content=content;
        this.resources=new ArrayList<>();
        if (resources!=null) {
            this.resources.addAll(resources);}
    }
    public static void validateLesson(int lessonId, String title){
        if (lessonId < 0) 
            throw new IllegalArgumentException("lesson id must be positive number!");
        if (title == null ||title.isEmpty())
            throw new IllegalArgumentException("Invalid course title");
    }

    public int getLessonId(){return lessonId;}
    public String getTitle(){return title;}
    public String getContent(){return content;}
    public List<String> getResources(){return new ArrayList<>(resources);}

    public void setLessonId(int lessonId){this.lessonId=lessonId;}
    public void setTitle(String title){this.title=title;}
    public void setContent(String content){this.content=content;}

    public void setResources(List<String> resources){
        this.resources.clear();
        if (resources!=null){
            this.resources.addAll(resources);}
    }
    public void addResource(String resource) {
        if (!resources.contains(resource)) {
            resources.add(resource);}
    }
    public void removeResource(String resource) {
        resources.remove(resource);
    }
}
