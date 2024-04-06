package org.example;

import java.util.ArrayList;
import java.util.List;

public class ApplicationLogger {
    private final List<String> activityHistory;
    private final List<String> webBrowserActivities;
    private int webBrowserUsageTime; // Time spent using web browser in seconds
    private int totalUsageTime; // Total time spent in seconds

    public ApplicationLogger() {
        activityHistory = new ArrayList<>();
        webBrowserActivities = new ArrayList<>();
        webBrowserUsageTime = 0;
        totalUsageTime = 0;
    }

    public void logActivity(String activity) {
        activityHistory.add(activity);
        totalUsageTime++; // Increment total usage time for every activity
        if (isWebBrowserActivity(activity)) {
            webBrowserActivities.add(activity);
            webBrowserUsageTime++; // Increment web browser usage time
        }
    }

    private boolean isWebBrowserActivity(String activity) {
        // Implement logic to determine if the activity is related to web browsing
        // For example, you can check if the activity contains keywords like "browser", "internet", etc.
        return activity.toLowerCase().contains("browser") || activity.toLowerCase().contains("internet");
    }

    public String getActivityHistory() {
        StringBuilder history = new StringBuilder();
        for (String activity : activityHistory) {
            history.append(activity).append("\n");
        }
        return history.toString();
    }

    public double getMalpracticeLevel() {
        if (totalUsageTime == 0) {
            return 0; // Avoid division by zero
        }
        return ((double) webBrowserUsageTime / totalUsageTime) * 100;
    }

    public List<String> getWebBrowserActivities() {
        return webBrowserActivities;
    }
}
