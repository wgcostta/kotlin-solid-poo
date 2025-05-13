package com.example.solidpoo.domain.model

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

/**
 * Pedido contendo produtos encomendados por um cliente.
 *
 * Demonstra associação entre classes (outro conceito POO)
 */
@Entity
@Table(name = "orders")
data class Order(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne
    val customer: Customer,

    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true)
    val items: MutableList<OrderItem> = mutableListOf(),

    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Enumerated(EnumType.STRING)
    var status: OrderStatus = OrderStatus.CREATED,

    var paymentMethod: String? = null
) {
    fun addItem(product: Product, quantity: Int): Boolean {
        if (!product.isAvailable(quantity)) return false

        items.add(OrderItem(product = product, quantity = quantity, price = product.price))
        return true
    }

    fun removeItem(item: OrderItem) {
        items.remove(item)
    }

    fun calculateTotal(): BigDecimal {
        return items.fold(BigDecimal.ZERO) { acc, item ->
            acc + (item.price * BigDecimal(item.quantity))
        }
    }

    fun processPayment(method: String) {
        paymentMethod = method
        status = OrderStatus.PAID
    }

    fun ship() {
        require(status == OrderStatus.PAID) { "Order must be paid before shipping" }
        status = OrderStatus.SHIPPED
    }

    fun deliver() {
        require(status == OrderStatus.SHIPPED) { "Order must be shipped before delivery" }
        status = OrderStatus.DELIVERED
    }

    fun cancel() {
        require(status != OrderStatus.SHIPPED && status != OrderStatus.DELIVERED) {
            "Cannot cancel shipped or delivered orders"
        }
        status = OrderStatus.CANCELLED
    }
}

@Entity
data class OrderItem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne
    val product: Product,

    val quantity: Int,

    val price: BigDecimal
)

enum class OrderStatus {
    CREATED, PAID, SHIPPED, DELIVERED, CANCELLED
}
