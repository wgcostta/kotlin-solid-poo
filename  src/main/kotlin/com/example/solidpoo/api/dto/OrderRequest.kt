package com.example.solidpoo.api.dto

/**
 * DTO para receber requisições de criação de pedidos.
 *
 * Demonstra o uso de DTOs para transferência de dados entre camadas
 * (outro conceito importante na programação orientada a objetos)
 */
data class OrderRequest(
    val customerId: Long,
    val items: List<OrderItemRequest>
)

data class OrderItemRequest(
    val productId: Long,
    val quantity: Int
)

data class ProcessPaymentRequest(
    val paymentMethod: String
)