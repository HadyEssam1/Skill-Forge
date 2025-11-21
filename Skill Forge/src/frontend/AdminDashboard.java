package frontend;

import models.Admin;
import models.Course;
import service.AdminService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AdminDashboard extends JFrame {

    private JPanel mainPanel, coursesPanel, bottomPanel;
    private JTable pendingTable;
    private JButton btnApprove, btnReject, btnLogout;

    private AdminService adminService;
    private Admin admin;

    public AdminDashboard(Admin admin,AdminService adminService) {
        this.adminService = adminService;
        this.admin=admin;
        initComponents();
        loadPendingCourses();
    }

    private void initComponents() {
        setTitle("Admin Dashboard");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        // Main Panel
        mainPanel = new JPanel(null);
        mainPanel.setBounds(0, 0, 800, 500);
        mainPanel.setBackground(new Color(93, 109, 127));
        add(mainPanel);

        // Courses Panel
        coursesPanel = new JPanel(null);
        coursesPanel.setBounds(10, 10, 760, 380);
        coursesPanel.setBackground(new Color(65, 85, 95));
        mainPanel.add(coursesPanel);

        JLabel lblPending = new JLabel("Pending Courses");
        lblPending.setForeground(Color.WHITE);
        lblPending.setFont(new Font("Times New Roman", Font.BOLD, 18));
        lblPending.setBounds(10, 10, 200, 25);
        coursesPanel.add(lblPending);

        pendingTable = new JTable(
                new DefaultTableModel(
                        new String[]{"Course ID", "Title", "Description"}, 0
                )
        );
        JScrollPane scrollPending = new JScrollPane(pendingTable);
        scrollPending.setBounds(10, 40, 740, 250);
        coursesPanel.add(scrollPending);

        // Approve Button
        btnApprove = new JButton("Approve");
        btnApprove.setBounds(10, 310, 120, 35);
        coursesPanel.add(btnApprove);

        // Reject Button
        btnReject = new JButton("Reject");
        btnReject.setBounds(140, 310, 120, 35);
        coursesPanel.add(btnReject);

        // Bottom Panel
        bottomPanel = new JPanel(null);
        bottomPanel.setBounds(0, 400, 780, 60);
        bottomPanel.setBackground(new Color(65, 85, 95));
        mainPanel.add(bottomPanel);

        btnLogout = new JButton("Logout");
        btnLogout.setBounds(20, 10, 120, 40);
        bottomPanel.add(btnLogout);

        // ---------- Actions ----------
        btnApprove.addActionListener(e -> approveCourse());
        btnReject.addActionListener(e -> rejectCourse());
        btnLogout.addActionListener(e -> dispose());
    }

    private void loadPendingCourses() {
        DefaultTableModel model = (DefaultTableModel) pendingTable.getModel();
        model.setRowCount(0);
        List<Course> pending = adminService.reviewCreatedCourses();
        for (Course c : pending) {
            model.addRow(new Object[]{
                    c.getCourseId(),
                    c.getTitle(),
                    c.getDescription()
            });
        }
    }

    private void approveCourse() {
        try {
            int row = pendingTable.getSelectedRow();
            if (row == -1) throw new Exception("Select a course first.");
            int id = Integer.parseInt(pendingTable.getValueAt(row, 0).toString());
            adminService.approveCreatedCourse(id);
            JOptionPane.showMessageDialog(this, "Course approved!");
            loadPendingCourses();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    private void rejectCourse() {
        try {
            int row = pendingTable.getSelectedRow();
            if (row == -1) throw new Exception("Select a course first.");
            int id = Integer.parseInt(pendingTable.getValueAt(row, 0).toString());
            adminService.rejectCreatedCourse(id);
            JOptionPane.showMessageDialog(this, "Course rejected!");
            loadPendingCourses();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
}