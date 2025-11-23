package models;

import java.security.cert.Certificate;
import java.util.List;
import java.util.ArrayList;

public class Course {
    private int courseId;
    private String title;
    private String description;
    private int instructorId;
    private CourseStatus status;
    private List<Certificate> earnedCertificates;
    private List<Integer> requiredQuizIds;
    public enum CourseStatus{
        PENDING, APPROVED, REJECTED
    }
    private List<Lesson> lessons;
    private List<Integer> studentIds;

    public Course() {
        this.lessons = new ArrayList<>();
        this.studentIds = new ArrayList<>();
        this.earnedCertificates=new ArrayList<>();
        this.requiredQuizIds=new ArrayList<>();
    }

    public Course(int courseId, String title, String description, int instructorId) {
        validateCourseId(courseId);
        validateTitle(title);
        validateInstructorId(instructorId);
        this.courseId = courseId;
        this.title = title;
        this.description = description;
        this.instructorId = instructorId;
        this.status=CourseStatus.PENDING;
        this.lessons = new ArrayList<>();
        this.studentIds = new ArrayList<>();
        this.earnedCertificates=new ArrayList<>();
        this.requiredQuizIds=new ArrayList<>();
    }
    public List<Integer> getRequiredQuizIds(){return new ArrayList<>(requiredQuizIds);}
    public List<Certificate> getEarnedCertificates(){
        return new ArrayList<>(earnedCertificates);
    }
    private void validateCourseId(int courseId){
        if (courseId < 0)
            throw new IllegalArgumentException("Course ID must be a positive number!");
    }
    private void validateTitle(String title){
        if (title == null || title.isEmpty())
            throw new IllegalArgumentException("Invalid course title!");
    }

    private void validateInstructorId(int instructorId){
        if (instructorId < 0)
            throw new IllegalArgumentException("Instructor ID must be a positive number!");
    }

    public int getCourseId(){ return courseId; }
    public String getTitle(){ return title; }
    public String getDescription(){ return description; }
    public int getInstructorId(){ return instructorId; }
    public List<Lesson> getLessons(){ return lessons; }
    public List<Integer> getStudentIds(){ return studentIds; }

    public void setCourseId(int courseId){
        validateCourseId(courseId);
        this.courseId = courseId;
    }

    public void setTitle(String title){
        validateTitle(title);
        this.title = title;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setInstructorId(int instructorId){
        validateInstructorId(instructorId);
        this.instructorId = instructorId;
    }

    public void addLesson(Lesson lesson){
        if (!lessons.contains(lesson)) {
            lessons.add(lesson);
        }
    }

    public void deleteLesson(Lesson lesson){
        lessons.remove(lesson);
    }

    public Lesson getLessonById(int lessonId) {
        for (Lesson lesson : lessons) {
            if (lesson.getLessonId() == lessonId) {
                return lesson;
            }
        }
        return null;
    }

    public void enrollStudent(int studentId) {
        if (!studentIds.contains(studentId)){
            studentIds.add(studentId);
        }
    }
    public void removeStudent(int studentId) {
        studentIds.remove(Integer.valueOf(studentId));
    }

    public CourseStatus getStatus() {
        return status;
    }

    public void setStatus(CourseStatus status) {
        this.status = status;
    }

}
