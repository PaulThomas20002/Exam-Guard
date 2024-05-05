package org.example;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DashboardApp extends JFrame {
    private final USBEnable usbEnable;
    private final USBBlocker usbBlocker;
    private final JTextArea loggingTextArea;
    private final JPanel usbBox;
    private final JPanel networkBox;
    private final JPanel loggingBox;
    private final JPanel fileIsolationBox;
    private final JLabel statusLabel;

    // Timer variables
    private final JLabel timerLabel;
    private final Timer timer;
    private int seconds = 0;
    private int minutes = 0;
    private int hours = 0;

    private boolean monitoring = false;

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
        usbBlocker = new USBBlocker();
        usbEnable = new USBEnable();
        // Add action listeners to the buttons
        startButton.addActionListener(e -> {
            setStatus("Status: Running");
            setBoxColor(new Color(24, 119, 242)); // Light blue color
            appendToLog("Start button clicked");
            startMonitoring();
            startTimer(); // Start the timer when start button is clicked
            Thread blockingThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    usbBlocker.Block();
                }
            });
            blockingThread.start();
            checkForPKExecAndSudo();
        });

        stopButton.addActionListener(e -> {
            setStatus("Status: Idle");
            setBoxColor(new Color(176, 190, 197)); // Grayish blue color
            appendToLog("Stop button clicked");
            stopTimer(); // Stop the timer when stop button is clicked
            Thread enableThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    usbEnable.enable();
                }
            });
            enableThread.start();
            checkForPKExecAndSudo(); // Launch ExamStopper for authentication
            // new ExamStopper().setVisible(true);

        });

        // Create the timer label
        timerLabel = new JLabel("00:00:00");
        timerLabel.setFont(new Font("Arial", Font.BOLD, 18));

        // Add the timer label to the top-right corner
        JPanel topRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topRightPanel.setBackground(new Color(240, 240, 240));
        titlePanel.add(topRightPanel, BorderLayout.NORTH);
        topRightPanel.add(timerLabel);

        // Initialize the timer
        timer = new Timer(1000, e -> {
            seconds++;
            if (seconds == 60) {
                seconds = 0;
                minutes++;
                if (minutes == 60) {
                    minutes = 0;
                    hours++;
                }
            }
            String timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);
            timerLabel.setText(timeString);
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

    private void startMonitoring() {
        if (!monitoring) {
            monitoring = true;
            new Thread(() -> {
                try {
                    // Start monitoring the active applications
                    Process process = Runtime.getRuntime().exec("ps -eo comm");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    String line;
                    while (monitoring && (line = reader.readLine()) != null) {
                        appendToLog("Active Application: " + line.trim());
                    }
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    private void checkForPKExecAndSudo() {
        new Thread(() -> {
            try {
                Process process = Runtime.getRuntime().exec("ps -eo args");
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.contains("pkexec")) {
                        appendToLog("User is using pkexec");
                    } else if (line.contains("sudo")) {
                        appendToLog("User is using sudo");
                    }
                    try {
                        Thread.sleep(1000); // Adjust the sleep duration as needed
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    // Start the timer
    private void startTimer() {
        timer.start();
    }

    // Stop the timer
    private void stopTimer() {
        timer.stop();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DashboardApp dashboardApp = new DashboardApp();
            dashboardApp.setVisible(true);
        });
    }
}

