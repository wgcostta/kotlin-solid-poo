package com.example.solidpoo.domain.service

import com.example.solidpoo.domain.model.Order
import com.example.solidpoo.infrastructure.notification.NotificationChannel
import com.example.solidpoo.infrastructure.notification.NotificationSender
import org.springframework.stereotype.Service

/**
 * Serviço para envio de notificações.
 *
 * Demonstra o princípio D (Dependency Inversion) do SOLID novamente:
 * A classe depende da abstração NotificationSender, não de implementações concretas
 */
@Service
class NotificationService(private val notificationSenders: List<NotificationSender>) {

    fun notifyOrderCreated(order: Order) {
        val customer = order.customer
        val subject = "Seu pedido #${order.id} foi criado"
        val content = """
            Olá ${customer.name},
            
            Seu pedido #${order.id} foi criado com sucesso.
            Total: ${order.calculateTotal()}
            
            Obrigado por comprar conosco!
        """.trimIndent()

        sendNotification(customer.email, NotificationChannel.EMAIL, subject, content)
    }

    fun notifyOrderPaid(order: Order) {
        val customer = order.customer
        val subject = "Seu pedido #${order.id} foi pago"
        val content = """
            Olá ${customer.name},
            
            O pagamento do seu pedido #${order.id} foi confirmado.
            Método de pagamento: ${order.paymentMethod}
            
            Seu pedido será enviado em breve.
            
            Obrigado por comprar conosco!
        """.trimIndent()

        sendNotification(customer.email, NotificationChannel.EMAIL, subject, content)
    }

    fun notifyOrderShipped(order: Order) {
        val customer = order.customer
        val subject = "Seu pedido #${order.id} foi enviado"
        val content = """
            Olá ${customer.name},
            
            Seu pedido #${order.id} foi enviado e está a caminho.
            
            Obrigado por comprar conosco!
        """.trimIndent()

        // Enviar email e SMS para notificação de envio
        sendNotification(customer.email, NotificationChannel.EMAIL, subject, content)
        if (customer.phone != null) {
            sendNotification(customer.phone, NotificationChannel.SMS, subject, "Seu pedido #${order.id} foi enviado e está a caminho!")
        }
    }

    private fun sendNotification(recipient: String, channel: NotificationChannel, subject: String, content: String): Boolean {
        val sender = notificationSenders.find { it.getChannelType() == channel }
            ?: throw IllegalArgumentException("No notification sender found for channel $channel")

        return sender.sendNotification(recipient, subject, content)
    }
}
