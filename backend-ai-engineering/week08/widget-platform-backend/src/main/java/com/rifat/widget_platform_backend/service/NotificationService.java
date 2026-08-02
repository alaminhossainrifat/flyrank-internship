package com.rifat.widget_platform_backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    public void sendEmailNotification(String widgetName) {
        try {
            logger.info("Attempting to send notification email for widget: {}", widgetName);

            // Simulate a random email server failure (50% chance to fail)
            if (Math.random() > 0.5) {
                throw new RuntimeException("Email server connection timed out or blocked!");
            }

            logger.info("Successfully sent email notification to the widget owner.");

        } catch (Exception e) {
            // Exception is caught but not re-thrown.
            // This ensures the main thread doesn't crash and the user still receives a '200 OK' response.
            logger.error("Safe Side Effect Triggered: Failed to send email - {}. But submission will be saved safely!", e.getMessage());
        }
    }
}
