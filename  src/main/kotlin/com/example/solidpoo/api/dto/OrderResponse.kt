package com.example.solidpoo.api.dto

import com.example.solidpoo.domain.model.Order
import com.example.solidpoo.domain.model.OrderStatus
import java.math.BigDecimal
import java.time.LocalDateTime

/**
 * DTO para retornar informações sobre pedidos nas respostas da API.
 */
data class OrderResponse(
    val id: Long,
    val customerId: Long,
    val customerName: String,
    val items: List<OrderItemResponse>,
    val total: BigDecimal,
    val status: OrderStatus,
    val paymentMethod: String?,
    val createdAt: LocalDateTime
) {
    companion object {
        fun fromOrder(order: Order): OrderResponse {
            return OrderResponse(
                id = order.id!!,
                customerId = order.customer.id!!,
                customerName = order.customer.name,
                items = order.items.map { OrderItemResponse.fromOrderItem(it) },
                total = order.calculateTotal(),
                status = order.status,
                paymentMethod = order.paymentMethod,
                createdAt = order.createdAt
            )
        }
    }
}
