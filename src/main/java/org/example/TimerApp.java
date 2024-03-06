package org.example;

import javax.swing.*;
import java.awt.*;

public class TimerApp extends JFrame {
    private final JButton startStopButton;
    private Timer timer;
    private final JLabel timerLabel;
    private int secondsElapsed;

    public TimerApp() {
        setTitle("Timer Application");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize components
        startStopButton = new JButton("Start");
        timerLabel = new JLabel("Timer: 0 seconds");

        // Add action listener to the button
        startStopButton.addActionListener(e -> {
            if (timer == null) {
                // Start button clicked
                startTimer();
            } else {
                // Stop button clicked
                stopTimer();
            }
        });

        // Add components to the content pane
        Container container = getContentPane();
        container.setLayout(new BorderLayout());
        container.add(startStopButton, BorderLayout.NORTH);
        container.add(timerLabel, BorderLayout.CENTER);
    }

    private void startTimer() {
        timer = new Timer(1000, e -> {
            secondsElapsed++;
            updateTimerLabel();
        });
        timer.start();
        startStopButton.setText("Stop");
    }

    private void stopTimer() {
        if (timer != null) {
            timer.stop();
            timer = null;
            startStopButton.setText("Start");
        }
    }

    private void updateTimerLabel() {
        SwingUtilities.invokeLater(() -> timerLabel.setText("Timer: " + secondsElapsed + " seconds"));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TimerApp timerApp = new TimerApp();
            timerApp.setVisible(true);
        });
    }
}
