package com.example.solidpoo.domain.service

import com.example.solidpoo.domain.model.Customer
import com.example.solidpoo.domain.model.Order
import com.example.solidpoo.infrastructure.repository.OrderRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Interface JPA para Customer
 */
@Repository
interface CustomerRepository : JpaRepository<Customer, Long>

/**
 * Serviço para gerenciamento de pedidos.
 *
 * Demonstra o princípio S (Single Responsibility) do SOLID -
 * Lida apenas com a orquestração de pedidos
 *
 * Também demonstra o princípio O (Open/Closed) do SOLID -
 * Podemos estender seu comportamento sem modificar (através de outros serviços)
 */
@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val customerRepository: CustomerRepository,
    private val inventoryService: InventoryService,
    private val paymentService: PaymentService,
    private val notificationService: NotificationService
) {
    /**
     * Demonstra o princípio O (Open/Closed) do SOLID -
     * O fluxo está aberto para extensão, mas fechado para modificação
     */
    @Transactional
    fun createOrder(customerId: Long, productItems: Map<Long, Int>): Order? {
        val customer = customerRepository.findById(customerId).orElse(null)
            ?: return null

        val order = Order(customer = customer)

        // Adiciona produtos ao pedido
        var allItemsAdded = true
        for ((productId, quantity) in productItems) {
            val product = inventoryService.getProductById(productId) ?: continue
            val itemAdded = order.addItem(product, quantity)
            if (!itemAdded) {
                allItemsAdded = false
                break
            }
        }

        if (!allItemsAdded || order.items.isEmpty()) {
            return null
        }

        // Reserva o estoque
        val reserved = inventoryService.reserveInventory(order)
        if (!reserved) {
            return null
        }

        // Salva e notifica
        val savedOrder = orderRepository.save(order)
        notificationService.notifyOrderCreated(savedOrder)

        return savedOrder
    }

    @Transactional
    fun processPayment(orderId: Long, paymentMethod: String): Boolean {
        val order = findOrderById(orderId) ?: return false

        val paymentResult = paymentService.processPayment(order, paymentMethod)
        if (paymentResult.success) {
            orderRepository.save(order)
            notificationService.notifyOrderPaid(order)
            return true
        }

        return false
    }

    @Transactional
    fun shipOrder(orderId: Long): Boolean {
        val order = findOrderById(orderId) ?: return false

        try {
            order.ship()
            orderRepository.save(order)
            notificationService.notifyOrderShipped(order)
            return true
        } catch (e: IllegalArgumentException) {
            return false
        }
    }

    @Transactional
    fun deliverOrder(orderId: Long): Boolean {
        val order = findOrderById(orderId) ?: return false

        try {
            order.deliver()
            orderRepository.save(order)
            return true
        } catch (e: IllegalArgumentException) {
            return false
        }
    }

    @Transactional
    fun cancelOrder(orderId: Long): Boolean {
        val order = findOrderById(orderId) ?: return false

        try {
            order.cancel()
            orderRepository.save(order)

            // Libera o estoque dos produtos
            inventoryService.releaseInventory(order)

            // Se o pedido foi pago, emite reembolso
            if (order.paymentMethod != null) {
                paymentService.refundPayment(order)
            }

            return true
        } catch (e: IllegalArgumentException) {
            return false
        }
    }

    fun findOrderById(orderId: Long): Order? {
        return orderRepository.findById(orderId).orElse(null)
    }

    fun findOrdersByCustomer(customerId: Long): List<Order> {
        return orderRepository.findByCustomerId(customerId)
    }

    fun findAllOrders(): List<Order> {
        return orderRepository.findAll()
    }
}
