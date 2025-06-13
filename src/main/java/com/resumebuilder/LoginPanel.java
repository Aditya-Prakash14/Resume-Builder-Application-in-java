package com.resumebuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginPanel extends JPanel {
    private Main mainFrame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField emailField;
    private JButton loginButton;
    private JButton registerButton;
    private JButton switchToRegisterButton;
    private JButton switchToLoginButton;
    private boolean isLoginMode = true;

    public LoginPanel(Main mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new GridBagLayout());
        initializeComponents();
        setupLayout();
    }

    private void initializeComponents() {
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        emailField = new JTextField(20);
        loginButton = new JButton("Login");
        registerButton = new JButton("Register");
        switchToRegisterButton = new JButton("New user? Register");
        switchToLoginButton = new JButton("Already have an account? Login");

        // Add action listeners
        loginButton.addActionListener(e -> handleLogin());
        registerButton.addActionListener(e -> handleRegistration());
        switchToRegisterButton.addActionListener(e -> switchToRegister());
        switchToLoginButton.addActionListener(e -> switchToLogin());

        // Initially hide register-specific components
        emailField.setVisible(false);
        registerButton.setVisible(false);
        switchToLoginButton.setVisible(false);
    }

    private void setupLayout() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        JLabel titleLabel = new JLabel("Resume Builder");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(titleLabel, gbc);

        // Username
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        add(usernameField, gbc);

        // Password
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        add(passwordField, gbc);

        // Email (initially hidden)
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        add(emailField, gbc);

        // Buttons
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        buttonPanel.add(switchToRegisterButton);
        buttonPanel.add(switchToLoginButton);
        add(buttonPanel, gbc);
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (DatabaseUtil.authenticateUser(username, password)) {
            mainFrame.setCurrentUsername(username);
            mainFrame.showResumeBuilder();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleRegistration() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String email = emailField.getText();

        if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (DatabaseUtil.registerUser(username, password, email)) {
            JOptionPane.showMessageDialog(this, "Registration successful! Please login.", "Success", JOptionPane.INFORMATION_MESSAGE);
            switchToLogin();
        } else {
            JOptionPane.showMessageDialog(this, "Registration failed. Username or email might already exist.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void switchToRegister() {
        isLoginMode = false;
        emailField.setVisible(true);
        loginButton.setVisible(false);
        registerButton.setVisible(true);
        switchToRegisterButton.setVisible(false);
        switchToLoginButton.setVisible(true);
        revalidate();
        repaint();
    }

    private void switchToLogin() {
        isLoginMode = true;
        emailField.setVisible(false);
        loginButton.setVisible(true);
        registerButton.setVisible(false);
        switchToRegisterButton.setVisible(true);
        switchToLoginButton.setVisible(false);
        revalidate();
        repaint();
    }
} 