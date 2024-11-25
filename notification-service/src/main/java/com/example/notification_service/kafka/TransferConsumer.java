package com.example.notification_service.kafka;

import com.example.notification_service.model.Notification;
import com.example.notification_service.service.NotificationService;
import com.example.transfer_service.model.Transfer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TransferConsumer {

    private final NotificationService notificationService;

    public TransferConsumer(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @KafkaListener(topics = "transfer-topic", groupId = "notification_group")
    public void consumeTransfer(Transfer transfer) {
        Notification notification = new Notification();
        notification.setUserId(transfer.getToAccountId()); // Alıcı kullanıcı
        notification.setMessage("You've received a transfer of " + transfer.getAmount());
        notificationService.createNotification(notification);
    }
}
