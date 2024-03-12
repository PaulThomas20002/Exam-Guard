package org.example;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

public class TimerApp extends JFrame {

    private JLabel timerLabel;
    private JButton startButton, stopButton;
    private Timer timer;
    private long startTime;
    private final long targetTime = 3 * 60 * 60 * 1000; // 3 hours in milliseconds

    public TimerApp() {
        try {
            // Set background color to white
            this.getContentPane().setBackground(Color.WHITE);

            // Set layout
            this.setLayout(new BorderLayout());

            // Create timer label with fixed font and alignment
            timerLabel = new JLabel("03:00:00", SwingConstants.CENTER);
            timerLabel.setFont(new Font("Arial", Font.BOLD, 24));
            this.add(timerLabel, BorderLayout.CENTER);

            // Create buttons with fixed layout
            JPanel buttonPanel = new JPanel();
            startButton = new JButton("Start");
            stopButton = new JButton("Stop");
            stopButton.setEnabled(false); // Initially disabled
            buttonPanel.add(startButton);
            buttonPanel.add(stopButton);
            this.add(buttonPanel, BorderLayout.SOUTH);

            // Add action listeners
            startButton.addActionListener(e -> {
                startTimer();
                startButton.setEnabled(false);
                stopButton.setEnabled(true);
            });
            stopButton.addActionListener(e -> {
                stopTimer();
                startButton.setEnabled(true);
                stopButton.setEnabled(false);
            });

            // Set default close operation
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            // Set window size and location
            this.setSize(400, 800);
            this.setLocationRelativeTo(null);
            this.setTitle("Countdown Timer");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void startTimer() {
        if (timer == null) { // Ensure timer is not already running
            startTime = System.currentTimeMillis();
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    long remainingTime = targetTime - (System.currentTimeMillis() - startTime);
                    if (remainingTime <= 0) {
                        stopTimer();
                        timerLabel.setText("00:00:00");
                        // Add your logic for handling timer completion (e.g., notification)
                        System.out.println("Countdown complete!");
                    } else {
                        long hours = remainingTime / (1000 * 60 * 60);
                        long minutes = (remainingTime % (1000 * 60 * 60)) / (1000 * 60);
                        long seconds = (remainingTime % (1000 * 60)) / 1000;
                        // Use DecimalFormat for zero-padding (optional)
                        DecimalFormat twoDigits = new DecimalFormat("00");
                        timerLabel.setText(twoDigits.format(hours) + ":" + twoDigits.format(minutes) + ":" + twoDigits.format(seconds));
                    }
                }
            }, 0, 1000); // Update every second
        }
    }

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}
