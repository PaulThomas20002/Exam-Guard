package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExamStopper extends JFrame {

    private JButton stopButton;
    private final String correctPassword = "secret123"; // Change this to your desired password

    public ExamStopper() {
        setTitle("Stop Exam");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        getContentPane().add(panel);

        stopButton = new JButton("Stop Exam");
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                authenticate();
            }
        });
        panel.add(stopButton);

        setVisible(true);
    }

    private void authenticate() {
        String inputPassword = JOptionPane.showInputDialog("Enter password to stop exam:");
        if (inputPassword != null && inputPassword.equals(correctPassword)) {
            stopExam();
        } else {
            JOptionPane.showMessageDialog(this, "Incorrect password. Please try again.");
        }
    }

    private void stopExam() {
        // Add logic here to stop the exam
        System.out.println("Exam stopped!");
        // For demonstration purposes, let's just close the window
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ExamStopper());
    }
}