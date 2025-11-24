package frontend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.*;
import java.time.format.DateTimeFormatter;

import models.Certificate;
import models.Course;
import models.Student;

public class CertificateDashboardPanel extends JPanel {

    private JButton downloadButton;
    private JButton closeButton;

    private final Certificate certificate;
    private final Course course;
    private final Student student;

    public CertificateDashboardPanel(Student student,
                                     Certificate certificate,
                                     Course course,
                                     ActionListener downloadListener,
                                     ActionListener closeListener) {

        this.student = student;
        this.certificate = certificate;
        this.course = course;

        setBackground(new Color(245, 249, 255));
        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 5, 0, 0, new Color(42, 107, 245)),
                BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));

        // ---------- Title ----------
        JLabel titleLabel = new JLabel("Certificate of Completion", SwingConstants.LEFT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(new Color(42, 107, 245));
        add(titleLabel, BorderLayout.NORTH);

        // ---------- Content Panel ----------
        JPanel contentPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        contentPanel.setOpaque(false);

        JLabel studentLabel = new JLabel("Awarded to: " + safe(student.getUsername()));
        studentLabel.setFont(new Font("Arial", Font.BOLD, 18));

        JLabel courseLabel = new JLabel("Course: " + safe(course.getTitle()));
        courseLabel.setFont(new Font("Arial", Font.PLAIN, 15));

        String issueDateStr = formatIssueDate(certificate.getIssueDate());

        JLabel issueLabel = new JLabel(
                "Issued: " + issueDateStr + "  (ID: " + certificate.getCertificateID() + ")"
        );
        issueLabel.setFont(new Font("Arial", Font.ITALIC, 12));

        contentPanel.add(studentLabel);
        contentPanel.add(courseLabel);
        contentPanel.add(issueLabel);

        add(contentPanel, BorderLayout.CENTER);

        // ---------- Buttons Panel ----------
        downloadButton = new JButton("Download JSON Certificate");
        downloadButton.setBackground(new Color(52, 168, 83));
        downloadButton.setForeground(Color.WHITE);
        downloadButton.setFocusPainted(false);
        downloadButton.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        downloadButton.setFont(new Font("Arial", Font.BOLD, 14));
        if (downloadListener != null)
            downloadButton.addActionListener(downloadListener);

        // New Close Button (Only addition you asked for)
        closeButton = new JButton("Close");
        closeButton.setBackground(new Color(220, 53, 69));
        closeButton.setForeground(Color.WHITE);
        closeButton.setFocusPainted(false);
        closeButton.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        closeButton.setFont(new Font("Arial", Font.BOLD, 14));
        if (closeListener != null)
            closeButton.addActionListener(closeListener);

        JPanel buttonWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        buttonWrapper.setOpaque(false);
        buttonWrapper.add(downloadButton); // untouched
        buttonWrapper.add(closeButton);    // just added

        add(buttonWrapper, BorderLayout.SOUTH);
    }

    private String safe(String s) {
        return (s == null || s.isEmpty()) ? "N/A" : s;
    }

    private String formatIssueDate(Object obj) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            if (obj instanceof LocalDate date)
                return date.format(fmt);

            if (obj instanceof LocalDateTime dt)
                return dt.toLocalDate().format(fmt);

            if (obj instanceof Instant instant)
                return instant.atZone(ZoneId.systemDefault())
                        .toLocalDate().format(fmt);

            if (obj instanceof java.util.Date d)
                return d.toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate().format(fmt);

            if (obj instanceof String s)
                return LocalDate.parse(s).format(fmt);

        } catch (Exception ignored) { }

        return "Unknown Date";
    }
}
