package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TimerApp extends JFrame {
    private JButton startButton;
    private JButton stopButton;
    private Timer timer;
    private JLabel titleLabel;
    private JLabel timerLabel;
    private int hours;
    private int minutes;
    private int seconds;

    public TimerApp() {
        try {
            setTitle("Exam Monitor");
            setSize(400, 600);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);

            // Initialize components
            titleLabel = new JLabel("Exam Monitor");
            titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
            titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

            timerLabel = new JLabel("00 hr 00 min 00 sec");
            timerLabel.setFont(new Font("Arial", Font.PLAIN, 18));
            timerLabel.setHorizontalAlignment(SwingConstants.CENTER);

            startButton = new JButton("Start");
            startButton.setBackground(Color.GREEN);
            startButton.addActionListener(e -> startTimer());

            stopButton = new JButton("Stop");
            stopButton.setBackground(Color.RED);
            stopButton.addActionListener(e -> stopTimer());

            // Add menu bar
            JMenuBar menuBar = new JMenuBar();
            JMenu fileMenu = new JMenu("File");
            JMenuItem exitMenuItem = new JMenuItem("Exit");
            exitMenuItem.addActionListener(e -> System.exit(0));
            fileMenu.add(exitMenuItem);
            menuBar.add(fileMenu);
            setJMenuBar(menuBar);

            // Add components to the content pane
            Container container = getContentPane();
            container.setLayout(new BorderLayout());
            container.add(titleLabel, BorderLayout.NORTH); // Move titleLabel to the top center
            container.add(timerLabel, BorderLayout.CENTER);
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            buttonPanel.add(startButton);
            buttonPanel.add(stopButton);
            container.add(buttonPanel, BorderLayout.SOUTH);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void startTimer() {
        if (timer == null) {
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
                updateTimerLabel();
            });
            timer.start();
            startButton.setEnabled(false);
            stopButton.setEnabled(true);
        }
    }

    private void stopTimer() {
        if (timer != null) {
            timer.stop();
            timer = null;
            startButton.setEnabled(true);
            stopButton.setEnabled(false);
        }
    }

    private void updateTimerLabel() {
        String time = String.format("%02d hr %02d min %02d sec", hours, minutes, seconds);
        timerLabel.setText(time);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TimerApp timerApp = new TimerApp();
            timerApp.setVisible(true);
        });
    }
}
