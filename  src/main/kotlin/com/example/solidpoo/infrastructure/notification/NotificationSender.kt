package com.example.solidpoo.infrastructure.notification

/**
 * Interface para envio de notificações.
 *
 * Demonstra o princípio I (Interface Segregation) do SOLID novamente -
 * Interface pequena e coesa, com métodos específicos para notificações
 */
interface NotificationSender {
    fun sendNotification(recipient: String, subject: String, content: String): Boolean
    fun getChannelType(): NotificationChannel
}

enum class NotificationChannel {
    EMAIL, SMS, PUSH
}
