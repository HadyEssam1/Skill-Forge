package models;
import java.util.List;
import java.util.ArrayList;

public class Instructor extends User{
     private List<Course> coursesTeaching;

     public Instructor(int id, String username, String pass, String email) {
        super("instructor", id, username, pass, email);
        this.coursesTeaching = new ArrayList<>();
    }
    public List<Course> getCoursesTeaching(){return new ArrayList<>(coursesTeaching);}

    public void assignCourse(String course) {
        if (!coursesTeaching.contains(course)) {
            coursesTeaching.add(course);}}

    public void leaveCourse(String course){
        coursesTeaching.remove(course);} 


}
