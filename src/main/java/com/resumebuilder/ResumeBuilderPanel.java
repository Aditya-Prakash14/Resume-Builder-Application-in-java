package com.resumebuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileOutputStream;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.util.List;

public class ResumeBuilderPanel extends JPanel {
    private Main mainFrame;
    private JTextArea contentArea;
    private JTextField titleField;
    private JComboBox<String> templateComboBox;
    private JList<Resume> resumeList;
    private DefaultListModel<Resume> resumeListModel;
    private int currentUserId; // This should be set when user logs in

    public ResumeBuilderPanel(Main mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());
        initializeComponents();
        setupLayout();
    }

    private void initializeComponents() {
        // Initialize components
        titleField = new JTextField(20);
        contentArea = new JTextArea();
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        
        String[] templates = {"Professional", "Creative", "Simple"};
        templateComboBox = new JComboBox<>(templates);
        
        resumeListModel = new DefaultListModel<>();
        resumeList = new JList<>(resumeListModel);
        resumeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Add action listeners
        resumeList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Resume selected = resumeList.getSelectedValue();
                if (selected != null) {
                    titleField.setText(selected.getTitle());
                    contentArea.setText(selected.getContent());
                }
            }
        });
    }

    private void setupLayout() {
        // Left panel for resume list
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createTitledBorder("My Resumes"));
        leftPanel.add(new JScrollPane(resumeList), BorderLayout.CENTER);
        
        JButton newResumeButton = new JButton("New Resume");
        newResumeButton.addActionListener(e -> createNewResume());
        leftPanel.add(newResumeButton, BorderLayout.SOUTH);

        // Right panel for resume editor
        JPanel rightPanel = new JPanel(new BorderLayout());
        
        // Top controls
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("Title:"));
        topPanel.add(titleField);
        topPanel.add(new JLabel("Template:"));
        topPanel.add(templateComboBox);
        
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> saveResume());
        topPanel.add(saveButton);
        
        JButton exportButton = new JButton("Export PDF");
        exportButton.addActionListener(e -> exportToPDF());
        topPanel.add(exportButton);
        
        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(e -> deleteResume());
        topPanel.add(deleteButton);
        
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> logout());
        topPanel.add(logoutButton);
        
        rightPanel.add(topPanel, BorderLayout.NORTH);
        
        // Content area
        JScrollPane scrollPane = new JScrollPane(contentArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Resume Content"));
        rightPanel.add(scrollPane, BorderLayout.CENTER);

        // Add panels to main layout
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setDividerLocation(200);
        add(splitPane, BorderLayout.CENTER);
    }

    private void createNewResume() {
        titleField.setText("New Resume");
        contentArea.setText("PERSONAL INFORMATION\n" +
                "Name: [Your Name]\n" +
                "Email: [Your Email]\n" +
                "Phone: [Your Phone]\n" +
                "Address: [Your Address]\n\n" +
                "PROFESSIONAL SUMMARY\n" +
                "[Brief summary of your professional background and key skills]\n\n" +
                "WORK EXPERIENCE\n" +
                "Job Title - Company Name (Start Date - End Date)\n" +
                "• [Achievement or responsibility]\n" +
                "• [Achievement or responsibility]\n" +
                "• [Achievement or responsibility]\n\n" +
                "EDUCATION\n" +
                "Degree - Institution Name (Graduation Year)\n" +
                "• [Relevant coursework or achievements]\n\n" +
                "SKILLS\n" +
                "• [Technical skill]\n" +
                "• [Technical skill]\n" +
                "• [Soft skill]\n" +
                "• [Soft skill]");
        resumeList.clearSelection();
    }

    private void saveResume() {
        String title = titleField.getText().trim();
        String content = contentArea.getText().trim();
        
        if (title.isEmpty() || content.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in both title and content", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (currentUserId <= 0) {
            JOptionPane.showMessageDialog(this, "User session expired. Please login again.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Resume selected = resumeList.getSelectedValue();
            if (selected != null) {
                DatabaseUtil.updateResume(selected.getId(), title, content);
                JOptionPane.showMessageDialog(this, "Resume updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                DatabaseUtil.saveResume(currentUserId, title, content);
                JOptionPane.showMessageDialog(this, "Resume saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
            
            refreshResumeList();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error saving resume: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void deleteResume() {
        Resume selected = resumeList.getSelectedValue();
        if (selected != null) {
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Are you sure you want to delete this resume?", 
                "Confirm Delete", 
                JOptionPane.YES_NO_OPTION);
                
            if (confirm == JOptionPane.YES_OPTION) {
                DatabaseUtil.deleteResume(selected.getId());
                refreshResumeList();
                createNewResume();
            }
        }
    }

    private void exportToPDF() {
        String title = titleField.getText();
        String content = contentArea.getText();
        
        if (title.isEmpty() || content.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in both title and content", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save PDF");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("PDF files", "pdf"));
        
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream(fileChooser.getSelectedFile()));
                document.open();
                
                // Add title
                com.itextpdf.text.Font titleFont = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 18, com.itextpdf.text.Font.BOLD);
                Paragraph titleParagraph = new Paragraph(title, titleFont);
                titleParagraph.setAlignment(Element.ALIGN_CENTER);
                document.add(titleParagraph);
                document.add(Chunk.NEWLINE);
                
                // Add content
                com.itextpdf.text.Font contentFont = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 12, com.itextpdf.text.Font.NORMAL);
                Paragraph contentParagraph = new Paragraph(content, contentFont);
                document.add(contentParagraph);
                
                document.close();
                JOptionPane.showMessageDialog(this, "PDF exported successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error exporting PDF: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void refreshResumeList() {
        resumeListModel.clear();
        if (currentUserId > 0) {
            try {
                List<Resume> resumes = DatabaseUtil.getResumes(currentUserId);
                for (Resume resume : resumes) {
                    resumeListModel.addElement(resume);
                }
                System.out.println("Loaded " + resumes.size() + " resumes for user ID: " + currentUserId);
            } catch (Exception e) {
                System.err.println("Error loading resumes: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to logout?", 
            "Confirm Logout", 
            JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            // Clear current data
            titleField.setText("");
            contentArea.setText("");
            resumeListModel.clear();
            currentUserId = 0;
            
            // Return to login screen
            mainFrame.setCurrentUsername(null);
            mainFrame.showLogin();
        }
    }

    public void setCurrentUserId(int userId) {
        this.currentUserId = userId;
        System.out.println("Setting current user ID to: " + userId);
        refreshResumeList();
    }
}