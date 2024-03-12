package org.example;
import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Log {

    public static void findRunningApplication(JTextArea logOutputTextArea) {
        try {
            // Execute 'ps' command to find running processes
            Process process = Runtime.getRuntime().exec("ps -e");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            List<String> runningApplications = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                runningApplications.add(line);
            }

            // Update the JTextArea with the list of running applications
            StringBuilder stringBuilder = new StringBuilder();
            for (String app : runningApplications) {
                stringBuilder.append(app).append("\n");
            }
            logOutputTextArea.setText(stringBuilder.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
