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
    private boolean isCourseCompleted(Student student, Course course) throws Exception {

        int courseId = course.getCourseId();

        for (Lesson lesson : course.getLessons()) {
            boolean done = student.getLessonProgress(courseId, lesson.getLessonId());
            if (!done) return false;
        }

        for (Lesson lesson : course.getLessons()) {
            Quiz quiz = lesson.getQuiz();
            if (quiz != null) {
                QuizAttempt best = studentService.getBestAttempt(student.getUserId(), quiz.getQuizId());
                if (best == null || !best.isPassed()) {
                    return false;
                }
            }
        }

        return true;
    }
    public Certificate courseCompletion(Student student, Course course) throws Exception {

        int courseId = course.getCourseId();

        boolean duplicated = student.getEarnedCertificates()
                .stream()
                .anyMatch(c -> c.getCourseID() == courseId);

        if (duplicated) return null;
        if (!isCourseCompleted(student, course)) {
            return null;
        }
        String certId = generateCertificateID();
        LocalDateTime date = generateIssueDate();

        Certificate cert = new Certificate(
                certId,
                date,
                student.getUserId(),
                course.getCourseId()
        );
        student.getEarnedCertificates().add(cert);
        userManager.save();
        return cert;
    }
}
