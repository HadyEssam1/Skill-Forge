package models;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class Student extends User {
    private List<Course> coursesEnrolled;
    private Map<Integer, List<Integer>> completedLessons;//ids of completed lessons for each course per student.

    public Student(int id, String username, String pass, String email) {
        super("student", id, username, pass, email);
        this.coursesEnrolled = new ArrayList<>();
        this.completedLessons = new HashMap<>();
    }
    public List<Course> getCoursesEnrolled() { return new ArrayList<>(coursesEnrolled); }

    public void addCourse(Course course) {
        if (!coursesEnrolled.contains(course)){
            coursesEnrolled.add(course);}}

    public void dropCourse(Course course) {
        coursesEnrolled.remove(course);}

    public void completeLesson(int courseId, int lessonId) {
        if (checkEnrolled(courseId)){
        completedLessons.putIfAbsent(courseId, new ArrayList<>());
        List<Integer> lessons = completedLessons.get(courseId);
        if (!lessons.contains(lessonId)) {
        lessons.add(lessonId);}}
        else {throw new IllegalArgumentException("Student is not enrolled for courseId: " + courseId);}
    }
    public List<Integer> getCompletedLessons(int courseId) {
        List<Integer> lessons=completedLessons.get(courseId);
        if (lessons==null){
            throw new IllegalArgumentException("Student has no completed lessons for courseId: " + courseId);
        }
        return new ArrayList<>(lessons);
    }
    public boolean checkEnrolled(int courseId){
        for (Course c :coursesEnrolled){
            if(c.getCourseId()==courseId){
                return true;
            }}
        return false;
    }
}
