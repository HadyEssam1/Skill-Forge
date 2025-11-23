package service;

import models.Certificate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import managers.CourseJsonManager;
import managers.UserJsonManager;
import models.Course;
import models.QuizAttempt;
import models.Student;

public class CertificateService {

    private UserJsonManager userManager;
    private CourseJsonManager courseManager;

    public CertificateService(UserJsonManager userManager, CourseJsonManager courseManager) {
        this.userManager = userManager;
        this.courseManager = courseManager;
    }

    private LocalDateTime generateIssueDate() {
        return LocalDateTime.now();
    }

    private String generateCertificateID() {
        return UUID.randomUUID().toString();
    }

    public Certificate courseCompletion(Student student, Course course, QuizAttempt quizAttempt) throws Exception {
        if (!isEgligibleCertificate(student, course)) {
            return null;
        }

        int studentId = student.getUserId();
        int courseId = course.getCourseId();
        String certificateId = generateCertificateID();
        LocalDateTime issueDate = generateIssueDate();

        Certificate newCertificate = new Certificate(certificateId, issueDate, studentId, courseId);
        student.getEarnedCertificates().add(newCertificate);
        course.getEarnedCertificates().add(newCertificate);

        userManager.update(student);
        courseManager.update(course);

        return newCertificate;
    }

    private boolean isEgligibleCertificate(Student student, Course course) {
        boolean duplicated = student.getEarnedCertificates().stream().anyMatch(c ->
                c.getCourseID().equals(course.getCourseId()));
        if (duplicated) {
            return false;
        }
        List<Integer> requiredQuizIDs = course.getRequiredQuizIds();
        if (requiredQuizIDs == null || requiredQuizIDs.isEmpty()) {
            return false;
        }
        for (int requiredQuizID : requiredQuizIDs) {
            Optional<QuizAttempt> attempt = student.getQuizAttempts().stream()
                    .filter(a -> a.getQuiz().getQuizId().equals(requiredQuizID))
                    .findFirst();
            if (attempt.isEmpty() || !attempt.get().isPassed()) {
                return false;
            }
        }
        return true;
    }
}







