package org.example;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.List;

public class UserActivityMonitor extends JFrame {

    private JTextArea activityLogTextArea;
    private JLabel malpracticeLevelLabel;
    private ApplicationLogger logger;

    public UserActivityMonitor() {
        // Initialize the application logger
        logger = new ApplicationLogger();

        // Set up the frame
        setTitle("User Activity Monitor");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create components
        activityLogTextArea = new JTextArea(10, 30);
        activityLogTextArea.setEditable(false);

        malpracticeLevelLabel = new JLabel("Malpractice Level: 0.0%");

        // Create a scroll pane for the text area
        JScrollPane scrollPane = new JScrollPane(activityLogTextArea);

        // Add components to the content pane
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(scrollPane, BorderLayout.CENTER);
        contentPane.add(malpracticeLevelLabel, BorderLayout.SOUTH);

        // Call method to update logs and malpractice level
        updateLogsAndMalpracticeLevel();
    }

    private void updateLogsAndMalpracticeLevel() {
        // Clear the activity log text area
        activityLogTextArea.setText("");

        // Log the running applications
        Log.findRunningApplication(activityLogTextArea);

        // Log other user activities
        logger.logActivity("Opened web browser");
        logger.logActivity("Accessed social media");
        logger.logActivity("Closed web browser");
        logger.logActivity("web content");
        logger.logActivity("browser");

        // Update the malpractice level label
        updateMalpracticeLevel();
    }

    private void updateMalpracticeLevel() {
        double malpracticeLevel = logger.getMalpracticeLevel();
        DecimalFormat df = new DecimalFormat("#0.00");
        malpracticeLevelLabel.setText("Malpractice Level: " + df.format(malpracticeLevel) + "%");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UserActivityMonitor app = new UserActivityMonitor();
            app.setVisible(true);
        });
    }
}
