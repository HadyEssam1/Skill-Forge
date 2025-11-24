package service;

import managers.CourseJsonManager;
import managers.UserJsonManager;
import models.*;

import java.util.List;
import java.util.ArrayList;

public class InstructorService {

    private CourseJsonManager courseManager;
    private UserJsonManager userManager;

    public InstructorService(CourseJsonManager courseManager, UserJsonManager userManager) {
        this.courseManager = courseManager;
        this.userManager = userManager;
    }

    private int generateCourseId() {
        List<Course> courses = courseManager.getAll();
        if (courses == null) {
            return 1;
        }
        int maxId = 0;
        for (Course c : courses) {
            if (c.getCourseId() > maxId) {
                maxId = c.getCourseId();
            }
        }
        return maxId + 1;}
    public Course createCourse(int instructorId, String title, String description) throws Exception {
        int newId = generateCourseId();
        Course c = new Course(newId, title, description, instructorId);
        courseManager.add(c);
        User u = userManager.getById(instructorId);
        if (u instanceof Instructor) {
            Instructor inst = (Instructor) u;
            inst.assignCourse(newId);
            userManager.save();
        }
        courseManager.save();
        return c;
    }

    public void editCourse(int courseId, String newTitle, String newDescription) throws Exception {
        Course c = courseManager.getById(courseId);
        if (c == null) return;

        c.setTitle(newTitle);
        c.setDescription(newDescription);

        courseManager.save();
    }

    public void deleteCourse(int instructorId, int courseId) throws Exception {
        Course c = courseManager.getById(courseId);
        if (c == null) return;

        courseManager.delete(c);
        courseManager.save();

        User u = userManager.getById(instructorId);
        if (u instanceof Instructor) {
            Instructor inst = (Instructor) u;
            inst.leaveCourse(courseId);
            userManager.save();
        }
    }

    public void addLesson(int courseId, String title, String content) throws Exception {
        Course c = courseManager.getById(courseId);
        if (c == null)
            throw new Exception("Course is not exit");
        int lessonId = generatelessonId(courseId);
        Lesson l = new Lesson(lessonId,courseId ,title, content);
        c.addLesson(l);
        courseManager.save();
    }

    public void editLesson(int courseId, int lessonId, String newTitle, String newContent) throws Exception {
        Course c = courseManager.getById(courseId);
        if (c == null)
            throw new Exception("Course is not exit");
        models.Lesson l = c.getLessonById(lessonId);
        if (l == null)
            throw new Exception("lesson is not exit");
        l.setTitle(newTitle);
        l.setContent(newContent);

        courseManager.save();
    }

    public void deleteLesson(int courseId, int lessonId) throws Exception {
        Course c = courseManager.getById(courseId);
        if (c == null) return;

        c.getLessons().removeIf(l -> l.getLessonId() == lessonId);

        courseManager.save();
    }

    public List<Course> getInstructorCourses(int instructorId) {
        User u = userManager.getById(instructorId);
        Instructor inst = (Instructor) u;
        List<Course> result = new ArrayList<>();
        for (int id : inst.getCoursesTeaching()) {
            Course c = courseManager.getById(id);
            if (c != null) result.add(c);
        }

        return result;
    }

    public List<Student> viewEnrolledStudents(int courseId) {
        Course c = courseManager.getById(courseId);
        if (c == null) return new ArrayList<>();

        List<Student> result = new ArrayList<>();

        for (Integer studentid : c.getStudentIds()) {
            User u = userManager.getById(studentid);
            if (u instanceof Student) {
                result.add((Student) u);
            }
        }

        return result;
    }
    public int generatelessonId(int courseId) throws Exception {
        Course c = courseManager.getById(courseId);
        if (c == null)
            throw new Exception("Course is not exit");
        List<Lesson> lessons = c.getLessons();

        if (lessons == null) {
            return 1;
        }
        int maxId = 0;
        for (Lesson lesson : lessons) {
            if (lesson.getLessonId() > maxId) {
                maxId = lesson.getLessonId();
            }
        }
        return maxId + 1;
    }
    public Quiz createQuizForLesson(int courseId, int lessonId, Quiz quiz) throws Exception {

        if (quiz == null)
            throw new IllegalArgumentException("Quiz cannot be null");

        if (quiz.getPassMark() < 0 || quiz.getPassMark() > 100)
            throw new IllegalArgumentException("passMark must be between 0 and 100");

        // Get course
        Course c = courseManager.getById(courseId);
        if (c == null)
            throw new IllegalArgumentException("Course not found");

        // Check instructor
        User u = userManager.getById(c.getInstructorId());
        if (!(u instanceof Instructor))
            throw new IllegalArgumentException("Instructor not found");

        Instructor inst = (Instructor) u;
        if (!inst.getCoursesTeaching().contains(courseId))
            throw new IllegalArgumentException("Unauthorized to create quiz for this course");

        // Get lesson
        Lesson lesson = c.getLessonById(lessonId);
        if (lesson == null)
            throw new IllegalArgumentException("Lesson not found");
        lesson.setQuiz(quiz);

        // Save
        courseManager.save();

        return quiz;
    }


    public void removeQuizFromLesson(int instructorId, int courseId, int lessonId) throws Exception {
        Course c = courseManager.getById(courseId);
        if (c == null)
            throw new IllegalArgumentException("Course not found");
        Lesson lesson = c.getLessonById(lessonId);
        if (lesson == null)
            throw new IllegalArgumentException("Lesson not found");
        User u = userManager.getById(instructorId);
        if (!(u instanceof Instructor))
            throw new IllegalArgumentException("Instructor not found");
        Instructor inst = (Instructor) u;
        if (!inst.getCoursesTeaching().contains(courseId))
            throw new IllegalArgumentException("Unauthorized attempt to remove quiz!");
        lesson.removeQuiz();
        courseManager.save();
    }

}