package frontend;
import models.Admin;
import models.Instructor;
import models.Student;
import models.User;
import service.*;
import utilities.CertificateUtilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Login extends JFrame {
    private UserService userService;
    private CourseService courseService;
    private InstructorService instructorService;
    private StudentService studentService;
    private AdminService adminService;
    private  CertificateService certificateService;
    private CertificateUtilities certificateUtilities;
    private Analytics analytics;
    private JTextField jTextField1;
    private JPasswordField jPasswordField1;
    private JButton jButton1, jButton2;
    private JLabel jLabel1, jLabel2, jLabel3, signUpLabel;
    private JPanel jPanel2;
    public Login(UserService userService , InstructorService instructorService, StudentService studentService, AdminService adminService, CourseService courseService, Analytics analytics,CertificateService certificateService, CertificateUtilities certificateUtilities) {
       this.userService=userService;
       this.analytics=analytics;
       this.instructorService=instructorService;
       this.studentService=studentService;
       this.courseService=courseService;
       this.adminService=adminService;
       this.certificateService=certificateService;
       this.certificateUtilities=certificateUtilities;
    initComponents();
    }
    private void initComponents() {
        jPanel2 = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                Color start = new Color(52, 73, 94);
                Color end = new Color(93, 109, 127);
                GradientPaint gp = new GradientPaint(0, 0, start, 0, getHeight(), end);
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        jLabel1 = new JLabel("Login");
        jLabel2 = new JLabel("Username");
        jLabel3 = new JLabel("Password");
        signUpLabel = new JLabel("Sign Up");
        jTextField1 = new JTextField();
        jPasswordField1 = new JPasswordField();
        jButton1 = new JButton("Login");
        jButton2 = new JButton("Cancel");

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Login");
        setSize(470, 260);
        setResizable(false);
        setLocationRelativeTo(null);

        jPanel2.setLayout(null);
        jLabel1.setFont(new Font("Times New Roman", Font.BOLD, 30));
        jLabel1.setForeground(Color.WHITE);
        jLabel1.setBounds(190, 10, 200, 30);

        jLabel2.setFont(new Font("Times New Roman", Font.BOLD, 18));
        jLabel2.setForeground(Color.WHITE);
        jLabel2.setBounds(50, 60, 100, 25);

        jTextField1.setBounds(160, 60, 200, 25);

        jLabel3.setFont(new Font("Times New Roman", Font.BOLD, 18));
        jLabel3.setForeground(Color.WHITE);
        jLabel3.setBounds(50, 100, 100, 25);

        jPasswordField1.setBounds(160, 100, 200, 25);

        jButton1.setBounds(150, 150, 100, 35);
        jButton2.setBounds(260, 150, 100, 35);

        signUpLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
        signUpLabel.setForeground(Color.white);
        signUpLabel.setBounds(200, 190, 100, 25);
        signUpLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        jPanel2.add(jLabel1);
        jPanel2.add(jLabel2);
        jPanel2.add(jTextField1);
        jPanel2.add(jLabel3);
        jPanel2.add(jPasswordField1);
        jPanel2.add(jButton1);
        jPanel2.add(jButton2);
        jPanel2.add(signUpLabel);
        getContentPane().add(jPanel2);
        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                String email = jTextField1.getText();
                String passWord = jPasswordField1.getText();
                User user = null;
                    user = userService.login(email,passWord);
                if (user!=null) {
                    if (user instanceof Instructor) {
                        Instructor instructor = (Instructor) user;
                        dispose();
                        InstructorDashboard instructorDashboard = new InstructorDashboard(instructor,instructorService,courseService,studentService,analytics);
                        instructorDashboard.setVisible(true);
                    } else if (user instanceof Student) {
                        Student student = (Student) user;
                        dispose();
                        StudentDashboard studentDashboard = new StudentDashboard(student, studentService, courseService,certificateService,certificateUtilities);
                        studentDashboard.setVisible(true);
                    }
                    else if (user instanceof Admin) {
                        Admin admin = (Admin) user;
                        dispose();
                        AdminDashboard adminDashboard = new AdminDashboard(admin,adminService);
                        adminDashboard.setVisible(true);
                    }else {
                        throw new Exception("Invalid Username and Password");
                    }
                }
                }catch (Exception e1)
                {
                    JOptionPane.showMessageDialog(null, ""+e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        jButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                System.exit(0);
            }
        });
        signUpLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                dispose();
                SignupFrame Signup=new SignupFrame(userService,Login.this);
                Signup.setVisible(true);
            }
        }
        );

    }
}

