package com.example.solidpoo.domain.service

import com.example.solidpoo.domain.model.Order
import com.example.solidpoo.infrastructure.payment.PaymentProcessor
import com.example.solidpoo.infrastructure.payment.PaymentResult
import org.springframework.stereotype.Service
import java.math.BigDecimal

/**
 * Serviço para processamento de pagamentos.
 *
 * Demonstra o princípio D (Dependency Inversion) do SOLID -
 * A classe depende da abstração PaymentProcessor, não de implementações concretas
 */
@Service
class PaymentService(private val paymentProcessors: List<PaymentProcessor>) {

    fun processPayment(order: Order, paymentMethod: String): PaymentResult {
        val amount = order.calculateTotal()
        val processor = getProcessorByMethod(paymentMethod)

        val result = processor.processPayment(order, amount)

        if (result.success) {
            order.processPayment(paymentMethod)
        }

        return result
    }

    fun refundPayment(order: Order, amount: BigDecimal? = null): PaymentResult {
        val paymentMethod = order.paymentMethod
            ?: throw IllegalStateException("Order has no payment method")

        val refundAmount = amount ?: order.calculateTotal()
        val processor = getProcessorByMethod(paymentMethod)

        return processor.refund(order.id!!, refundAmount)
    }

    private fun getProcessorByMethod(method: String): PaymentProcessor {
        return paymentProcessors.find { it.getPaymentMethod() == method }
            ?: throw IllegalArgumentException("No payment processor found for method $method")
    }
}
