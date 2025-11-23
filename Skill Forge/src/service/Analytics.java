package service;
import managers.CourseJsonManager;
import managers.UserJsonManager;
import models.Course;
import models.Lesson;
import models.Student;
import models.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Analytics {

    private CourseJsonManager courseManager;
    private UserJsonManager userManager;

    public Analytics() throws Exception {
    }


    public double getLessonQuizAverage(int courseId, int lessonId) throws Exception {
        Course course = courseManager.getById(courseId);
        if (course == null) return 0;

        List<Integer> studentIds = course.getStudentIds();
        ArrayList<User> users = userManager.load();

        int sum = 0;
        int count = 0;

        for (Integer sid : studentIds) {
            for (User u : users) {
                if (u.getUserId() == sid && u instanceof Student s) {

                    Integer score = s.getQuizScore(courseId, lessonId);
                    if (score != null) {
                        sum += score;
                        count++;
                    }
                }
            }
        }
        return count == 0 ? 0 : (sum * 1.0) / count;
    }

    public double getCourseCompletionRate(int courseId) throws Exception {
        Course course = courseManager.getById(courseId);
        if (course == null) return 0;

        List<Integer> studentIds = course.getStudentIds();
        List<Lesson> lessons = course.getLessons();

        if (studentIds.isEmpty() || lessons.isEmpty()) return 0;

        ArrayList<User> users = userManager.load();

        int totalLessons = lessons.size();
        int totalStudents = studentIds.size();
        int totalCompleted = 0;

        for (Integer studentId : studentIds) {
            for (User u : users) {
                if (u.getUserId() == studentId && u instanceof Student s) {
                    for (Lesson lesson : lessons) {
                        if (s.hasCompletedLesson(courseId, lesson.getLessonId())) {
                            totalCompleted++;
                        }
                    }
                }
            }
        }
        int totalPossible = totalLessons * totalStudents;

        return (totalCompleted * 100.0) / totalPossible;
    }

    public Map<String, Double> getStudentProgressForCourse(int courseId) throws Exception {

        Map<String, Double> progressMap = new HashMap<>();
        Course course = courseManager.getById(courseId);
        if (course == null) return progressMap;

        List<Integer> studentIds = course.getStudentIds();
        List<Lesson> lessons = course.getLessons();
        ArrayList<User> users = userManager.load();

        int totalLessons = lessons.size();

        for (Integer sid : studentIds) {
            for (User u : users) {
                if (u.getUserId() == sid && u instanceof Student s) {

                    int completed = 0;
                    for (Lesson lesson : lessons) {
                        if (s.hasCompletedLesson(courseId, lesson.getLessonId())) {
                            completed++;
                        }
                    }
                    double percent = (completed * 100.0) / totalLessons;
                    progressMap.put(s.getUsername(), percent);
                }
            }
        }
        return progressMap;
    }

    public Map<String, Object> getInstructorInsights(int courseId) throws Exception {

        Map<String, Object> data = new HashMap<>();
        Course course = courseManager.getById(courseId);

        if (course == null) {
            return data;
        }
                                                                            //analytics 3shan el dashboard
        data.put("completionRate", getCourseCompletionRate(courseId));

        Map<Integer, Double> lessonAverages = new HashMap<>();

        for (Lesson lesson : course.getLessons()) {
            double avg = getLessonQuizAverage(courseId, lesson.getLessonId());
            lessonAverages.put(lesson.getLessonId(), avg);
        }
        data.put("quizAverages", lessonAverages);
        data.put("studentProgress", getStudentProgressForCourse(courseId));

        return data;
    }

}
