package com.example.solidpoo.infrastructure.repository.implementation

import com.example.solidpoo.domain.model.Order
import com.example.solidpoo.infrastructure.repository.OrderRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

/**
 * JpaRepository para o entidade Order - framework Spring Data
 */
@Repository
interface JpaOrderRepository : JpaRepository<Order, Long> {
    fun findByCustomerId(customerId: Long): List<Order>
}

/**
 * Implementação concreta do OrderRepository usando o Spring Data JPA.
 *
 * Demonstra o princípio O (Open/Closed) do SOLID -
 * A classe OrderRepository está aberta para extensão, fechada para modificação
 */
@Repository
class OrderRepositoryImpl(private val jpaRepository: JpaOrderRepository) : OrderRepository {

    override fun save(order: Order): Order {
        return jpaRepository.save(order)
    }

    override fun findById(id: Long): Optional<Order> {
        return jpaRepository.findById(id)
    }

    override fun findAll(): List<Order> {
        return jpaRepository.findAll()
    }

    override fun findByCustomerId(customerId: Long): List<Order> {
        return jpaRepository.findByCustomerId(customerId)
    }

    override fun delete(order: Order) {
        jpaRepository.delete(order)
    }
}
