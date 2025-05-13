package com.example.solidpoo.infrastructure.payment.implementation

import com.example.solidpoo.domain.model.Order
import com.example.solidpoo.infrastructure.payment.PaymentProcessor
import com.example.solidpoo.infrastructure.payment.PaymentResult
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.util.UUID

/**
 * Implementação do processador para cartão de crédito.
 *
 * Demonstra o princípio L (Liskov Substitution) do SOLID -
 * Pode substituir a interface PaymentProcessor sem alterar o comportamento do programa
 */
@Component
class CreditCardProcessor : PaymentProcessor {

    override fun processPayment(order: Order, amount: BigDecimal): PaymentResult {
        // Simulação de processamento de pagamento com cartão de crédito
        val transactionId = UUID.randomUUID().toString()
        println("Processing credit card payment for order ${order.id}, amount: $amount")
        return PaymentResult(
            success = true,
            transactionId = transactionId,
            message = "Credit card payment processed successfully"
        )
    }

    override fun refund(orderId: Long, amount: BigDecimal): PaymentResult {
        // Simulação de reembolso com cartão de crédito
        val transactionId = UUID.randomUUID().toString()
        println("Refunding credit card payment for order $orderId, amount: $amount")
        return PaymentResult(
            success = true,
            transactionId = transactionId,
            message = "Credit card refund processed successfully"
        )
    }

    override fun getPaymentMethod(): String {
        return "CREDIT_CARD"
    }
}