package models;

import java.util.List;
import java.util.ArrayList;

public class Instructor extends User {
    private List<Integer> coursesTeaching; // course IDs

    public Instructor(int id, String username, String pass, String email) {
        super("instructor", id, username, pass, email);
        this.coursesTeaching = new ArrayList<>();
    }
    public Instructor(){}
    public List<Integer> getCoursesTeaching() {
        return new ArrayList<>(coursesTeaching);
    }

    public void assignCourse(int courseId) {
        if (!coursesTeaching.contains(courseId)) {
            coursesTeaching.add(courseId);
        }
    }

    public void leaveCourse(int courseId) {
        coursesTeaching.remove(Integer.valueOf(courseId));
    }
}
