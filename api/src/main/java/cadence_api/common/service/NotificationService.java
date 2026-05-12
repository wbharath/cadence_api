package cadence_api.common.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Async
    public void sendHabitCompletionAlert(String habitName) {
        try {
            System.out.println(
                    "Sending notification for: " + habitName +
                            " on thread: " + Thread.currentThread().getName()
            );

            Thread.sleep(2000); // simulate email delay

            System.out.println(
                    "Notification sent for: " + habitName
            );

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}