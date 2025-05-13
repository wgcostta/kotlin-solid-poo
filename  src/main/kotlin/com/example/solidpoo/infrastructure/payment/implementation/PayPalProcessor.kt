package com.example.solidpoo.infrastructure.payment.implementation

import com.example.solidpoo.domain.model.Order
import com.example.solidpoo.infrastructure.payment.PaymentProcessor
import com.example.solidpoo.infrastructure.payment.PaymentResult
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.util.UUID

/**
 * Implementação do processador para PayPal.
 *
 * Outro exemplo do princípio L (Liskov Substitution) do SOLID
 */
@Component
class PayPalProcessor : PaymentProcessor {

    override fun processPayment(order: Order, amount: BigDecimal): PaymentResult {
        // Simulação de processamento de pagamento com PayPal
        val transactionId = UUID.randomUUID().toString()
        println("Processing PayPal payment for order ${order.id}, amount: $amount")
        return PaymentResult(
            success = true,
            transactionId = transactionId,
            message = "PayPal payment processed successfully"
        )
    }

    override fun refund(orderId: Long, amount: BigDecimal): PaymentResult {
        // Simulação de reembolso com PayPal
        val transactionId = UUID.randomUUID().toString()
        println("Refunding PayPal payment for order $orderId, amount: $amount")
        return PaymentResult(
            success = true,
            transactionId = transactionId,
            message = "PayPal refund processed successfully"
        )
    }

    override fun getPaymentMethod(): String {
        return "PAYPAL"
    }
}
