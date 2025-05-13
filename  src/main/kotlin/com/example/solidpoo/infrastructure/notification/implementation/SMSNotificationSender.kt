package com.example.solidpoo.infrastructure.notification.implementation

import com.example.solidpoo.infrastructure.notification.NotificationChannel
import com.example.solidpoo.infrastructure.notification.NotificationSender
import org.springframework.stereotype.Component

/**
 * Implementação do envio de notificações por SMS.
 */
@Component
class SMSNotificationSender : NotificationSender {

    override fun sendNotification(recipient: String, subject: String, content: String): Boolean {
        // Simulação de envio de SMS
        println("Sending SMS to $recipient")
        println("Content: $content")
        return true
    }

    override fun getChannelType(): NotificationChannel {
        return NotificationChannel.SMS
    }
}
