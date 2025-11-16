package models;
import java.util.List;
import java.util.ArrayList;
public class Student extends User {
    private List<String> coursesEnrolled;

    public Student(int id, String username, String pass, String email, String gradeLevel) {
        super("student", id, username, pass, email);
        this.coursesEnrolled = new ArrayList<>();
    }
    public List<String> getCoursesEnrolled() { return new ArrayList<>(coursesEnrolled); }

    public void addCourse(String course) {
        if (!coursesEnrolled.contains(course)){
            coursesEnrolled.add(course);}}

    public void dropCourse(String course) {
        coursesEnrolled.remove(course);}
}
