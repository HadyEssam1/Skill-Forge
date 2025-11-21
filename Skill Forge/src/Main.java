import javax.swing.*;
import managers.*;
import service.*;
import frontend.*;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            try {
            UserJsonManager userJsonManager = new UserJsonManager();
            CourseJsonManager courseJsonManager = new CourseJsonManager();
            UserService userService = new UserService(userJsonManager);
            CourseService courseService = new CourseService(courseJsonManager);
            InstructorService instructorService = new InstructorService(courseJsonManager, userJsonManager);
            StudentService studentService = new StudentService(userJsonManager, courseJsonManager);
            AdminService adminService=new AdminService(courseJsonManager,userJsonManager);
            Login loginFrame = new Login(userService, instructorService, studentService,adminService,courseService);
            loginFrame.setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, ""+e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
