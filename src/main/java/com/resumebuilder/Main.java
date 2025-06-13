package com.resumebuilder;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import com.formdev.flatlaf.FlatLightLaf;

public class Main extends JFrame {
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private LoginPanel loginPanel;
    private ResumeBuilderPanel resumeBuilderPanel;
    private String currentUsername;

    public Main() {
        // Set up FlatLaf
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("Resume Builder");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        // Initialize components
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        loginPanel = new LoginPanel(this);
        resumeBuilderPanel = new ResumeBuilderPanel(this);

        // Add panels to card layout
        mainPanel.add(loginPanel, "LOGIN");
        mainPanel.add(resumeBuilderPanel, "RESUME_BUILDER");

        // Show login panel first
        cardLayout.show(mainPanel, "LOGIN");

        add(mainPanel);
    }

    public void showResumeBuilder() {
        if (currentUsername != null) {
            int userId = DatabaseUtil.getUserId(currentUsername);
            if (userId != -1) {
                resumeBuilderPanel.setCurrentUserId(userId);
            }
        }
        cardLayout.show(mainPanel, "RESUME_BUILDER");
    }

    public void showLogin() {
        cardLayout.show(mainPanel, "LOGIN");
    }

    public void setCurrentUsername(String username) {
        this.currentUsername = username;
    }

    public static void main(String[] args) {
        // Initialize database first
        DatabaseUtil.initializeDatabase();
        
        SwingUtilities.invokeLater(() -> {
            new Main().setVisible(true);
        });
    }
}