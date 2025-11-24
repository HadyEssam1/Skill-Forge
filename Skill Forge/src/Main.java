import javax.swing.*;
import managers.*;
import service.*;
import frontend.*;
import utilities.CertificateUtilities;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            try {
            UserJsonManager userJsonManager = new UserJsonManager();
            CourseJsonManager courseJsonManager = new CourseJsonManager();
            CertificateUtilities certificateUtilities=new CertificateUtilities();
            UserService userService = new UserService(userJsonManager);
            CourseService courseService = new CourseService(courseJsonManager);
            InstructorService instructorService = new InstructorService(courseJsonManager, userJsonManager);
            StudentService studentService = new StudentService(userJsonManager, courseJsonManager,courseService);
            AdminService adminService=new AdminService(courseJsonManager,userJsonManager);
            CertificateService certificateService=new CertificateService(userJsonManager,courseJsonManager,courseService,studentService);
                Login loginFrame = new Login(userService, instructorService, studentService,adminService,courseService,certificateService,certificateUtilities);
            loginFrame.setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, ""+e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
