package frontend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import models.Certificate;
import models.Course;
import models.Student;


public class CertificateDashboardPanel extends JPanel {

    private JButton downloadButton;
    private final Certificate certificate;
    private final Course course;

    public CertificateDashboardPanel(Certificate certificate,Course course,ActionListener downloadListener) {
        this.certificate=certificate;
        this.course =course;
        setBackground(new Color(245,249,255));
        setLayout(new BorderLayout(15,15));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0,5,0,0,new Color(42,107,245)),
                BorderFactory.createEmptyBorder(15,20,15,20)
        ));
        JLabel titleLabel=new JLabel("Certificate of Completion",SwingConstants.LEFT);
        titleLabel.setFont(new Font("Arial",Font.BOLD,16));
        titleLabel.setForeground(new Color(42,107,245));
        add(titleLabel, BorderLayout.NORTH);

        JPanel contentPanel=new JPanel(new GridLayout(3,1,5,5));
        contentPanel.setOpaque(false);

        JLabel courseLabel = new JLabel("Course: "+course.getTitle());
        courseLabel.setFont(new Font("Arial",Font.PLAIN,14));

        JLabel studentLabel = new JLabel("Awarded to: "+student.getName());
        studentLabel.setFont(new Font("Arial",Font.BOLD,18));

        JLabel issueLabel=new JLabel("Issued: "+certificate.getIssueDate().toLocalDate().toString()+" (ID: " + certificate.getCertificateID() + ")");
        issueLabel.setFont(new Font("Arial",Font.ITALIC,12));

        contentPanel.add(studentLabel);
        contentPanel.add(courseLabel);
        contentPanel.add(issueLabel);
        add(contentPanel, BorderLayout.CENTER);

        downloadButton=new JButton("Download JSON Certificate ");
        downloadButton.setBackground(new Color(52, 168, 83));
        downloadButton.setForeground(Color.WHITE);
        downloadButton.setFocusPainted(false);
        downloadButton.setBorder(BorderFactory.createEmptyBorder(10,25,10,25));
        downloadButton.setFont(new Font("Arial",Font.BOLD,14));

        downloadButton.addActionListener(downloadListener);

        JPanel buttonWrapper=new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonWrapper.setOpaque(false);
        buttonWrapper.add(downloadButton);
        add(buttonWrapper,BorderLayout.SOUTH);
    }
    public Certificate getCertificate() {
        return certificate;
    }

    public Course getCourse() {
        return course;
    }
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(400,180);
    }
    private String getStudentName() {
        return "The Student";
    }
}
