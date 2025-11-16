package models;
import java.util.List;
import java.util.ArrayList;
public class Course {
    private int courseId;
    private String title;
    private String description;
    private int instructorId;
    private List<Lesson> lessons;
    private List<Student> students;

    public Course(int courseId, String title, String description, int instructorId) {
        this.courseId=courseId;
        this.title=title;
        this.description=description;
        this.instructorId=instructorId;
        this.lessons=new ArrayList<>();
        this.students=new ArrayList<>();
    }

    public int getCourseId(){return courseId;}
    public String getTitle(){return title;}
    public String getDescription(){return description;}
    public int getInstructorId(){return instructorId;}

    public List<Lesson> getLessons(){return new ArrayList<>(lessons);}
    public List<Student> getStudents(){return new ArrayList<>(students);}

    public void setCourseId(int courseId){this.courseId=courseId;}
    public void setTitle(String title){this.title=title;}
    public void setDescription(String description){this.description=description;}
    public void setInstructorId(int instructorId){this.instructorId=instructorId;}

    public void addLesson(Lesson lesson){
        if (!lessons.contains(lesson))
        {lessons.add(lesson);}
    }
    public void deleteLesson(Lesson lesson){
        lessons.remove(lesson);
    }
  
    public void enrollStudent(Student student) {
        if (!students.contains(student)){
            students.add(student);
            student.addCourse(title);}}

    public void removeStudent(Student student) {
        if (students.contains(student)){
            students.remove(student);
            student.dropCourse(title);}}
}
