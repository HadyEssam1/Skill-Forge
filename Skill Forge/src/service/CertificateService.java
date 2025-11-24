package service;

import managers.UserJsonManager;
import managers.CourseJsonManager;
import models.*;

import java.time.LocalDateTime;
import java.util.UUID;

public class CertificateService {

    private final UserJsonManager userManager;
    private final CourseJsonManager courseManager;
    private final CourseService courseService;
    private final StudentService studentService;

    public CertificateService(UserJsonManager userManager,
                              CourseJsonManager courseManager,
                              CourseService courseService,
                              StudentService studentService) {
        this.userManager = userManager;
        this.courseManager = courseManager;
        this.courseService = courseService;
        this.studentService = studentService;
    }

    private LocalDateTime generateIssueDate() {
        return LocalDateTime.now();
    }

    private String generateCertificateID() {
        return UUID.randomUUID().toString();
    }

    private void ensureCourseIsFullyCompleted(Student student, Course course) throws Exception {
        int courseId = course.getCourseId();

        for (Lesson lesson : course.getLessons()) {
            boolean done = student.getLessonProgress(courseId, lesson.getLessonId());
            if (!done)
                throw new Exception("You must finish the lesson: " + lesson.getTitle());
        }

        for (Lesson lesson : course.getLessons()) {
            Quiz quiz = lesson.getQuiz();
            if (quiz != null) {
                QuizAttempt best = studentService.getBestAttempt(student.getUserId(), quiz.getQuizId());

                if (best == null)
                    throw new Exception("You must attempt the quiz for lesson: " + lesson.getTitle());

                if (!best.isPassed())
                    throw new Exception("You must pass the quiz for lesson: " + lesson.getTitle());
            }
        }
    }

    public Certificate generateCertificateForCourse(int studentId, int courseId) throws Exception {

        Student student = (Student) userManager.getById(studentId);
        if (student == null)
            throw new Exception("Student not found with ID: " + studentId);

        Course course = courseManager.getById(courseId);
        if (course == null)
            throw new Exception("Course not found with ID: " + courseId);

        if (!student.getCoursesEnrolled().contains(courseId))
            throw new Exception("Student is not enrolled in this course.");

        boolean alreadyHas = student.getEarnedCertificates()
                .stream()
                .anyMatch(c -> c.getCourseID() == courseId);

        if (alreadyHas)
            throw new Exception("Certificate already issued for this course.");

        ensureCourseIsFullyCompleted(student, course);

        Certificate cert = new Certificate(
                generateCertificateID(),
                generateIssueDate(),
                student.getUserId(),
                course.getCourseId()
        );

        student.getEarnedCertificates().add(cert);
        userManager.save();

        return cert;
    }
    public Certificate getCertificateForCourse(int studentId, int courseId) throws Exception {
        Student student = (Student) userManager.getById(studentId);
        if (student == null)
            throw new Exception("Student not found");

        return student.getEarnedCertificates()
                .stream()
                .filter(c -> c.getCourseID() == courseId)
                .findFirst()
                .orElse(null);
    }

}
