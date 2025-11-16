package models;
import java.util.List;
import java.util.ArrayList;

public class Lesson {
    private int lessonId;
    private String title;
    private String content;
    private List<String>resources;

    public Lesson(int lessonId, String title, String content) {
        this.lessonId=lessonId;
        this.title=title;
        this.content=content;
        this.resources=new ArrayList<>();
    }
    public Lesson(int lessonId, String title, String content, List<String> resources) {
        this.lessonId=lessonId;
        this.title=title;
        this.content=content;
        this.resources=new ArrayList<>();
        if (resources!=null) {
            this.resources.addAll(resources);}
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
