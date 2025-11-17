package frontend;

import models.Course;
import models.Instructor;
import models.Student;
import service.CourseService;
import service.InstructorService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class InstructorDashboard extends JFrame {

    private JPanel mainPanel, coursesPanel, courseInfoPanel, lessonsPanel, enrolledStudentsPanel, bottomPanel;
    private JPanel addCoursePanel, addLessonPanel;
    private JTable coursesTable, lessonsTable, studentsTable;
    private JTextField txtCourseId, txtCourseTitle, txtCourseDesc,txtNewCourseId,txtNewCourseTitle;
    private JTextField txtLessonId, txtLessonTitle;
    private JTextArea txtLessonContent,txtNewCourseDesc;
    private JButton btnAddCourse, btnEditCourse, btnDeleteCourse,btnCancelCourse,btnSaveCourse;
    private JButton btnAddLesson, btnEditLesson, btnDeleteLesson,btnSaveLesson,btnCancelLesson;
    private JButton btnViewLessons, btnCloseLessons;
    private JButton btnViewStudents, btnCloseStudents, btnLogout;
    private Instructor instructor;
    private InstructorService instructorService;
    private CourseService courseService;
    private JPanel editLessonPanel;
    private JTextField txtEditLessonId;
    private JTextField txtEditLessonTitle;
    private JTextArea txtEditLessonContent;
    private JButton btnUpdateLesson, btnCancelEditLesson;

    public InstructorDashboard(Instructor instructor, InstructorService instructorService,CourseService courseService) {
        this.instructor=instructor;
        this.instructorService = instructorService;
        this.courseService = courseService;
        initComponents();
        loadCreatedCourses();
    }

    private void initComponents() {
        setTitle("Instructor Dashboard");
        setSize(1000, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        // ---------- Main Panel ----------
        mainPanel = new JPanel(null);
        mainPanel.setBounds(0, 0, 1000, 550);
        mainPanel.setBackground(new Color(93, 109, 127));
        add(mainPanel);

        // ---------- Courses Panel ----------
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
                new String[]{"Course ID", "Title", "Description"}, 0));
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

        // ---------- Course Info Panel ----------
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

        // ---------- Lessons Panel ----------
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

        // ---------- Enrolled Students Panel ----------
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

        // ---------- Add Course Panel ----------
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

        // ---------- Add Lesson Panel ----------
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

        JTextField txtNewLessonId = new JTextField();
        txtNewLessonId.setBounds(110, 50, 200, 25);
        addLessonPanel.add(txtNewLessonId);

        JLabel lblNewLessonTitle = new JLabel("Title:");
        lblNewLessonTitle.setForeground(Color.WHITE);
        lblNewLessonTitle.setBounds(10, 90, 100, 25);
        addLessonPanel.add(lblNewLessonTitle);

        JTextField txtNewLessonTitle = new JTextField();
        txtNewLessonTitle.setBounds(110, 90, 200, 25);
        addLessonPanel.add(txtNewLessonTitle);

        JLabel lblNewLessonContent = new JLabel("Content:");
        lblNewLessonContent.setForeground(Color.WHITE);
        lblNewLessonContent.setBounds(10, 130, 100, 25);
        addLessonPanel.add(lblNewLessonContent);

        JTextArea txtNewLessonContent = new JTextArea();
        txtNewLessonContent.setBounds(110, 130, 300, 200);
        addLessonPanel.add(txtNewLessonContent);

        btnSaveLesson = new JButton("Save");
        btnSaveLesson.setBounds(110, 350, 100, 35);
        addLessonPanel.add(btnSaveLesson);

         btnCancelLesson = new JButton("Cancel");
        btnCancelLesson.setBounds(220, 350, 100, 35);
        addLessonPanel.add(btnCancelLesson);
// ---------- Edit Lesson Panel ----------
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

        // ---------- Bottom Panel ----------
        bottomPanel = new JPanel(null);
        bottomPanel.setBounds(0, 415, 980, 60);
        bottomPanel.setBackground(new Color(65, 85, 95));
        mainPanel.add(bottomPanel);
        btnLogout = new JButton("Logout");
        btnLogout.setBounds(200, 10, 120, 40);
        bottomPanel.add(btnLogout);
        // ---------- Actions ----------
        btnViewLessons.addActionListener(e -> {
           try{
            int selectedRow = coursesTable.getSelectedRow();
            if (selectedRow == -1) {
                throw new Exception("Please select a course first.");
            }
            int courseId = Integer.parseInt(coursesTable.getValueAt(selectedRow, 0).toString());
            loadLessons(courseId);
            showPanel(lessonsPanel);}
            catch (Exception e1)
            {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        btnCloseLessons.addActionListener(e -> showHome());
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
            }
            catch (Exception e1)
            {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        btnDeleteLesson.addActionListener(e -> {
            try {
                int selectedRow1 = lessonsTable.getSelectedRow();
                int selectedRow2 = coursesTable.getSelectedRow();
                if (selectedRow1 == -1) {
                    throw new Exception("Please select a lesson to delete.");
                }
               else if (selectedRow2 == -1) {
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
        btnAddCourse.addActionListener(e -> showPanel(addCoursePanel));
        btnCancelCourse.addActionListener(e -> showHome());
        btnSaveCourse.addActionListener(e -> addCourse());
        btnEditCourse.addActionListener(e->editCourse());
        btnAddLesson.addActionListener(e -> {
            showPanel(addLessonPanel);
        });
        btnEditLesson.addActionListener(e -> editSelectedLesson());
        btnCancelLesson.addActionListener(e -> showPanel(lessonsPanel));
        btnDeleteCourse.addActionListener(e -> deleteCourse());
        btnLogout.addActionListener(e -> dispose());
        coursesTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                fillCourseFields();            }
        });
        btnUpdateLesson.addActionListener(e -> updateLesson());
    }

    // ---------- Show Home ----------
    private void showHome() {
        coursesPanel.setVisible(true);
        courseInfoPanel.setVisible(true);
        lessonsPanel.setVisible(false);
        enrolledStudentsPanel.setVisible(false);
        addCoursePanel.setVisible(false);
        addLessonPanel.setVisible(false);
        bottomPanel.setVisible(true);
    }
    // ---------- Show any other panel ----------
    private void showPanel(JPanel panelToShow) {
        coursesPanel.setVisible(false);
        courseInfoPanel.setVisible(false);
        lessonsPanel.setVisible(false);
        enrolledStudentsPanel.setVisible(false);
        addCoursePanel.setVisible(false);
        addLessonPanel.setVisible(false);
        bottomPanel.setVisible(panelToShow == coursesPanel || panelToShow == courseInfoPanel);
        panelToShow.setVisible(true);
    }
    private void loadCreatedCourses() {
        DefaultTableModel model = (DefaultTableModel) coursesTable.getModel();
        List<Course> availableCourses = instructorService.getInstructorCourses(instructor.getUserId());
        model.setRowCount(0);
        for (Course c : availableCourses) {
            model.addRow(new Object[]{c.getCourseId(), c.getTitle(), c.getDescription()});
        }}
    private void addCourse () {
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
                JOptionPane.showMessageDialog(this, "Error while saving course." + ex.getMessage());
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
        try{
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

            if (confirm == JOptionPane.YES_OPTION){
            instructorService.deleteCourse(instructor.getUserId(),courseId);
            JOptionPane.showMessageDialog(this, "Course deleted successfully!");
            loadCreatedCourses();}
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
            int courseId = Integer.parseInt(coursesTable.getValueAt(selectedCourseRow, 0).toString());
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
            editLessonPanel.setVisible(false);
            showPanel(lessonsPanel);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }


}
