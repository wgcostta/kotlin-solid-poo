package com.example.solidpoo.infrastructure.notification.implementation

import com.example.solidpoo.infrastructure.notification.NotificationChannel
import com.example.solidpoo.infrastructure.notification.NotificationSender
import org.springframework.stereotype.Component

/**
 * Implementação do envio de notificações por e-mail.
 */
@Component
class EmailNotificationSender : NotificationSender {
    
    override fun sendNotification(recipient: String, subject: String, content: String): Boolean {
        // Simulação de envio de e-mail
        println("Sending email to $recipient")
        println("Subject: $subject")
        println("Content: $content")
        return true
    }
    
    override fun getChannelType(): NotificationChannel {
        return NotificationChannel.EMAIL
    }
}