package frontend;

import models.Course;
import models.Lesson;
import models.Student;
import service.CourseService;
import service.StudentService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class StudentDashboard extends JFrame {

    private JPanel mainPanel, coursesPanel, courseInfoPanel, lessonsPanel, lessonDetailsPanel, bottomPanel;
    private JTable enrolledTable, availableTable, lessonsTable;
    private JTextField txtSearchEnrolled, txtSearchAvailable;
    private JTextField txtCourseId, txtCourseTitle, txtCourseDesc;
    private JTextField txtLessonId, txtLessonTitle;
    private JTextArea txtLessonDesc;
    private JCheckBox chkLessonCompleted;
    private JButton btnViewLessons, btnCloseLessons, btnEnroll, btnLogout, btnRefresh, btnRefreshAvailable, btnSearch, btnSearchAvailable;

    private Student student;
    private StudentService studentService;
    private CourseService courseService;

    public StudentDashboard(Student student, StudentService studentService, CourseService courseService) {
        this.student = student;
        this.studentService = studentService;
        this.courseService = courseService;
        initComponents();
        addTableSelectionListeners(); //
        loadEnrolledCourses();
        loadAvailableCourses();
    }

    private void initComponents() {
        setTitle("Student Dashboard");
        setSize(1000, 517);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        // ---------------- Main Panel ----------------
        mainPanel = new JPanel(null);
        mainPanel.setBounds(0, 0, 1000, 517);
        mainPanel.setBackground(new Color(93, 109, 127));
        add(mainPanel);

        // ---------------- Courses Panel ----------------
        coursesPanel = new JPanel(null);
        coursesPanel.setBounds(10, 10, 480, 400);
        coursesPanel.setBackground(new Color(65, 85, 95));
        mainPanel.add(coursesPanel);

        JLabel lblEnrolled = new JLabel("Enrolled Courses");
        lblEnrolled.setForeground(Color.WHITE);
        lblEnrolled.setFont(new Font("Times New Roman", Font.BOLD, 18));
        lblEnrolled.setBounds(10, 10, 200, 25);
        coursesPanel.add(lblEnrolled);

        txtSearchEnrolled = new JTextField();
        txtSearchEnrolled.setBounds(160, 10, 120, 20);
        coursesPanel.add(txtSearchEnrolled);

        btnSearch = new JButton("Search");
        btnSearch.setBounds(290, 10, 80, 25);
        coursesPanel.add(btnSearch);

        btnRefresh = new JButton("Refresh");
        btnRefresh.setBounds(380, 10, 80, 25);
        coursesPanel.add(btnRefresh);

        enrolledTable = new JTable(new DefaultTableModel(new Object[]{"Course ID", "Title", "Description", "Instructor ID"}, 0));
        JScrollPane scrollEnrolled = new JScrollPane(enrolledTable);
        scrollEnrolled.setBounds(10, 40, 460, 150);
        coursesPanel.add(scrollEnrolled);

        JLabel lblAvailable = new JLabel("Available Courses");
        lblAvailable.setForeground(Color.WHITE);
        lblAvailable.setFont(new Font("Times New Roman", Font.BOLD, 18));
        lblAvailable.setBounds(10, 210, 200, 25);
        coursesPanel.add(lblAvailable);

        txtSearchAvailable = new JTextField();
        txtSearchAvailable.setBounds(160, 210, 120, 20);
        coursesPanel.add(txtSearchAvailable);

        btnSearchAvailable = new JButton("Search");
        btnSearchAvailable.setBounds(290, 210, 80, 25);
        coursesPanel.add(btnSearchAvailable);

        btnRefreshAvailable = new JButton("Refresh");
        btnRefreshAvailable.setBounds(380, 210, 80, 25);
        coursesPanel.add(btnRefreshAvailable);

        availableTable = new JTable(new DefaultTableModel(new Object[]{"Course ID", "Title", "Description", "Instructor ID"}, 0));
        JScrollPane scrollAvailable = new JScrollPane(availableTable);
        scrollAvailable.setBounds(10, 245, 460, 150);
        coursesPanel.add(scrollAvailable);

        // ---------------- Course Info Panel ----------------
        courseInfoPanel = new JPanel(null);
        courseInfoPanel.setBounds(500, 10, 480, 400);
        courseInfoPanel.setBackground(new Color(65, 85, 95));
        mainPanel.add(courseInfoPanel);

        JLabel lblCourseInfo = new JLabel("Course Details");
        lblCourseInfo.setForeground(Color.WHITE);
        lblCourseInfo.setFont(new Font("Times New Roman", Font.BOLD, 18));
        lblCourseInfo.setBounds(10, 10, 200, 25);
        courseInfoPanel.add(lblCourseInfo);

        JLabel lblCourseId = new JLabel("Course ID:");
        lblCourseId.setForeground(Color.WHITE);
        lblCourseId.setBounds(10, 50, 100, 25);
        courseInfoPanel.add(lblCourseId);
        txtCourseId = new JTextField();
        txtCourseId.setBounds(110, 50, 200, 25);
        txtCourseId.setEditable(false);
        courseInfoPanel.add(txtCourseId);

        JLabel lblCourseTitle = new JLabel("Title:");
        lblCourseTitle.setForeground(Color.WHITE);
        lblCourseTitle.setBounds(10, 90, 100, 25);
        courseInfoPanel.add(lblCourseTitle);
        txtCourseTitle = new JTextField();
        txtCourseTitle.setBounds(110, 90, 200, 25);
        txtCourseTitle.setEditable(false);
        courseInfoPanel.add(txtCourseTitle);

        JLabel lblCourseDesc = new JLabel("Description:");
        lblCourseDesc.setForeground(Color.WHITE);
        lblCourseDesc.setBounds(10, 130, 100, 25);
        courseInfoPanel.add(lblCourseDesc);
        txtCourseDesc = new JTextField();
        txtCourseDesc.setBounds(110, 130, 300, 100);
        txtCourseDesc.setEditable(false);
        courseInfoPanel.add(txtCourseDesc);

        btnViewLessons = new JButton("View Lessons");
        btnViewLessons.setBounds(150, 250, 150, 35);
        courseInfoPanel.add(btnViewLessons);

        // ---------------- Lessons Panel ----------------
        lessonsPanel = new JPanel(null);
        lessonsPanel.setBounds(5, 5, 975, 535);
        lessonsPanel.setBackground(new Color(65, 85, 95));
        lessonsPanel.setVisible(false);
        mainPanel.add(lessonsPanel);

        lessonsTable = new JTable(new DefaultTableModel(new Object[]{"Lesson ID", "Title", "Completed"}, 0));
        JScrollPane scrollLessons = new JScrollPane(lessonsTable);
        scrollLessons.setBounds(10, 10, 600, 450);
        lessonsPanel.add(scrollLessons);

        // ---------------- Lesson Details Panel ----------------
        lessonDetailsPanel = new JPanel(null);
        lessonDetailsPanel.setBounds(620, 10, 320, 520);
        lessonDetailsPanel.setBackground(new Color(65, 85, 95));
        lessonsPanel.add(lessonDetailsPanel);

        JLabel lblLessonDetails = new JLabel("Lesson Details");
        lblLessonDetails.setForeground(Color.WHITE);
        lblLessonDetails.setFont(new Font("Times New Roman", Font.BOLD, 16));
        lblLessonDetails.setBounds(10, 10, 200, 25);
        lessonDetailsPanel.add(lblLessonDetails);

        JLabel lblLessonId = new JLabel("Lesson ID:");
        lblLessonId.setForeground(Color.WHITE);
        lblLessonId.setBounds(10, 50, 100, 25);
        lessonDetailsPanel.add(lblLessonId);
        txtLessonId = new JTextField();
        txtLessonId.setBounds(110, 50, 180, 25);
        txtLessonId.setEditable(false);
        lessonDetailsPanel.add(txtLessonId);

        JLabel lblLessonTitle = new JLabel("Title:");
        lblLessonTitle.setForeground(Color.WHITE);
        lblLessonTitle.setBounds(10, 90, 100, 25);
        lessonDetailsPanel.add(lblLessonTitle);
        txtLessonTitle = new JTextField();
        txtLessonTitle.setBounds(110, 90, 180, 25);
        txtLessonTitle.setEditable(false);
        lessonDetailsPanel.add(txtLessonTitle);

        JLabel lblLessonDesc = new JLabel("Description:");
        lblLessonDesc.setForeground(Color.WHITE);
        lblLessonDesc.setBounds(10, 130, 100, 25);
        lessonDetailsPanel.add(lblLessonDesc);
        txtLessonDesc = new JTextArea();
        txtLessonDesc.setBounds(10, 160, 280, 200);
        txtLessonDesc.setLineWrap(true);
        txtLessonDesc.setWrapStyleWord(true);
        txtLessonDesc.setEditable(false);
        lessonDetailsPanel.add(txtLessonDesc);

        chkLessonCompleted = new JCheckBox("Completed");
        chkLessonCompleted.setForeground(Color.WHITE);
        chkLessonCompleted.setBackground(new Color(65, 85, 95));
        chkLessonCompleted.setBounds(110, 380, 120, 25);
        lessonDetailsPanel.add(chkLessonCompleted);

        btnCloseLessons = new JButton("Close Lessons");
        btnCloseLessons.setBounds(90, 420, 150, 30);
        lessonDetailsPanel.add(btnCloseLessons);

        // ---------------- Bottom Panel ----------------
        bottomPanel = new JPanel(null);
        bottomPanel.setBounds(0, 415, 980, 60);
        bottomPanel.setBackground(new Color(65, 85, 95));
        mainPanel.add(bottomPanel);

        btnEnroll = new JButton("Enroll");
        btnEnroll.setBounds(50, 10, 120, 40);
        bottomPanel.add(btnEnroll);

        btnLogout = new JButton("Logout");
        btnLogout.setBounds(200, 10, 120, 40);
        bottomPanel.add(btnLogout);

        // ---------------- Actions ----------------
        btnCloseLessons.addActionListener(e -> {
            lessonsPanel.setVisible(false);
            coursesPanel.setVisible(true);
            courseInfoPanel.setVisible(true);
            bottomPanel.setVisible(true);
        });

        btnEnroll.addActionListener(e -> enrollSelectedCourse());
        btnRefresh.addActionListener(e -> loadEnrolledCourses());
        btnRefreshAvailable.addActionListener(e -> loadAvailableCourses());
        btnSearch.addActionListener(e -> searchEnrolledCourses());
        btnSearchAvailable.addActionListener(e -> searchAvailableCourses());
        btnLogout.addActionListener(e->System.exit(0));
        chkLessonCompleted.addActionListener(e -> updateLessonProgress());

    }

    // ---------------- Table Listeners ----------------
    private void addTableSelectionListeners() {
        // Enrolled Table
        enrolledTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = enrolledTable.getSelectedRow();
                if (selectedRow != -1) {
                    txtCourseId.setText(enrolledTable.getValueAt(selectedRow, 0).toString());
                    txtCourseTitle.setText(enrolledTable.getValueAt(selectedRow, 1).toString());
                    txtCourseDesc.setText(enrolledTable.getValueAt(selectedRow, 2).toString());
                }
            }
        });

        // Available Table
        availableTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = availableTable.getSelectedRow();
                if (selectedRow != -1) {
                    txtCourseId.setText(availableTable.getValueAt(selectedRow, 0).toString());
                    txtCourseTitle.setText(availableTable.getValueAt(selectedRow, 1).toString());
                    txtCourseDesc.setText(availableTable.getValueAt(selectedRow, 2).toString());
                }
            }
        });

        // View Lessons
        btnViewLessons.addActionListener(e -> {
            int selectedRow = enrolledTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a course from Enrolled Courses first!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            lessonsPanel.setVisible(true);
            coursesPanel.setVisible(false);
            courseInfoPanel.setVisible(false);
            bottomPanel.setVisible(false);

            int courseId =Integer.parseInt(enrolledTable.getValueAt(selectedRow, 0).toString());
            loadLessons(courseId);
        });

        // Lessons Table
        lessonsTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = lessonsTable.getSelectedRow();
                if (selectedRow != -1) {
                    txtLessonId.setText(lessonsTable.getValueAt(selectedRow, 0).toString());
                    txtLessonTitle.setText(lessonsTable.getValueAt(selectedRow, 1).toString());
                    chkLessonCompleted.setSelected((Boolean) lessonsTable.getValueAt(selectedRow, 2));

                }
            }
        });
    }

    // ---------------- Load Lessons ----------------
    private void loadLessons(int courseId) {
        DefaultTableModel model = (DefaultTableModel) lessonsTable.getModel();
        model.setRowCount(0);

        List<Lesson> lessons = courseService.viewLessons(courseId);
        for (Lesson l : lessons) {
            boolean completed = studentService.getLessonProgress(student.getUserId(),courseId,l.getLessonId());
            model.addRow(new Object[]{l.getLessonId(), l.getTitle(), completed});
        }
    }

    // ---------------- Load Courses ----------------
    private void loadEnrolledCourses() {
        DefaultTableModel model = (DefaultTableModel) enrolledTable.getModel();
        List<Course> enrolledCourses = studentService.viewEnrolledCourses(student.getUserId());
        model.setRowCount(0);
        for (Course c : enrolledCourses) {
            model.addRow(new Object[]{c.getCourseId(), c.getTitle(), c.getDescription(), c.getInstructorId()});
        }
    }

    private void loadAvailableCourses() {
        DefaultTableModel model = (DefaultTableModel) availableTable.getModel();
        List<Course> availableCourses = studentService.viewAvailableCourses(student.getUserId());
        model.setRowCount(0);
        for (Course c : availableCourses) {
            model.addRow(new Object[]{c.getCourseId(), c.getTitle(), c.getDescription(), c.getInstructorId()});
        }
    }

    // ---------------- Search  ----------------
    private void searchEnrolledCourses() {
        String keyword = txtSearchEnrolled.getText().trim();
        DefaultTableModel model = (DefaultTableModel) enrolledTable.getModel();
        model.setRowCount(0);
        if (!keyword.isEmpty()) {
            List<Course> results = studentService.searchEnrolledCourses(student.getUserId(),keyword);
            for (Course c : results) {
                model.addRow(new Object[]{c.getCourseId(), c.getTitle(), c.getDescription(), c.getInstructorId()});
            }
        }
    }

    private void searchAvailableCourses() {
        String keyword = txtSearchAvailable.getText().trim();
        DefaultTableModel model = (DefaultTableModel) availableTable.getModel();
        model.setRowCount(0);
        if (!keyword.isEmpty()) {
            List<Course> results = studentService.searchAvailableCourses(student.getUserId(),keyword);
            for (Course c : results) {
                model.addRow(new Object[]{c.getCourseId(), c.getTitle(), c.getDescription(), c.getInstructorId()});
            }
        }
    }
    private void enrollSelectedCourse() {
        int selectedRow = availableTable.getSelectedRow();
        if (selectedRow != -1) {
            DefaultTableModel model = (DefaultTableModel) availableTable.getModel();
            int courseId = Integer.parseInt(model.getValueAt(selectedRow, 0).toString());

            int confirm = JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to enroll in this course?");
            if (confirm == JOptionPane.YES_OPTION) {
                boolean success = studentService.enrollInCourse(student.getUserId(), courseId);
                if (success) {
                    JOptionPane.showMessageDialog(null, "You have successfully enrolled in the course!");
                    loadEnrolledCourses();
                    loadAvailableCourses();
                } else {
                    JOptionPane.showMessageDialog(null, "You are already enrolled in this course!");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select a course to enroll.");
        }
    }
    private void updateLessonProgress() {
        try {
            int lessonId = Integer.parseInt(txtLessonId.getText());
            int courseId = Integer.parseInt(txtCourseId.getText());
            boolean completed = chkLessonCompleted.isSelected();

            boolean saved = studentService.setLessonProgress(student.getUserId(), courseId, lessonId, completed);

            if (!saved) {
                JOptionPane.showMessageDialog(this, "Failed to update lesson progress!");
            } else {
                int selectedRow = lessonsTable.getSelectedRow();
                lessonsTable.setValueAt(completed, selectedRow, 2);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Select a lesson first!");
        }
    }

}
