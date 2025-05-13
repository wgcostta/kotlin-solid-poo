package com.example.solidpoo.domain.service

import com.example.solidpoo.domain.model.Order
import com.example.solidpoo.domain.model.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Interface JPA para Product
 */
@Repository
interface ProductRepository : JpaRepository<Product, Long>

/**
 * Serviço para gerenciamento de inventário.
 *
 * Demonstra o princípio S (Single Responsibility) do SOLID -
 * Responsável apenas pela lógica relacionada ao inventário
 */
@Service
class InventoryService(private val productRepository: ProductRepository) {

    @Transactional
    fun reserveInventory(order: Order): Boolean {
        // Verifica se todos os produtos têm estoque suficiente
        val canReserve = order.items.all { item ->
            val product = item.product
            product.isAvailable(item.quantity)
        }

        // Se pode reservar, reduz as quantidades no estoque
        if (canReserve) {
            order.items.forEach { item ->
                val product = item.product
                product.decreaseQuantity(item.quantity)
                productRepository.save(product)
            }
        }

        return canReserve
    }

    @Transactional
    fun releaseInventory(order: Order) {
        order.items.forEach { item ->
            val product = item.product
            product.increaseQuantity(item.quantity)
            productRepository.save(product)
        }
    }

    fun getProductById(productId: Long): Product? {
        return productRepository.findById(productId).orElse(null)
    }

    fun getAllProducts(): List<Product> {
        return productRepository.findAll()
    }
}
