package models;

import java.util.List;
import java.util.ArrayList;

public class Course {
    private int courseId;
    private String title;
    private String description;
    private int instructorId;

    private List<Lesson> lessons;
    private List<Integer> studentIds;

    public Course() {
        this.lessons = new ArrayList<>();
        this.studentIds = new ArrayList<>();
    }

    public Course(int courseId, String title, String description, int instructorId) {
        validateCourse(courseId, title, instructorId);

        this.courseId = courseId;
        this.title = title;
        this.description = description;
        this.instructorId = instructorId;

        this.lessons = new ArrayList<>();
        this.studentIds = new ArrayList<>();
    }

    public static void validateCourse(int courseId, String title, int instructorId){
        if (courseId < 0)
            throw new IllegalArgumentException("Course id must be positive number!");
        if (title == null || title.isEmpty() || title.matches(".*\\d.*"))
            throw new IllegalArgumentException("Invalid course title");
        if (instructorId < 0)
            throw new IllegalArgumentException("Instructor id must be positive number!");
    }

    public int getCourseId(){ return courseId; }
    public String getTitle(){ return title; }
    public String getDescription(){ return description; }
    public int getInstructorId(){ return instructorId; }

    public List<Lesson> getLessons(){ return lessons; }
    public List<Integer> getStudentIds(){ return studentIds; }

    public void setCourseId(int courseId){ this.courseId = courseId; }
    public void setTitle(String title){ this.title = title; }
    public void setDescription(String description){ this.description = description; }
    public void setInstructorId(int instructorId){ this.instructorId = instructorId; }

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
}
