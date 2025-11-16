package service;

import managers.CourseJsonManager;
import managers.UserJsonManager;
import models.Course;
import models.Lesson;
import models.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentService {
    private UserJsonManager userJsonManager;
    private CourseJsonManager courseJsonManager;

    public StudentService(UserJsonManager userJsonManager,CourseJsonManager courseJsonManager){
        this.userJsonManager = userJsonManager;
        this.courseJsonManager = courseJsonManager;
    }

    public ArrayList<Course> browseCourses(){
        return courseJsonManager.loadCourses();
    }


//    public boolean enrollCourse(int studentId , int courseId){
//        try {
//            ArrayList<User>users = userJsonManager.loadUsers();
//            ArrayList<Course>courses = courseJsonManager.loadCourses();
//
//            Student student = null;
//            Course course = null;
//
//            for (User u : users){
//                if (u.getUserId().equals(studentId) && u instanceof Student){
//                    student = (Student) u;                   // hena nta betdawar 3ala el student ely nta 3ayzo f el users kolhom
//                }
//            }
//            for (Course c : courses){
//                if (c.getCourseId().equals(courseId)){
//                    course = c;
//                }
//            }
//            if (student==null || course==null){
//                System.out.println("Student or course not found!");
//                return false;
//            }
//
//        }
//    }



    public boolean enrollCourse(int studentId , int courseId)throws Exception{
        Student s = (Student)userJsonManager.getStudentId(studentId);
        Course c = courseJsonManager.getCourseId(courseId);

        if (s==null || c==null){return false;}

        if (s.getCoursesEnrolled().contains(courseId)){
            System.out.println("Student already enrolled in this course !");
            return false;}

        c.enrollStudent(s);
        s.addCourse(c);

        userJsonManager.saveToFile();
        courseJsonManager.saveToFile();
        return true;

    }

    public boolean dropCourse(int studentId , int courseId)throws Exception{
        Student s = (Student)userJsonManager.getStudentId(studentId);
        Course c = courseJsonManager.getCourseId(courseId);

        if (s == null || c == null) return false;

        s.dropCourse(courseId);
        c.removeStudent(studentId);

        userJsonManager.saveToFile();
        courseJsonManager.saveToFile();
        return true;
    }

    public List<Course> searchAvailableCourses(int studentId, String keyword)throws Exception {

        Student st = (Student) userManager.getById(studentId);
        if (st == null) return new ArrayList<>();

        String k = keyword.toLowerCase();

        List<Integer> enrolled = st.getEnrolledCourses();
        List<Course> courses = courseManager.getAll();

        List<Course> result = new ArrayList<>();

        for (Course c : courses) {

            // skip enrolled courses
            if (enrolled.contains(c.getCourseId()))
                continue;

            // match keyword in title or description
            if (c.getTitle().toLowerCase().contains(k) ||
                    c.getDescription().toLowerCase().contains(k)) {

                result.add(c);
            }
        }

        return result;
    }

    public List<Course> searchEnrolledCourses(int studentId, String keyword) {
        List<Course> enrolled = viewEnrolledCourses(studentId);
        if (enrolled == null) return null;

        keyword = keyword.toLowerCase();
        List<Course> result = new ArrayList<>();

        for (Course c : enrolled) {
            if (c.getTitle().toLowerCase().contains(keyword) ||
                    c.getDescription().toLowerCase().contains(keyword)) {
                result.add(c);
            }
        }
        return enrolled;
    }


    public List<Course> viewEnrolledCourses(int studentId){
        Student s = (Student)userJsonManager.getStudentId(studentId);
        if (s==null) return null;

        return s.getCoursesEnrolled();
    }

    public boolean setLessonProgress(int studentId, int courseId, int lessonId, boolean done) {
        Student st = (Student) userManager.getById(studentId);
        Course cr = courseManager.getById(courseId);

        if (st == null || cr == null) return false;

        Lesson target = null;
        for (Lesson ls : cr.getLessons()) {
            if (ls.getLessonId() == lessonId) {
                target = ls;
                break;
            }
        }
        if (target == null) return false;

        st.setLessonProgress(courseId, lessonId, done);
        userManager.save();
        return true;
    }

    public boolean getLessonProgress(int studentId, int courseId, int lessonId) {
        Student st = (Student) userManager.getById(studentId);
        if (st == null) return false;
        return st.getLessonProgress(courseId, lessonId);
    }






}
