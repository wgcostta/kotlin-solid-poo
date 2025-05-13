package com.example.solidpoo.infrastructure.payment

import com.example.solidpoo.domain.model.Order
import java.math.BigDecimal

/**
 * Interface para processador de pagamentos.
 *
 * Demonstra o princípio I (Interface Segregation) do SOLID -
 * Interface pequena e coesa, com métodos específicos para pagamento
 */
interface PaymentProcessor {
    fun processPayment(order: Order, amount: BigDecimal): PaymentResult
    fun refund(orderId: Long, amount: BigDecimal): PaymentResult

    // Cada implementação de processador de pagamento tem sua própria lógica
    fun getPaymentMethod(): String
}

data class PaymentResult(
    val success: Boolean,
    val transactionId: String?,
    val message: String?
)