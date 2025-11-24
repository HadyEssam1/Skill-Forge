package frontend;

import managers.CourseJsonManager;
import managers.UserJsonManager;
import models.*;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import service.Analytics;
import service.CourseService;
import service.InstructorService;
import service.StudentService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InstructorDashboard extends JFrame {

    private static final long serialVersionUID = 1L;

    private JPanel mainPanel, coursesPanel, courseInfoPanel, lessonsPanel, enrolledStudentsPanel, bottomPanel;
    private JPanel addCoursePanel, addLessonPanel, createQuizPanel, addQuestionsPanel, editLessonPanel;
    private JTable coursesTable, lessonsTable, studentsTable;
    private JTextField txtCourseId, txtCourseTitle, txtCourseDesc, txtNewCourseId, txtNewCourseTitle, txtNewLessonId, txtNewLessonTitle, txtNewQuizId, txtQuizPassMark;
    private JTextArea txtNewLessonContent, txtNewCourseDesc;
    private JTextArea txtChoice1, txtChoice2, txtChoice3, txtChoice4, txtQuestion;
    private JButton btnAddCourse, btnEditCourse, btnDeleteCourse, btnCancelCourse, btnSaveCourse;
    private JButton btnAddLesson, btnEditLesson, btnDeleteLesson, btnSaveLesson, btnCancelLesson, btnCreateQuiz, btnRemoveQuiz;
    private JButton btnContinueQuiz, btnCancelQuiz,btnAnalytics;
    private JButton btnAddQuestion, btnClearQuestion, btnFinishQuiz;
    private JButton btnViewLessons, btnCloseLessons;
    private JButton btnViewStudents, btnCloseStudents, btnLogout;
    private Instructor instructor;
    private InstructorService instructorService;
    private CourseService courseService;
    private JTextField txtEditLessonId;
    private JTextField txtEditLessonTitle;
    private JTextArea txtEditLessonContent;
    private JButton btnUpdateLesson, btnCancelEditLesson;
    private int currentQuestionNum = 1;
    private int quizId;
    private Quiz currentQuiz; // now strongly-typed Quiz
    private JComboBox<String> comboCorrect;
    private CourseJsonManager courseManager;
    private UserJsonManager userManager;
    private StudentService studentService;
    Analytics analytics;

    public InstructorDashboard(Instructor instructor, InstructorService instructorService, CourseService courseService,
                               StudentService studentService,Analytics analytics) {

        this.instructor = instructor;
        this.instructorService = instructorService;
        this.courseService = courseService;
        this.analytics=analytics;
        this.courseManager = courseManager;
        this.userManager = userManager;
        this.studentService = studentService;

        initComponents();
        loadCreatedCourses();
    }

    private void initComponents() {
        setTitle("Instructor Dashboard");
        setSize(1000, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        //Main Panel
        mainPanel = new JPanel(null);
        mainPanel.setBounds(0, 0, 1000, 550);
        mainPanel.setBackground(new Color(93, 109, 127));
        add(mainPanel);

        // Courses Panel
        coursesPanel = new JPanel(null);
        coursesPanel.setBounds(10, 10, 480, 400);
        coursesPanel.setBackground(new Color(65, 85, 95));
        mainPanel.add(coursesPanel);

        JLabel lblCourses = new JLabel("Courses");
        lblCourses.setForeground(Color.WHITE);
        lblCourses.setFont(new Font("Times New Roman", Font.BOLD, 18));
        lblCourses.setBounds(10, 10, 200, 25);
        coursesPanel.add(lblCourses);

        coursesTable = new JTable(new DefaultTableModel(
                new String[]{"Course ID", "Title", "Description","Status"}, 0));
        JScrollPane scrollCourses = new JScrollPane(coursesTable);
        scrollCourses.setBounds(10, 40, 460, 250);
        coursesPanel.add(scrollCourses);

        btnAddCourse = new JButton("Add Course");
        btnAddCourse.setBounds(10, 300, 120, 35);
        coursesPanel.add(btnAddCourse);

        btnEditCourse = new JButton("Edit Course");
        btnEditCourse.setBounds(140, 300, 120, 35);
        coursesPanel.add(btnEditCourse);

        btnDeleteCourse = new JButton("Delete Course");
        btnDeleteCourse.setBounds(270, 300, 120, 35);
        coursesPanel.add(btnDeleteCourse);

        btnViewLessons = new JButton("View Lessons");
        btnViewLessons.setBounds(140, 350, 120, 35);
        coursesPanel.add(btnViewLessons);

        btnViewStudents = new JButton("View Enrolled Students");
        btnViewStudents.setBounds(10, 350, 120, 35);
        coursesPanel.add(btnViewStudents);

        //Course Info Panel
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
        courseInfoPanel.add(txtCourseTitle);

        JLabel lblCourseDesc = new JLabel("Description:");
        lblCourseDesc.setForeground(Color.WHITE);
        lblCourseDesc.setBounds(10, 130, 100, 25);
        courseInfoPanel.add(lblCourseDesc);
        txtCourseDesc = new JTextField();
        txtCourseDesc.setBounds(110, 130, 300, 100);
        courseInfoPanel.add(txtCourseDesc);

        //Lessons Panel
        lessonsPanel = new JPanel(null);
        lessonsPanel.setBounds(10, 10, 970, 500);
        lessonsPanel.setBackground(new Color(65, 85, 95));
        lessonsPanel.setVisible(false);
        mainPanel.add(lessonsPanel);

        lessonsTable = new JTable(new DefaultTableModel(new String[]{"Lesson ID", "Title", "Content"}, 0));
        JScrollPane scrollLessons = new JScrollPane(lessonsTable);
        scrollLessons.setBounds(10, 10, 600, 450);
        lessonsPanel.add(scrollLessons);

        btnAddLesson = new JButton("Add Lesson");
        btnAddLesson.setBounds(620, 10, 120, 35);
        lessonsPanel.add(btnAddLesson);

        btnEditLesson = new JButton("Edit Lesson");
        btnEditLesson.setBounds(620, 60, 120, 35);
        lessonsPanel.add(btnEditLesson);

        btnDeleteLesson = new JButton("Delete Lesson");
        btnDeleteLesson.setBounds(620, 110, 120, 35);
        lessonsPanel.add(btnDeleteLesson);

        btnCloseLessons = new JButton("Close Lessons");
        btnCloseLessons.setBounds(620, 160, 120, 35);
        lessonsPanel.add(btnCloseLessons);

        btnCreateQuiz = new JButton("Create Quiz");
        btnCreateQuiz.setBounds(620, 210, 120, 35);
        lessonsPanel.add(btnCreateQuiz);

        btnRemoveQuiz = new JButton("Remove Quiz");
        btnRemoveQuiz.setBounds(620, 260, 120, 35);
        lessonsPanel.add(btnRemoveQuiz);

        // Enrolled Students Panel
        enrolledStudentsPanel = new JPanel(null);
        enrolledStudentsPanel.setBounds(10, 10, 970, 500);
        enrolledStudentsPanel.setBackground(new Color(65, 85, 95));
        enrolledStudentsPanel.setVisible(false);
        mainPanel.add(enrolledStudentsPanel);

        studentsTable = new JTable(new DefaultTableModel(new String[]{"Student ID", "Name", "Email"}, 0));
        JScrollPane scrollStudents = new JScrollPane(studentsTable);
        scrollStudents.setBounds(10, 10, 600, 450);
        enrolledStudentsPanel.add(scrollStudents);

        btnCloseStudents = new JButton("Close");
        btnCloseStudents.setBounds(620, 10, 120, 35);
        enrolledStudentsPanel.add(btnCloseStudents);

        // Add Course Panel
        addCoursePanel = new JPanel(null);
        addCoursePanel.setBounds(10, 10, 970, 500);
        addCoursePanel.setBackground(new Color(75, 95, 110));
        addCoursePanel.setVisible(false);
        mainPanel.add(addCoursePanel);

        JLabel lblAddCourse = new JLabel("Add New Course");
        lblAddCourse.setForeground(Color.WHITE);
        lblAddCourse.setFont(new Font("Times New Roman", Font.BOLD, 18));
        lblAddCourse.setBounds(10, 10, 200, 25);
        addCoursePanel.add(lblAddCourse);

        JLabel lblNewCourseId = new JLabel("Course ID:");
        lblNewCourseId.setForeground(Color.WHITE);
        lblNewCourseId.setBounds(10, 50, 100, 25);
        addCoursePanel.add(lblNewCourseId);

        txtNewCourseId = new JTextField();
        txtNewCourseId.setBounds(110, 50, 200, 25);
        addCoursePanel.add(txtNewCourseId);
        txtNewCourseId.setEditable(false);

        JLabel lblNewCourseTitle = new JLabel("Title:");
        lblNewCourseTitle.setForeground(Color.WHITE);
        lblNewCourseTitle.setBounds(10, 90, 100, 25);
        addCoursePanel.add(lblNewCourseTitle);

        txtNewCourseTitle = new JTextField();
        txtNewCourseTitle.setBounds(110, 90, 200, 25);
        addCoursePanel.add(txtNewCourseTitle);

        JLabel lblNewCourseDesc = new JLabel("Description:");
        lblNewCourseDesc.setForeground(Color.WHITE);
        lblNewCourseDesc.setBounds(10, 130, 100, 25);
        addCoursePanel.add(lblNewCourseDesc);

        txtNewCourseDesc = new JTextArea();
        txtNewCourseDesc.setBounds(110, 130, 300, 100);
        addCoursePanel.add(txtNewCourseDesc);

        btnSaveCourse = new JButton("Save");
        btnSaveCourse.setBounds(110, 250, 100, 35);
        addCoursePanel.add(btnSaveCourse);

        btnCancelCourse = new JButton("Cancel");
        btnCancelCourse.setBounds(220, 250, 100, 35);
        addCoursePanel.add(btnCancelCourse);

        btnAnalytics = new JButton("View Analytics");
        btnAnalytics.setBounds(270, 350, 120, 35);
        coursesPanel.add(btnAnalytics);

        // Add Lesson Panel
        addLessonPanel = new JPanel(null);
        addLessonPanel.setBounds(10, 10, 970, 500);
        addLessonPanel.setBackground(new Color(75, 95, 110));
        addLessonPanel.setVisible(false);
        mainPanel.add(addLessonPanel);

        JLabel lblAddLesson = new JLabel("Add New Lesson");
        lblAddLesson.setForeground(Color.WHITE);
        lblAddLesson.setFont(new Font("Times New Roman", Font.BOLD, 18));
        lblAddLesson.setBounds(10, 10, 200, 25);
        addLessonPanel.add(lblAddLesson);

        JLabel lblNewLessonId = new JLabel("Lesson ID:");
        lblNewLessonId.setForeground(Color.WHITE);
        lblNewLessonId.setBounds(10, 50, 100, 25);
        addLessonPanel.add(lblNewLessonId);

        txtNewLessonId = new JTextField();
        txtNewLessonId.setBounds(110, 50, 200, 25);
        txtNewLessonId.setEditable(false);
        addLessonPanel.add(txtNewLessonId);

        JLabel lblNewLessonTitle = new JLabel("Title:");
        lblNewLessonTitle.setForeground(Color.WHITE);
        lblNewLessonTitle.setBounds(10, 90, 100, 25);
        addLessonPanel.add(lblNewLessonTitle);

        txtNewLessonTitle = new JTextField();
        txtNewLessonTitle.setBounds(110, 90, 200, 25);
        addLessonPanel.add(txtNewLessonTitle);

        JLabel lblNewLessonContent = new JLabel("Content:");
        lblNewLessonContent.setForeground(Color.WHITE);
        lblNewLessonContent.setBounds(10, 130, 100, 25);
        addLessonPanel.add(lblNewLessonContent);

        txtNewLessonContent = new JTextArea();
        txtNewLessonContent.setBounds(110, 130, 300, 200);
        addLessonPanel.add(txtNewLessonContent);

        btnSaveLesson = new JButton("Save");
        btnSaveLesson.setBounds(110, 350, 100, 35);
        addLessonPanel.add(btnSaveLesson);

        btnCancelLesson = new JButton("Cancel");
        btnCancelLesson.setBounds(220, 350, 100, 35);
        addLessonPanel.add(btnCancelLesson);

        // Edit Lesson Panel
        editLessonPanel = new JPanel(null);
        editLessonPanel.setBounds(10, 10, 970, 500);
        editLessonPanel.setBackground(new Color(75, 95, 110));
        editLessonPanel.setVisible(false);
        mainPanel.add(editLessonPanel);

        JLabel lblEditLesson = new JLabel("Edit Lesson");
        lblEditLesson.setForeground(Color.WHITE);
        lblEditLesson.setFont(new Font("Times New Roman", Font.BOLD, 18));
        lblEditLesson.setBounds(10, 10, 200, 25);
        editLessonPanel.add(lblEditLesson);

        JLabel lblEditLessonId = new JLabel("Lesson ID:");
        lblEditLessonId.setForeground(Color.WHITE);
        lblEditLessonId.setBounds(10, 50, 100, 25);
        editLessonPanel.add(lblEditLessonId);

        txtEditLessonId = new JTextField();
        txtEditLessonId.setBounds(110, 50, 200, 25);
        txtEditLessonId.setEditable(false);
        editLessonPanel.add(txtEditLessonId);

        JLabel lblEditLessonTitle = new JLabel("Title:");
        lblEditLessonTitle.setForeground(Color.WHITE);
        lblEditLessonTitle.setBounds(10, 90, 100, 25);
        editLessonPanel.add(lblEditLessonTitle);

        txtEditLessonTitle = new JTextField();
        txtEditLessonTitle.setBounds(110, 90, 200, 25);
        editLessonPanel.add(txtEditLessonTitle);

        JLabel lblEditLessonContent = new JLabel("Content:");
        lblEditLessonContent.setForeground(Color.WHITE);
        lblEditLessonContent.setBounds(10, 130, 100, 25);
        editLessonPanel.add(lblEditLessonContent);

        txtEditLessonContent = new JTextArea();
        txtEditLessonContent.setBounds(110, 130, 300, 200);
        editLessonPanel.add(txtEditLessonContent);

        btnUpdateLesson = new JButton("Update");
        btnUpdateLesson.setBounds(110, 350, 100, 35);
        editLessonPanel.add(btnUpdateLesson);

        btnCancelEditLesson = new JButton("Cancel");
        btnCancelEditLesson.setBounds(220, 350, 100, 35);
        editLessonPanel.add(btnCancelEditLesson);

        // quiz panels:
        createQuizPanel = new JPanel(null);
        createQuizPanel.setBounds(10, 10, 970, 250);
        createQuizPanel.setBackground(new Color(75, 95, 110));
        createQuizPanel.setVisible(false);
        mainPanel.add(createQuizPanel);

        JLabel lblQuizCreation = new JLabel("Create new quiz");
        lblQuizCreation.setForeground(Color.WHITE);
        lblQuizCreation.setFont(new Font("Times New Roman", Font.BOLD, 18));
        lblQuizCreation.setBounds(10, 10, 200, 25);
        createQuizPanel.add(lblQuizCreation);

        JLabel lblNewQuizId = new JLabel("Quiz ID:");
        lblNewQuizId.setForeground(Color.WHITE);
        lblNewQuizId.setBounds(10, 50, 100, 25);
        createQuizPanel.add(lblNewQuizId);

        txtNewQuizId = new JTextField();
        txtNewQuizId.setBounds(110, 50, 200, 25);
        txtNewQuizId.setEditable(false);
        createQuizPanel.add(txtNewQuizId);

        JLabel lblQuizPassMark = new JLabel("PassMark:");
        lblQuizPassMark.setForeground(Color.WHITE);
        lblQuizPassMark.setBounds(10, 90, 100, 25);
        createQuizPanel.add(lblQuizPassMark);

        txtQuizPassMark = new JTextField();
        txtQuizPassMark.setBounds(110, 90, 200, 25);
        createQuizPanel.add(txtQuizPassMark);

        btnContinueQuiz = new JButton("Continue..");
        btnContinueQuiz.setBounds(110, 150, 100, 35);
        createQuizPanel.add(btnContinueQuiz);

        btnCancelQuiz = new JButton("Cancel");
        btnCancelQuiz.setBounds(220, 150, 100, 35);
        createQuizPanel.add(btnCancelQuiz);

        addQuestionsPanel = new JPanel(null);
        addQuestionsPanel.setBounds(10, 10, 970, 400);
        addQuestionsPanel.setBackground(new Color(75, 95, 110));
        addQuestionsPanel.setVisible(false);
        mainPanel.add(addQuestionsPanel);

        JLabel lblAddQuestion = new JLabel("Write Question " + currentQuestionNum + ":");
        lblAddQuestion.setForeground(Color.WHITE);
        lblAddQuestion.setFont(new Font("Times New Roman", Font.BOLD, 18));
        lblAddQuestion.setBounds(10, 10, 400, 25);
        addQuestionsPanel.add(lblAddQuestion);

        JLabel lblQuestion = new JLabel("Question:");
        lblQuestion.setForeground(Color.WHITE);
        lblQuestion.setBounds(10, 40, 150, 25);
        addQuestionsPanel.add(lblQuestion);

        txtQuestion = new JTextArea();
        txtQuestion.setBounds(10, 70, 940, 30);
        addQuestionsPanel.add(txtQuestion);

        JLabel lblChoice1 = new JLabel("Choice 1:");
        lblChoice1.setForeground(Color.WHITE);
        lblChoice1.setBounds(10, 130, 100, 25);
        addQuestionsPanel.add(lblChoice1);

        txtChoice1 = new JTextArea();
        txtChoice1.setBounds(110, 130, 840, 30);
        addQuestionsPanel.add(txtChoice1);

        JLabel lblChoice2 = new JLabel("Choice 2:");
        lblChoice2.setForeground(Color.WHITE);
        lblChoice2.setBounds(10, 165, 100, 25);
        addQuestionsPanel.add(lblChoice2);

        txtChoice2 = new JTextArea();
        txtChoice2.setBounds(110, 165, 840, 30);
        addQuestionsPanel.add(txtChoice2);

        JLabel lblChoice3 = new JLabel("Choice 3:");
        lblChoice3.setForeground(Color.WHITE);
        lblChoice3.setBounds(10, 200, 100, 25);
        addQuestionsPanel.add(lblChoice3);

        txtChoice3 = new JTextArea();
        txtChoice3.setBounds(110, 200, 840, 30);
        addQuestionsPanel.add(txtChoice3);

        JLabel lblChoice4 = new JLabel("Choice 4:");
        lblChoice4.setForeground(Color.WHITE);
        lblChoice4.setBounds(10, 235, 100, 25);
        addQuestionsPanel.add(lblChoice4);

        txtChoice4 = new JTextArea();
        txtChoice4.setBounds(110, 235, 840, 30);
        addQuestionsPanel.add(txtChoice4);

        JLabel lblCorrectAns = new JLabel("Correct Answer:");
        lblCorrectAns.setForeground(Color.WHITE);
        lblCorrectAns.setBounds(10, 250, 150, 25);
        addQuestionsPanel.add(lblCorrectAns);

        String[] choices = {"Choice 1", "Choice 2", "Choice 3", "Choice 4"};
        comboCorrect = new JComboBox<>(choices);
        comboCorrect.setBounds(160, 270, 150, 30);
        addQuestionsPanel.add(comboCorrect);

        btnAddQuestion = new JButton("Add Question");
        btnAddQuestion.setBounds(50, 310, 100, 35);
        addQuestionsPanel.add(btnAddQuestion);

        btnClearQuestion = new JButton("Clear");
        btnClearQuestion.setBounds(170, 310, 100, 35);
        addQuestionsPanel.add(btnClearQuestion);

        btnFinishQuiz = new JButton("Finish Quiz");
        btnFinishQuiz.setBounds(290, 310, 150, 35);
        addQuestionsPanel.add(btnFinishQuiz);

        // Bottom Panel
        bottomPanel = new JPanel(null);
        bottomPanel.setBounds(0, 415, 980, 60);
        bottomPanel.setBackground(new Color(65, 85, 95));
        mainPanel.add(bottomPanel);
        btnLogout = new JButton("Logout");
        btnLogout.setBounds(200, 10, 120, 40);
        bottomPanel.add(btnLogout);
        // Actions
        btnViewLessons.addActionListener(e -> {
            try {
                int selectedRow = coursesTable.getSelectedRow();
                if (selectedRow == -1) {
                    throw new Exception("Please select a course first.");
                }
                int courseId = Integer.parseInt(coursesTable.getValueAt(selectedRow, 0).toString());
                loadLessons(courseId);
                showPanel(lessonsPanel);
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCloseLessons.addActionListener(e -> showHome());

        btnCreateQuiz.addActionListener(e -> {
            try {
                int selectedCourseRow = coursesTable.getSelectedRow();
                if (selectedCourseRow == -1)
                    throw new Exception("Please select a course first.");

                int selectedLessonRow = lessonsTable.getSelectedRow();
                if (selectedLessonRow == -1)
                    throw new Exception("Please select a lesson first.");

                int courseId = Integer.parseInt(coursesTable.getValueAt(selectedCourseRow, 0).toString());
                String courseTitle = coursesTable.getValueAt(selectedCourseRow, 1).toString();

                int lessonId = Integer.parseInt(lessonsTable.getValueAt(selectedLessonRow, 0).toString());
                String lessonTitle = lessonsTable.getValueAt(selectedLessonRow, 1).toString();

                showPanel(createQuizPanel);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });


        btnRemoveQuiz.addActionListener(e -> {
            try {
                int selectedLessonRow = lessonsTable.getSelectedRow();
                int selectedCourseRow = coursesTable.getSelectedRow();
                if (selectedCourseRow == -1) {
                    throw new Exception("Please select a course first.");
                }
                if (selectedLessonRow == -1) {
                    throw new Exception("Please select a lesson first.");
                }
                int courseId = Integer.parseInt(coursesTable.getValueAt(selectedCourseRow, 0).toString());
                int lessonId = Integer.parseInt(lessonsTable.getValueAt(selectedLessonRow, 0).toString());

                instructorService.removeQuizFromLesson(instructor.getUserId(), courseId, lessonId);
                loadLessons(courseId);
                JOptionPane.showMessageDialog(this, "Quiz removed from lesson successfully!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnSaveLesson.addActionListener(e -> {
            try {
                int selectedRow = coursesTable.getSelectedRow();
                if (selectedRow == -1) {
                    throw new Exception("Please select a course first.");
                }
                int courseId = Integer.parseInt(coursesTable.getValueAt(selectedRow, 0).toString());
                String title = txtNewLessonTitle.getText().trim();
                String content = txtNewLessonContent.getText().trim();
                instructorService.addLesson(courseId, title, content);
                loadLessons(courseId);
                showPanel(lessonsPanel);
                JOptionPane.showMessageDialog(this, "Lesson added successfully!");
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnDeleteLesson.addActionListener(e -> {
            try {
                int selectedRow1 = lessonsTable.getSelectedRow();
                int selectedRow2 = coursesTable.getSelectedRow();
                if (selectedRow1 == -1) {
                    throw new Exception("Please select a lesson to delete.");
                } else if (selectedRow2 == -1) {
                    throw new Exception("Please select a course first.");
                }

                int courseId = Integer.parseInt(coursesTable.getValueAt(selectedRow2, 0).toString());
                int lessonId = Integer.parseInt(lessonsTable.getValueAt(selectedRow1, 0).toString());
                instructorService.deleteLesson(courseId, lessonId);
                loadLessons(courseId);
                JOptionPane.showMessageDialog(this, "Lesson deleted successfully!");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCloseStudents.addActionListener(e -> showHome());

        btnViewStudents.addActionListener(e -> {
            try {
                int selectedRow = coursesTable.getSelectedRow();
                if (selectedRow == -1) {
                    throw new Exception("Please select a course first.");
                }

                int courseId = Integer.parseInt(coursesTable.getValueAt(selectedRow, 0).toString());
                loadEnrolledStudents(courseId);
                showPanel(enrolledStudentsPanel);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnAddCourse.addActionListener(e -> {
            showPanel(addCoursePanel);
            clearCourseFields();
        });

        btnCancelCourse.addActionListener(e -> showHome());

        btnSaveCourse.addActionListener(e -> addCourse());

        btnEditCourse.addActionListener(e -> editCourse());

        btnAddLesson.addActionListener(e -> {
            clearLessonFields();
            showPanel(addLessonPanel);
        });

        btnEditLesson.addActionListener(e -> editSelectedLesson());

        btnCancelLesson.addActionListener(e -> showPanel(lessonsPanel));

        btnDeleteCourse.addActionListener(e -> deleteCourse());

        btnLogout.addActionListener(e -> dispose());

        coursesTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                fillCourseFields();
            }
        });

        btnUpdateLesson.addActionListener(e -> updateLesson());

        btnCancelEditLesson.addActionListener(e -> showPanel(lessonsPanel));

// Quiz flow
        btnContinueQuiz.addActionListener(e -> {
            try {
                int selectedLessonRow = lessonsTable.getSelectedRow();
                if (selectedLessonRow == -1)
                    throw new Exception("Please select a lesson first.");

                int selectedCourseRow = coursesTable.getSelectedRow();
                if (selectedCourseRow == -1)
                    throw new Exception("Please select a course first.");

                int courseId = Integer.parseInt(coursesTable.getValueAt(selectedCourseRow, 0).toString());
                int lessonId = Integer.parseInt(lessonsTable.getValueAt(selectedLessonRow, 0).toString());

                quizId = (int) (System.currentTimeMillis() / 1000);

                int passMark;
                try {
                    passMark = Integer.parseInt(txtQuizPassMark.getText().trim());
                } catch (NumberFormatException nfe) {
                    throw new Exception("Pass mark must be a number.");
                }

                // Create quiz object locally
                currentQuiz = new Quiz(quizId, lessonId, courseId, passMark);

                txtNewQuizId.setText(String.valueOf(quizId));
                JOptionPane.showMessageDialog(this, "Quiz initialized. Now add questions.");

                showPanel(addQuestionsPanel);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancelQuiz.addActionListener(e -> showPanel(lessonsPanel));
        btnAnalytics.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int selectedRow = coursesTable.getSelectedRow();
                    if (selectedRow == -1) {
                        throw new Exception("Please select a course first.");
                    }
                    int courseId = Integer.parseInt(coursesTable.getValueAt(selectedRow, 0).toString());
                    openInsights(courseId);
                }

                catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

// Add Question
        btnAddQuestion.addActionListener(e -> {
            try {
                if (currentQuiz == null)
                    throw new Exception("Please create a quiz first.");

                String questionText = txtQuestion.getText().trim();
                String choice1 = txtChoice1.getText().trim();
                String choice2 = txtChoice2.getText().trim();
                String choice3 = txtChoice3.getText().trim();
                String choice4 = txtChoice4.getText().trim();
                int correctAnswerIndex = comboCorrect.getSelectedIndex(); // 0..3

                if (questionText.isEmpty() || choice1.isEmpty() || choice2.isEmpty())
                    throw new Exception("Question and at least two choices are required.");
                Question q = new Question(
                        currentQuestionNum,
                        currentQuiz.getQuizId(),
                        questionText
                );

                // Choices
                q.addChoice(choice1);
                q.addChoice(choice2);
                if (!choice3.isEmpty()) q.addChoice(choice3);
                if (!choice4.isEmpty()) q.addChoice(choice4);
                q.setCorrectAnsIndex(correctAnswerIndex);
                currentQuiz.addQuestionTo(currentQuestionNum, q);
                currentQuestionNum++;
                JOptionPane.showMessageDialog(this, "Question added.");
                btnClearQuestion.doClick();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });


        btnClearQuestion.addActionListener(e -> {
            txtQuestion.setText("");
            txtChoice1.setText("");
            txtChoice2.setText("");
            txtChoice3.setText("");
            txtChoice4.setText("");
            comboCorrect.setSelectedIndex(0);
        });

// Finish Quiz
        btnFinishQuiz.addActionListener(e -> {
            try {
                if (currentQuiz == null)
                    throw new Exception("No quiz to finish.");

                int selectedCourseRow = coursesTable.getSelectedRow();
                int selectedLessonRow = lessonsTable.getSelectedRow();

                if (selectedCourseRow == -1 || selectedLessonRow == -1)
                    throw new Exception("Please select a course and lesson first.");

                int courseId = Integer.parseInt(coursesTable.getValueAt(selectedCourseRow, 0).toString());
                int lessonId = Integer.parseInt(lessonsTable.getValueAt(selectedLessonRow, 0).toString());
                instructorService.createQuizForLesson(
                        courseId,
                        lessonId,
                        currentQuiz
                );

                currentQuiz = null;
                currentQuestionNum = 1;
                txtNewQuizId.setText("");
                txtQuizPassMark.setText("");

                JOptionPane.showMessageDialog(this, "Quiz finished and attached to the lesson successfully.");
                showPanel(lessonsPanel);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });


    }

    private void showHome() {
        coursesPanel.setVisible(true);
        courseInfoPanel.setVisible(true);
        lessonsPanel.setVisible(false);
        editLessonPanel.setVisible(false);
        enrolledStudentsPanel.setVisible(false);
        addCoursePanel.setVisible(false);
        addLessonPanel.setVisible(false);
        createQuizPanel.setVisible(false);
        addQuestionsPanel.setVisible(false);
        bottomPanel.setVisible(true);
    }

    private void showPanel(JPanel panelToShow) {
        coursesPanel.setVisible(false);
        courseInfoPanel.setVisible(false);
        lessonsPanel.setVisible(false);
        enrolledStudentsPanel.setVisible(false);
        addCoursePanel.setVisible(false);
        addLessonPanel.setVisible(false);
        editLessonPanel.setVisible(false);
        createQuizPanel.setVisible(false);
        addQuestionsPanel.setVisible(false);
        bottomPanel.setVisible(false);

        panelToShow.setVisible(true);
        if (panelToShow == coursesPanel || panelToShow == courseInfoPanel) {
            bottomPanel.setVisible(true);
        }
    }

    private void loadCreatedCourses() {
        DefaultTableModel model = (DefaultTableModel) coursesTable.getModel();
        List<Course> availableCourses = instructorService.getInstructorCourses(instructor.getUserId());
        model.setRowCount(0);
        for (Course c : availableCourses) {
            model.addRow(new Object[]{c.getCourseId(), c.getTitle(), c.getDescription(), c.getStatus()});
        }
    }

    private void addCourse() {
        try {
            String title = txtNewCourseTitle.getText().trim();
            String desc = txtNewCourseDesc.getText().trim();
            if (title.isEmpty() || desc.isEmpty()) {
                throw new Exception("Please fill all fields.");
            }
            Course created = instructorService.createCourse(instructor.getUserId(), title, desc);
            if (created != null) {
                JOptionPane.showMessageDialog(this, "Course added successfully!");
                loadCreatedCourses();
                showHome();
            } else {
                throw new Exception("Failed to add course!");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error while saving course. " + ex.getMessage());
        }
    }

    private void fillCourseFields() {
        int selectedRow = coursesTable.getSelectedRow();

        if (selectedRow != -1) {
            txtCourseId.setText(coursesTable.getValueAt(selectedRow, 0).toString());
            txtCourseTitle.setText(coursesTable.getValueAt(selectedRow, 1).toString());
            txtCourseDesc.setText(coursesTable.getValueAt(selectedRow, 2).toString());
        }
    }

    private void editCourse() {
        try {
            String idText = txtCourseId.getText().trim();
            String title = txtCourseTitle.getText().trim();
            String desc = txtCourseDesc.getText().trim();

            if (idText.isEmpty() || title.isEmpty() || desc.isEmpty()) {
                throw new Exception("Please fill all the fields.");
            }

            int courseId = Integer.parseInt(idText);

            instructorService.editCourse(courseId, title, desc);
            JOptionPane.showMessageDialog(this, "Course updated successfully!");
            loadCreatedCourses();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void loadLessons(int courseId) {
        DefaultTableModel model = (DefaultTableModel) lessonsTable.getModel();
        model.setRowCount(0);
        Course c = courseService.getCourseById(courseId);
        if (c != null) {
            c.getLessons().forEach(lesson ->
                    model.addRow(new Object[]{
                            lesson.getLessonId(),
                            lesson.getTitle(),
                            lesson.getContent()
                    })
            );
        }
    }

    private void deleteCourse() {
        int selectedRow = coursesTable.getSelectedRow();
        try {
            if (selectedRow == -1) {
                throw new Exception("Please select a course first.");
            }
            int courseId = Integer.parseInt(coursesTable.getValueAt(selectedRow, 0).toString());

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete this course?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                instructorService.deleteCourse(instructor.getUserId(), courseId);
                JOptionPane.showMessageDialog(this, "Course deleted successfully!");
                loadCreatedCourses();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void loadEnrolledStudents(int courseId) {
        DefaultTableModel model = (DefaultTableModel) studentsTable.getModel();
        model.setRowCount(0);
        List<Student> students = instructorService.viewEnrolledStudents(courseId);
        for (Student s : students) {
            model.addRow(new Object[]{
                    s.getUserId(),
                    s.getUsername(),
                    s.getEmail()
            });
        }
    }

    private void editSelectedLesson() {
        try {
            int selectedLessonRow = lessonsTable.getSelectedRow();
            int selectedCourseRow = coursesTable.getSelectedRow();

            if (selectedLessonRow == -1) {
                throw new Exception("Please select a lesson to edit.");
            }
            if (selectedCourseRow == -1) {
                throw new Exception("Please select a course first.");
            }

            int lessonId = Integer.parseInt(lessonsTable.getValueAt(selectedLessonRow, 0).toString());
            String oldTitle = lessonsTable.getValueAt(selectedLessonRow, 1).toString();
            String oldContent = lessonsTable.getValueAt(selectedLessonRow, 2).toString();

            txtEditLessonId.setText(String.valueOf(lessonId));
            txtEditLessonTitle.setText(oldTitle);
            txtEditLessonContent.setText(oldContent);
            showPanel(editLessonPanel);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void updateLesson() {
        try {
            int selectedLessonRow = lessonsTable.getSelectedRow();
            int selectedCourseRow = coursesTable.getSelectedRow();

            if (selectedLessonRow == -1) {
                throw new Exception("Please select a lesson to edit.");
            }
            if (selectedCourseRow == -1) {
                throw new Exception("Please select a course first.");
            }
            int lessonId = Integer.parseInt(lessonsTable.getValueAt(selectedLessonRow, 0).toString());
            int courseId = Integer.parseInt(coursesTable.getValueAt(selectedCourseRow, 0).toString());
            String newTitle = txtEditLessonTitle.getText().trim();
            String newContent = txtEditLessonContent.getText().trim();

            instructorService.editLesson(courseId, lessonId, newTitle, newContent);
            JOptionPane.showMessageDialog(this, "Lesson updated successfully!");
            loadLessons(courseId);
            showPanel(lessonsPanel);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    public void clearLessonFields() {
        txtNewLessonId.setText("");
        txtNewLessonTitle.setText("");
        txtNewLessonContent.setText("");
    }

    public void clearCourseFields() {
        txtNewCourseId.setText("");
        txtNewCourseTitle.setText("");
        txtNewCourseDesc.setText("");
    }
    private void openInsights(int courseId) {
        try {
            Map<String, Object> data = analytics.getInstructorInsights(courseId);
            Map<Integer, Double> lessonAverages =
                    (Map<Integer, Double>) data.get("quizAverages");
            double completionRate =
                    (double) data.get("completionRate");

            Map<String, Double> studentProgress =
                    (Map<String, Double>) data.get("studentProgress");

            Course c = courseService.getCourseById(courseId);
            Map<String, Double> lessonChartData = new HashMap<>();
            for (Lesson l : c.getLessons()) {
                double avg = lessonAverages.getOrDefault(l.getLessonId(), 0.0);
                lessonChartData.put(l.getTitle(), avg);
            }

            JFreeChart quizChart =
                    ChartGenerator.createLessonAverageChart(c.getTitle(), lessonChartData);

            ChartFrame frame1 = new ChartFrame("Lesson Quiz Averages", quizChart);
            frame1.setVisible(true);
            frame1.setSize(600, 400);
            frame1.setLocation(0, 0);
            JFreeChart completionChart =
                    ChartGenerator.createCompletionChart(c.getTitle(), completionRate);

            ChartFrame frame2 = new ChartFrame("Completion Rate", completionChart);
            frame2.setVisible(true);
            frame2.setSize(600, 400);
            frame2.setLocation(610, 0);
            JFreeChart studentChart =
                    ChartGenerator.createStudentProgressChart(c.getTitle(), studentProgress);

            ChartFrame frame3 = new ChartFrame("Student Progress", studentChart);
            frame3.setVisible(true);
            frame3.setSize(600, 400);
            frame3.setLocation(310, 420);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

}
