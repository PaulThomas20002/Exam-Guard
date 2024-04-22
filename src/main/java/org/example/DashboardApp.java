package org.example;

import javax.swing.*;
import java.awt.*;

public class DashboardApp extends JFrame {

    private JTextArea loggingTextArea;
    private JPanel usbBox;
    private JPanel networkBox;
    private JPanel loggingBox;
    private JPanel fileIsolationBox;
    private JLabel statusLabel;

    public DashboardApp() {
        // Set up the frame
        setTitle("Exam Guard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Create the main panel for the dashboard layout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 240, 240)); // Light gray background

        // Create the text component for Exam Guard
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBackground(new Color(240, 240, 240));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 0));
        JTextArea examGuardTextArea = new JTextArea("Exam Guard");
        examGuardTextArea.setFont(new Font("Arial", Font.BOLD, 36));
        examGuardTextArea.setEditable(false);
        examGuardTextArea.setForeground(new Color(2, 48, 71)); // Dark Blue color
        examGuardTextArea.setOpaque(false);
        examGuardTextArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        titlePanel.add(examGuardTextArea);

        // Create the subtitle component
        JTextArea subtitleTextArea = new JTextArea("Ensure the lab exam's integrity");
        subtitleTextArea.setFont(new Font("Arial", Font.PLAIN, 20));
        subtitleTextArea.setEditable(false);
        subtitleTextArea.setForeground(new Color(2, 48, 60)); // Dark blue color
        subtitleTextArea.setOpaque(false);
        subtitleTextArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        titlePanel.add(subtitleTextArea);

        // Create the buttons panel for Start and Stop buttons
        JPanel buttonsPanel = new JPanel(new GridLayout(2, 1));

        JButton startButton = new JButton("Start");
        startButton.setBackground(new Color(76, 175, 80)); // Light green color
        startButton.setForeground(Color.WHITE);
        startButton.setFont(new Font("Arial", Font.BOLD, 18));

        JButton stopButton = new JButton("Stop");
        stopButton.setBackground(new Color(244, 67, 54)); // Light red color
        stopButton.setForeground(Color.WHITE);
        stopButton.setFont(new Font("Arial", Font.BOLD, 18));

        JPanel wrappedStartButton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrappedStartButton.add(startButton);
        JPanel wrappedStopButton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrappedStopButton.add(stopButton);

        buttonsPanel.add(wrappedStartButton);
        buttonsPanel.add(wrappedStopButton);

        // Set preferred size for the wrapped button panels
        wrappedStartButton.setPreferredSize(new Dimension(100, 40)); // Adjust size as needed
        wrappedStopButton.setPreferredSize(new Dimension(100, 40)); // Adjust size as needed
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(250, 0, 0, 0));

        //buttonsPanel.add(startButton);
        //buttonsPanel.add(stopButton);

        // Create the status panel to display current status
        JPanel statusPanel = new JPanel();
        statusPanel.setBackground(new Color(240, 240, 240)); // Light gray background
        statusPanel.setPreferredSize(new Dimension(200, 100)); // Adjust size as needed
        statusLabel = new JLabel("Status: Idle");
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        statusPanel.add(statusLabel);

        // Create the logging text area with scroll bar
        loggingTextArea = new JTextArea();
        loggingTextArea.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(loggingTextArea);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 2)); // Light gray border

        // Create the boxes for USB, Network, Logging, and File Isolation
        usbBox = createBox("USB", new Color(176, 190, 197)); // Grayish Blue color
        networkBox = createBox("Network", new Color(176, 190, 197)); // Grayish Blue color
        loggingBox = createBox("Logging", new Color(176, 190, 197)); // Grayish Blue color
        fileIsolationBox = createBox("File Isolation", new Color(176, 190, 197)); // Grayish Blue color

        // Add components to the main panel
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(buttonsPanel, BorderLayout.WEST);
        mainPanel.add(statusPanel, BorderLayout.SOUTH);

        // Create a panel for the right side components (logging text area and boxes)
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(scrollPane, BorderLayout.CENTER);
        JPanel boxesPanel = new JPanel(new GridLayout(1, 4, 10, 10)); // Adjust spacing between boxes
        boxesPanel.add(usbBox);
        boxesPanel.add(networkBox);
        boxesPanel.add(loggingBox);
        boxesPanel.add(fileIsolationBox);
        rightPanel.add(boxesPanel, BorderLayout.NORTH);
        mainPanel.add(rightPanel, BorderLayout.CENTER);

        // Add the main panel to the frame
        getContentPane().add(mainPanel);

        // Center the frame on the screen
        setLocationRelativeTo(null);

        // Add action listeners to the buttons
        startButton.addActionListener(e -> {
            setStatus("Status: Running");
            setBoxColor(new Color(24,119,242)); // Light blue color
            appendToLog("Start button clicked");
        });

        stopButton.addActionListener(e -> {
            setStatus("Status: Idle");
            setBoxColor(new Color(176, 190, 197)); // Grayish blue color
            appendToLog("Stop button clicked");
        });
    }

    private JPanel createBox(String title, Color color) {
        JPanel box = new JPanel();
        box.setPreferredSize(new Dimension(150, 150)); // Adjust size as needed
        box.setLayout(new BorderLayout());
        box.setBorder(BorderFactory.createLineBorder(Color.BLACK, 0));
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        box.add(titleLabel, BorderLayout.CENTER);
        box.setBackground(color);
        box.setBorder(BorderFactory.createEmptyBorder(100, 0, 0, 0));
        return box;
    }

    private void setStatus(String status) {
        statusLabel.setText(status);
    }

    private void setBoxColor(Color color) {
        usbBox.setBackground(color);
        networkBox.setBackground(color);
        loggingBox.setBackground(color);
        fileIsolationBox.setBackground(color);
    }

    private void appendToLog(String message) {
        loggingTextArea.append(message + "\n");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DashboardApp app = new DashboardApp();
            app.setVisible(true);
        });
    }
}
