package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class USBEnable {
    public void enable() {
        try {
            // Command to be executed
            //String command = "sudo rmmod usb_storage";
            String command = "pkexec /bin/bash -c 'modprobe usb_storage'";

            // Create ProcessBuilder object with command as argument
            ProcessBuilder pb = new ProcessBuilder("bash", "-c", command);

            // Start the process
            Process process = pb.start();

            // Read output of the process
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // Wait for the process to finish
            process.waitFor();

            // Close the reader
            reader.close();

            // Get the exit status of the process
            int exitStatus = process.exitValue();
            if (exitStatus == 0) {
                System.out.println("Command executed successfully.");
            } else {
                System.out.println("Command failed with exit code: " + exitStatus);
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
