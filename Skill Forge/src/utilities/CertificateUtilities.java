package utilities;

import models.Certificate;
import models.Student;
import models.Course;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CertificateUtilities {
    private static final SimpleDateFormat DATE_FORMAT_ISO = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

    public File generateCertificate(Student student, Course course, Certificate certificate) throws IOException {

        // folder inside the project: data/certificates/
        File projectCertFolder = new File("data/certificates/");
        if (!projectCertFolder.exists()) {
            projectCertFolder.mkdirs();
        }

        String safeTitle = course.getTitle().replaceAll("[^a-zA-Z0-9.-]", "_");
        String safeName = student.getUsername().replaceAll(" ", "_");

        String fileName = safeName + "_Certificate_" + safeTitle + ".json";
        File outputFile = new File(projectCertFolder, fileName);

        // Convert LocalDateTime → Date
        Date issueDateAsDate = Date.from(
                certificate.getIssueDate()
                        .atZone(java.time.ZoneId.systemDefault())
                        .toInstant()
        );

        String completionDateString = DATE_FORMAT_ISO.format(issueDateAsDate);

        String certificateId = course.getCourseId() + "-" + student.getUserId() + "-" + new Date().getTime();

        String jsonContent = String.format(
                "{\n" +
                        "  \"certificateId\": \"%s\",\n" +
                        "  \"studentDetails\": {\n" +
                        "    \"userId\": %d,\n" +
                        "    \"name\": \"%s\"\n" +
                        "  },\n" +
                        "  \"courseDetails\": {\n" +
                        "    \"courseId\": \"%s\",\n" +
                        "    \"title\": \"%s\",\n" +
                        "    \"instructorId\": %d\n" +
                        "  },\n" +
                        "  \"completionDetails\": {\n" +
                        "    \"completionDate\": \"%s\",\n" +
                        "    \"issuedBy\": \"E-Learning Platform\"\n" +
                        "  }\n" +
                        "}",
                certificateId,
                student.getUserId(),
                student.getUsername(),
                course.getCourseId(),
                course.getTitle(),
                course.getInstructorId(),
                completionDateString
        );

        try (FileWriter fileWriter = new FileWriter(outputFile)) {
            fileWriter.write(jsonContent);
        }

        System.out.println("JSON Certificate generated successfully at: " + outputFile.getAbsolutePath());
        return outputFile;
    }

}
