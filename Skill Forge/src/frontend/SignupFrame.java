package frontend;

import models.User;
import service.UserService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignupFrame extends JFrame {

    private JPanel mainPanel;
    private JTextField txtUsername, txtEmail;
    private JPasswordField txtPassword, txtConfirmPassword;
    private JComboBox<String> accountTypeCombo;
    private JButton btnSignup, btnCancel;
    private UserService userService;

    public SignupFrame(UserService userService) {
        this.userService = userService;
        initComponents();
    }

    private void initComponents() {
        setTitle("Sign Up");
        setSize(470, 515);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        mainPanel = new JPanel(null);
        mainPanel.setBounds(0, 0, 540, 560);
        mainPanel.setBackground(new Color(93, 109, 127));
        add(mainPanel);

        JPanel formPanel = new JPanel(null);
        formPanel.setBounds(10, 10, 440, 460);
        formPanel.setBackground(new Color(65, 85, 95));
        mainPanel.add(formPanel);

        JLabel lblTitle = new JLabel("Sign Up");
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 24));
        lblTitle.setBounds(160, 10, 200, 40);
        formPanel.add(lblTitle);

        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setForeground(Color.WHITE);
        lblUsername.setBounds(20, 70, 100, 25);
        formPanel.add(lblUsername);

        txtUsername = new JTextField();
        txtUsername.setBounds(150, 70, 240, 30);
        txtUsername.setBackground(new Color(80, 100, 120));
        txtUsername.setForeground(Color.WHITE);
        txtUsername.setCaretColor(Color.WHITE);
        formPanel.add(txtUsername);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setForeground(Color.WHITE);
        lblEmail.setBounds(20, 120, 100, 25);
        formPanel.add(lblEmail);

        txtEmail = new JTextField();
        txtEmail.setBounds(150, 120, 240, 30);
        txtEmail.setBackground(new Color(80, 100, 120));
        txtEmail.setForeground(Color.WHITE);
        txtEmail.setCaretColor(Color.WHITE);
        formPanel.add(txtEmail);

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setForeground(Color.WHITE);
        lblPassword.setBounds(20, 170, 100, 25);
        formPanel.add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(150, 170, 240, 30);
        txtPassword.setBackground(new Color(80, 100, 120));
        txtPassword.setForeground(Color.WHITE);
        txtPassword.setCaretColor(Color.WHITE);
        formPanel.add(txtPassword);

        JLabel lblConfirm = new JLabel("Confirm Password:");
        lblConfirm.setForeground(Color.WHITE);
        lblConfirm.setBounds(20, 220, 130, 25);
        formPanel.add(lblConfirm);

        txtConfirmPassword = new JPasswordField();
        txtConfirmPassword.setBounds(150, 220, 240, 30);
        txtConfirmPassword.setBackground(new Color(80, 100, 120));
        txtConfirmPassword.setForeground(Color.WHITE);
        txtConfirmPassword.setCaretColor(Color.WHITE);
        formPanel.add(txtConfirmPassword);

        JLabel lblType = new JLabel("Account Type:");
        lblType.setForeground(Color.WHITE);
        lblType.setBounds(20, 270, 120, 25);
        formPanel.add(lblType);

        accountTypeCombo = new JComboBox<>(new String[]{"Student", "Instructor"});
        accountTypeCombo.setBounds(150, 270, 240, 30);
        formPanel.add(accountTypeCombo);

        btnSignup = new JButton("Sign Up");
        btnSignup.setBounds(70, 350, 120, 38);
        btnSignup.setBackground(new Color(93, 109, 127));
        btnSignup.setForeground(Color.WHITE);
        formPanel.add(btnSignup);

        btnCancel = new JButton("Cancel");
        btnCancel.setBounds(250, 350, 120, 38);
        btnCancel.setBackground(new Color(93, 109, 127));
        btnCancel.setForeground(Color.WHITE);
        formPanel.add(btnCancel);

        btnSignup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                String username = txtUsername.getText().trim();
                String email = txtEmail.getText().trim();
                String password = new String(txtPassword.getPassword());
                String confirmPassword = new String(txtConfirmPassword.getPassword());
                String accountType = (String) accountTypeCombo.getSelectedItem();

                if (!password.equals(confirmPassword)) {
                    throw new Exception("Password and Confirm Password do not match!");
                }
                else if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    throw new Exception("Please fill all fields!");
                }
                else {
                    User user = userService.signup(email, username, password, accountType);
                    if (user != null) {
                        throw new Exception("Account created successfully!");
                    } else {
                        throw new Exception( "Signup failed. Email may already exist.");
                    }
                }}
                catch (Exception e1)
                {
                    JOptionPane.showMessageDialog(null, ""+e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

                }
            }
        });

        btnCancel.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        dispose();
                    }
                }
        );
    }
    }
