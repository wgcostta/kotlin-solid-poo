package com.example.solidpoo.infrastructure.repository

import com.example.solidpoo.domain.model.Order
import java.util.Optional

/**
 * Interface para o repositório de pedidos.
 *
 * Demonstra o princípio S (Single Responsibility) do SOLID -
 * Responsável apenas pela persistência de Order
 *
 * Também demonstra o princípio D (Dependency Inversion) -
 * Classes de alto nível dependem de abstrações
 */
interface OrderRepository {
    fun save(order: Order): Order
    fun findById(id: Long): Optional<Order>
    fun findAll(): List<Order>
    fun findByCustomerId(customerId: Long): List<Order>
    fun delete(order: Order)
}
