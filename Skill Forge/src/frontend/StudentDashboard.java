package frontend;

import models.*;
import service.CertificateService;
import service.CourseService;
import service.StudentService;
import utilities.CertificateUtilities;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class StudentDashboard extends JFrame {

    private JPanel mainPanel, coursesPanel, courseInfoPanel, lessonsPanel, lessonDetailsPanel, bottomPanel, takeQuizPanel, AnsQuestionsPanel, AttemptResultPanel;
    private JTable enrolledTable, availableTable, lessonsTable;
    private JTextField txtSearchEnrolled, txtSearchAvailable;
    private JTextField txtCourseId, txtCourseTitle, txtCourseDesc;
    private JTextField txtLessonId, txtLessonTitle;
    private JTextField txtAnsStat, txtCorrectAns, txtResult;
    private JTextArea txtCurrQuestion; // was mismatched type
    private JTextArea txtLessonDesc;
    private JCheckBox chkLessonCompleted;
    private JButton btnViewLessons, btnCloseLessons, btnEnroll, btnLogout, btnRefresh, btnRefreshAvailable, btnSearch, btnSearchAvailable;
    private JButton btnTakeQuiz;
    private JButton btnEnterAns, btnQuitQuiz, btnShowMark, btnRetry, btnBye;
    private JButton btnViewCertificates;
    private JButton btnEarnCertificate;
    private JPanel certificateHolderPanel;
    private CertificateDashboardPanel certificatePanel;
    private CertificateUtilities certificateUtilities;

    private Student student;
    private StudentService studentService;
    private CourseService courseService;
    private CertificateService certificateService;

    private JTextArea[] choices;
    private JRadioButton[] rdos;
    private JLabel lblTakeQuiz, lblQPassMark, lblQuestionNum;

    private String currentQText, currChoice1, currChoice2, curChoice3, currChoice4;
    private int currQuestionNum = 1;
    private QuizAttempt currAttempt;
    private Map<Integer, Integer> ans;
    private Quiz currQuiz;
    private int currentCourseId = -1; // store course id when viewing lessons

    public StudentDashboard(Student student,
                            StudentService studentService,
                            CourseService courseService,
                            CertificateService certificateService,
                            CertificateUtilities certificateUtilities) {
        this.student = student;
        this.studentService = studentService;
        this.courseService = courseService;
        this.certificateService = certificateService;
        this.certificateUtilities = certificateUtilities;
        initComponents();
        addTableSelectionListeners();
        loadEnrolledCourses();
        loadAvailableCourses();
    }

    private void initComponents() {
        setTitle("Student Dashboard");
        setSize(1000, 550);
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

        // ---------- Earn Certificate button placed in Course Info Panel ----------
        btnEarnCertificate = new JButton("Earn Certificate");
        btnEarnCertificate.setBounds(150, 300, 170, 25);
        btnEarnCertificate.setBackground(new Color(52, 168, 83));
        btnEarnCertificate.setForeground(Color.WHITE);
        btnEarnCertificate.setFocusPainted(false);
        btnEarnCertificate.setVisible(false);
        courseInfoPanel.add(btnEarnCertificate);
        btnEarnCertificate.addActionListener(e -> earnCertificate());

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
        chkLessonCompleted.setEnabled(false);
        btnCloseLessons = new JButton("Close Lessons");
        btnCloseLessons.setBounds(90, 420, 150, 30);
        lessonDetailsPanel.add(btnCloseLessons);

        btnTakeQuiz = new JButton("Take Quiz");
        btnTakeQuiz.setBounds(90, 460, 150, 30);
        lessonDetailsPanel.add(btnTakeQuiz);

        // ---------------- Take Quiz Panel ----------------
        takeQuizPanel = new JPanel(null);
        takeQuizPanel.setBounds(10, 10, 970, 70);
        takeQuizPanel.setBackground(new Color(75, 95, 110));
        takeQuizPanel.setVisible(false);
        mainPanel.add(takeQuizPanel);

        lblTakeQuiz = new JLabel("Quiz: ");
        lblTakeQuiz.setForeground(Color.WHITE);
        lblTakeQuiz.setFont(new Font("Times New Roman", Font.BOLD, 18));
        lblTakeQuiz.setBounds(10, 10, 200, 25);
        takeQuizPanel.add(lblTakeQuiz);

        lblQPassMark = new JLabel("PassMark: ");
        lblQPassMark.setForeground(Color.WHITE);
        lblQPassMark.setFont(new Font("Times New Roman", Font.BOLD, 18));
        lblQPassMark.setBounds(220, 10, 200, 25);
        takeQuizPanel.add(lblQPassMark);

        // ---------------- Answer Questions Panel ----------------
        AnsQuestionsPanel = new JPanel(null);
        AnsQuestionsPanel.setBounds(10, 90, 970, 320);
        AnsQuestionsPanel.setBackground(new Color(75, 95, 110));
        AnsQuestionsPanel.setVisible(false);
        mainPanel.add(AnsQuestionsPanel);

        lblQuestionNum = new JLabel("Question " + currQuestionNum + ":");
        lblQuestionNum.setForeground(Color.WHITE);
        lblQuestionNum.setFont(new Font("Times New Roman", Font.BOLD, 18));
        lblQuestionNum.setBounds(10, 10, 200, 25);
        AnsQuestionsPanel.add(lblQuestionNum);

        txtCurrQuestion = new JTextArea();
        txtCurrQuestion.setBounds(10, 40, 940, 40);
        txtCurrQuestion.setLineWrap(true);
        txtCurrQuestion.setWrapStyleWord(true);
        txtCurrQuestion.setEditable(false);
        AnsQuestionsPanel.add(txtCurrQuestion);

        choices = new JTextArea[4];
        rdos = new JRadioButton[4];
        ButtonGroup group = new ButtonGroup();

        for (int i = 0; i < 4; i++) {
            choices[i] = new JTextArea();
            choices[i].setBounds(10, 90 + (i * 50), 930, 40); // spacing for 4 options
            choices[i].setEditable(false);
            AnsQuestionsPanel.add(choices[i]);

            rdos[i] = new JRadioButton();
            rdos[i].setOpaque(true);
            rdos[i].setForeground(Color.WHITE);
            rdos[i].setBackground(new Color(75, 95, 110));
            rdos[i].setBounds(950, 90 + (i * 50), 20, 40);
            AnsQuestionsPanel.add(rdos[i]);

            group.add(rdos[i]);
        }

        btnEnterAns = new JButton("Enter");
        btnEnterAns.setBounds(10, 290, 100, 35);
        AnsQuestionsPanel.add(btnEnterAns);

        btnQuitQuiz = new JButton("Quit");
        btnQuitQuiz.setBounds(120, 290, 100, 35);
        AnsQuestionsPanel.add(btnQuitQuiz);

        JLabel lblAnsStat = new JLabel("Your answer is:");
        lblAnsStat.setForeground(Color.WHITE);
        lblAnsStat.setBounds(240, 290, 120, 25);
        AnsQuestionsPanel.add(lblAnsStat);

        txtAnsStat = new JTextField();
        txtAnsStat.setBounds(360, 290, 150, 25);
        txtAnsStat.setEditable(false);
        AnsQuestionsPanel.add(txtAnsStat);

        JLabel lblCorrectAns = new JLabel("Correct Answer:");
        lblCorrectAns.setForeground(Color.WHITE);
        lblCorrectAns.setBounds(530, 290, 120, 25);
        AnsQuestionsPanel.add(lblCorrectAns);

        txtCorrectAns = new JTextField();
        txtCorrectAns.setBounds(650, 290, 150, 25);
        txtCorrectAns.setEditable(false);
        AnsQuestionsPanel.add(txtCorrectAns);

        btnShowMark = new JButton("Show Mark");
        btnShowMark.setBounds(820, 290, 120, 35);
        AnsQuestionsPanel.add(btnShowMark);

        // ---------------- Attempt Result Panel ----------------
        AttemptResultPanel = new JPanel(null);
        AttemptResultPanel.setBounds(10, 420, 970, 90);
        AttemptResultPanel.setBackground(new Color(75, 95, 110));
        AttemptResultPanel.setVisible(false);
        mainPanel.add(AttemptResultPanel);

        JLabel lblResult = new JLabel("Your mark is:");
        lblResult.setForeground(Color.WHITE);
        lblResult.setBounds(10, 20, 120, 25);
        AttemptResultPanel.add(lblResult);

        txtResult = new JTextField();
        txtResult.setBounds(140, 20, 100, 25);
        txtResult.setEditable(false);
        AttemptResultPanel.add(txtResult);

        btnRetry = new JButton("Retry");
        btnRetry.setBounds(260, 20, 100, 35);
        AttemptResultPanel.add(btnRetry);
        btnBye = new JButton("Done");
        btnBye.setBounds(380, 20, 100, 35);
        AttemptResultPanel.add(btnBye);
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

        btnViewCertificates = new JButton("Certificates");
        btnViewCertificates.setBounds(350, 10, 150, 40);
        bottomPanel.add(btnViewCertificates);

        certificateHolderPanel = new JPanel(null);
        certificateHolderPanel.setBounds(500, 220, 480, 270);
        certificateHolderPanel.setOpaque(false);
        certificateHolderPanel.setVisible(false);
        mainPanel.add(certificateHolderPanel);

        // ---------------- Actions ----------------
        btnCloseLessons.addActionListener(e -> {
            lessonsPanel.setVisible(false);
            coursesPanel.setVisible(true);
            courseInfoPanel.setVisible(true);
            bottomPanel.setVisible(true);
            certificateHolderPanel.setVisible(false);
            mainPanel.revalidate();
            mainPanel.repaint();
        });
        btnViewCertificates.addActionListener(e -> showCertificatesPanel());
        btnEnroll.addActionListener(e -> enrollSelectedCourse());
        btnRefresh.addActionListener(e -> loadEnrolledCourses());
        btnRefreshAvailable.addActionListener(e -> loadAvailableCourses());
        btnSearch.addActionListener(e -> searchEnrolledCourses());
        btnSearchAvailable.addActionListener(e -> searchAvailableCourses());
        btnLogout.addActionListener(e -> System.exit(0));

    }

    //  Table Listeners
    private void addTableSelectionListeners() {
        // Enrolled Table
        enrolledTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = enrolledTable.getSelectedRow();
                if (selectedRow != -1) {
                    txtCourseId.setText(enrolledTable.getValueAt(selectedRow, 0).toString());
                    txtCourseTitle.setText(enrolledTable.getValueAt(selectedRow, 1).toString());
                    txtCourseDesc.setText(enrolledTable.getValueAt(selectedRow, 2).toString());
                    btnEarnCertificate.setVisible(true);
                    try {
                        int courseId = Integer.parseInt(txtCourseId.getText());
                        updateEarnButtonState(courseId);
                    } catch (Exception ex) {
                        btnEarnCertificate.setEnabled(false);
                    }
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
                    btnEarnCertificate.setVisible(false);
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
            certificateHolderPanel.setVisible(false);

            int courseId = Integer.parseInt(enrolledTable.getValueAt(selectedRow, 0).toString());
            currentCourseId = courseId;
            loadLessons(courseId);
        });

        // Lessons Table
        lessonsTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = lessonsTable.getSelectedRow();
                if (selectedRow != -1) {
                    txtLessonId.setText(lessonsTable.getValueAt(selectedRow, 0).toString());
                    txtLessonTitle.setText(lessonsTable.getValueAt(selectedRow, 1).toString());
                    updateLessonProgress();               }
            }
        });
        btnTakeQuiz.addActionListener(e -> {
            int selectedLessonRow = lessonsTable.getSelectedRow();
            if (selectedLessonRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a lesson first.");
                return;
            }

            if (currentCourseId == -1) {
                JOptionPane.showMessageDialog(this, "Internal error: course not selected.");
                return;
            }

            lessonsPanel.setVisible(false);
            coursesPanel.setVisible(false);
            courseInfoPanel.setVisible(false);
            bottomPanel.setVisible(false);
            takeQuizPanel.setVisible(true);
            AnsQuestionsPanel.setVisible(true);
            AttemptResultPanel.setVisible(false);

            int courseId = currentCourseId;
            int lessonId = Integer.parseInt(lessonsTable.getValueAt(selectedLessonRow, 0).toString());

            Course c = courseService.getCourseById(courseId);
            Lesson lesson = c.getLessonById(lessonId);
            currQuiz = lesson.getQuiz();

            if (currQuiz == null) {
                JOptionPane.showMessageDialog(this, "This lesson has no quiz.");
                showMainPanel();
                return;
            }

            lblTakeQuiz.setText("Quiz: " + currQuiz.getQuizId());
            lblQPassMark.setText("PassMark: " + currQuiz.getPassMark());

            ans = new LinkedHashMap<>();
            currAttempt = new QuizAttempt(currQuiz.getQuizId());

            currQuestionNum = 1;
            btnRetry.setEnabled(true);
            loadQuestion();
        });


        btnEnterAns.addActionListener(e -> {
            int selectedIndex = -1;
            for (int i = 0; i < rdos.length; i++) {
                if (rdos[i].isSelected()) {
                    selectedIndex = i;
                    break;
                }
            }

            if (selectedIndex == -1) {
                JOptionPane.showMessageDialog(this, "Please select an answer.");
                return;
            }
            ans.put(currQuestionNum, selectedIndex);
            currAttempt.addAnswer(currQuestionNum, selectedIndex, currQuiz);

            int correct = currQuiz.getQuestion(currQuestionNum).getCorrectAnsIndex();
            txtAnsStat.setText(selectedIndex == correct ? "Correct" : "Wrong");
            txtCorrectAns.setText(String.valueOf(correct));
            for (JRadioButton r : rdos) r.setSelected(false);

            currQuestionNum++;
            loadQuestion();
        });

        btnShowMark.addActionListener(e -> {
            try {
                currAttempt.calcScore(currQuiz);

                txtResult.setText(String.valueOf(currAttempt.getScore()));
                AttemptResultPanel.setVisible(true);
                AnsQuestionsPanel.setVisible(false);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });

        btnRetry.addActionListener(e -> {
            currQuestionNum = 1;
            currAttempt = new QuizAttempt(currQuiz.getQuizId());
            ans.clear();

            AnsQuestionsPanel.setVisible(true);
            AttemptResultPanel.setVisible(false);

            loadQuestion();
        });


        btnBye.addActionListener(e -> {
            try {
                studentService.takeQuiz(
                        student.getUserId(),
                        currQuiz.getQuizId(),
                        ans
                );

                JOptionPane.showMessageDialog(this, "Attempt saved successfully!");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
            lessonsPanel.setVisible(true);
            coursesPanel.setVisible(false);
            courseInfoPanel.setVisible(false);
            bottomPanel.setVisible(false);
            takeQuizPanel.setVisible(false);
            AnsQuestionsPanel.setVisible(false);
            AttemptResultPanel.setVisible(false);

            currQuiz = null;
            currAttempt = null;
            currQuestionNum = 1;
            ans = null;

            btnRetry.setEnabled(false);
        });

        btnRetry.addActionListener(e -> {
            currQuestionNum = 1;
            currAttempt = new QuizAttempt(currQuiz.getQuizId());
            if (ans != null) ans.clear();

            AnsQuestionsPanel.setVisible(true);
            AttemptResultPanel.setVisible(false);

            loadQuestion();
        });


    }
    private void loadLessons(int courseId) {
        DefaultTableModel model = (DefaultTableModel) lessonsTable.getModel();
        model.setRowCount(0);
        List<Lesson> lessons = courseService.viewLessons(courseId);
        for (Lesson l : lessons) {
            boolean completed = studentService.getLessonProgress(student.getUserId(), courseId, l.getLessonId());
            model.addRow(new Object[]{l.getLessonId(), l.getTitle(), completed});
        }
    }
    private void loadEnrolledCourses() {
        try {
            DefaultTableModel model = (DefaultTableModel) enrolledTable.getModel();
            List<Course> enrolledCourses = studentService.viewEnrolledCourses(student.getUserId());
            model.setRowCount(0);
            for (Course c : enrolledCourses) {
                model.addRow(new Object[]{c.getCourseId(), c.getTitle(), c.getDescription(), c.getInstructorId()});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void loadAvailableCourses() {
        try {
            DefaultTableModel model = (DefaultTableModel) availableTable.getModel();
            List<Course> availableCourses = studentService.viewAvailableCourses(student.getUserId());
            model.setRowCount(0);
            for (Course c : availableCourses) {
                model.addRow(new Object[]{c.getCourseId(), c.getTitle(), c.getDescription(), c.getInstructorId()});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void searchEnrolledCourses() {
        try {
            String keyword = txtSearchEnrolled.getText().trim();
            DefaultTableModel model = (DefaultTableModel) enrolledTable.getModel();
            model.setRowCount(0);
            if (!keyword.isEmpty()) {
                List<Course> results = studentService.searchEnrolledCourses(student.getUserId(), keyword);
                for (Course c : results) {
                    model.addRow(new Object[]{c.getCourseId(), c.getTitle(), c.getDescription(), c.getInstructorId()});
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void searchAvailableCourses() {
        try {
            String keyword = txtSearchAvailable.getText().trim();
            DefaultTableModel model = (DefaultTableModel) availableTable.getModel();
            model.setRowCount(0);
            if (!keyword.isEmpty()) {
                List<Course> results = studentService.searchAvailableCourses(student.getUserId(), keyword);
                for (Course c : results) {
                    model.addRow(new Object[]{c.getCourseId(), c.getTitle(), c.getDescription(), c.getInstructorId()});
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
                try {
                    boolean success = studentService.enrollInCourse(student.getUserId(), courseId);
                    if (success) {
                        JOptionPane.showMessageDialog(null, "You have successfully enrolled in the course!");
                        loadEnrolledCourses();
                        loadAvailableCourses();
                    } else {
                        JOptionPane.showMessageDialog(null, "You are already enrolled in this course!");
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
            boolean completed = studentService.getLessonProgress(
                    student.getUserId(),
                    courseId,
                    lessonId
            );
            chkLessonCompleted.setSelected(completed);
            int selectedRow = lessonsTable.getSelectedRow();
            if (selectedRow != -1) {
                lessonsTable.setValueAt(completed, selectedRow, 2);
            }
            try {
                updateEarnButtonState(courseId);
            } catch (Exception ignored) {}

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Select a lesson first!");
        }
    }
    private void earnCertificate() {
        try {
            if (txtCourseId.getText() == null || txtCourseId.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Select a course first (from Enrolled Courses)",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            int courseId = Integer.parseInt(txtCourseId.getText());
            Certificate cert = certificateService.generateCertificateForCourse(
                    student.getUserId(),
                    courseId
            );
            Course course = courseService.getCourseById(courseId);

            File generated = certificateUtilities.generateCertificate(student, course, cert);

            certificateHolderPanel.removeAll();

            ActionListener downloadListener = evt -> {
                JOptionPane.showMessageDialog(this,
                        "Certificate JSON saved at:\n" + generated.getAbsolutePath(),
                        "Downloaded", JOptionPane.INFORMATION_MESSAGE);
            };

            ActionListener closeListener = evt -> showMainPanel();
            CertificateDashboardPanel certPanel = new CertificateDashboardPanel(student, cert, course, downloadListener, closeListener);
            certPanel.setBounds(0, 0, 480, 220);
            certificateHolderPanel.add(certPanel);

            coursesPanel.setVisible(false);
            courseInfoPanel.setVisible(false);
            lessonsPanel.setVisible(false);
            bottomPanel.setVisible(false);
            certificateHolderPanel.setVisible(true);
            certificateHolderPanel.setBounds(250, 50, 500, 300);

            mainPanel.revalidate();
            mainPanel.repaint();

            JOptionPane.showMessageDialog(this,
                    "Certificate issued successfully!\nSaved to: " + generated.getAbsolutePath(),
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);

            updateEarnButtonState(courseId);

        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this,
                    "Invalid course ID.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);

            try {
                int courseId = Integer.parseInt(txtCourseId.getText());
                updateEarnButtonState(courseId);
            } catch (Exception ignored) {
            }
        }
    }
    private void updateEarnButtonState(int courseId) {
        try {
            boolean existing = student.getEarnedCertificates()
                    .stream()
                    .anyMatch(c -> c.getCourseID() == courseId);
            btnEarnCertificate.setEnabled(!existing);
        } catch (Exception ex) {
            btnEarnCertificate.setEnabled(false);

        }
    }
    private void showCertificatesPanel() {
        try {
            int selectedCourseRow = enrolledTable.getSelectedRow();

            if (selectedCourseRow == -1) {
                throw new Exception("Please select a course first.");
            }

            int courseId = Integer.parseInt(enrolledTable.getValueAt(selectedCourseRow, 0).toString());

            Certificate cert = certificateService.getCertificateForCourse(student.getUserId(), courseId);
            if (cert == null) {
                JOptionPane.showMessageDialog(this,
                        "No certificate available for this course.",
                        "Info",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            Course course = courseService.getCourseById(courseId);

            certificateHolderPanel.removeAll();
            certificateHolderPanel.setLayout(null);
            File generated = certificateUtilities.generateCertificate(student, course, cert);
            ActionListener downloadListener = evt -> {
                JOptionPane.showMessageDialog(this,
                        "Certificate JSON saved at:\n" + generated.getAbsolutePath(),
                        "Downloaded", JOptionPane.INFORMATION_MESSAGE);
            };
            ActionListener closeListener = evt -> showMainPanel();

            CertificateDashboardPanel certPanel =
                    new CertificateDashboardPanel(student, cert, course, downloadListener, closeListener);

            certPanel.setBounds(0, 0, 480, 220);
            certificateHolderPanel.add(certPanel);

            certificateHolderPanel.setBounds(250, 50, 500, 300);
            coursesPanel.setVisible(false);
            courseInfoPanel.setVisible(false);
            lessonsPanel.setVisible(false);
            bottomPanel.setVisible(false);
            certificateHolderPanel.setVisible(true);
            mainPanel.revalidate();
            mainPanel.repaint();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    private void showMainPanel() {
        coursesPanel.setVisible(true);
        courseInfoPanel.setVisible(true);
        lessonsPanel.setVisible(false);
        bottomPanel.setVisible(true);
        certificateHolderPanel.setVisible(false);
        mainPanel.revalidate();
        mainPanel.repaint();
    }
    private void loadQuestion() {
        if (currQuiz == null) return;
        if (currQuestionNum > currQuiz.getTotalQ()) {
            // finished - show result but do NOT save here
            currAttempt.calcScore(currQuiz);
            txtResult.setText(String.valueOf(currAttempt.getScore()));
            AttemptResultPanel.setVisible(true);
            AnsQuestionsPanel.setVisible(false);
            return;
        }
        Question q = currQuiz.getQuestion(currQuestionNum);
        lblQuestionNum.setText("Question " + currQuestionNum + ":");
        txtCurrQuestion.setText(q.getQText());
        String[] opts = q.getChoices().toArray(new String[0]);
        for (int i = 0; i < 4; i++) {
            choices[i].setText(i < opts.length ? opts[i] : "");
            rdos[i].setSelected(false);
        }
    }

}
