package yourPackageName; // عدّل الاسم لو عندك باكدج

import javax.swing.*;
import managers.*;
import service.*;
import frontend.*;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            UserJsonManager userJsonManager = new UserJsonManager();
            CourseJsonManager courseJsonManager = new CourseJsonManager();
            UserService userService = new UserService();
            CourseService courseService = new CourseService(courseJsonManager);
            InstructorService instructorService = new InstructorService(courseJsonManager, userJsonManager);
            StudentService studentService = new StudentService(userJsonManager, courseJsonManager);
            Login loginFrame = new Login(userService, instructorService, studentService, courseService);
            loginFrame.setVisible(true);
        });
    }
}
