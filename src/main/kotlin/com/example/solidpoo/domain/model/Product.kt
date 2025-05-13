package com.example.solidpoo.domain.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.math.BigDecimal

/**
 * Produto representa um item que pode ser comprado.
 *
 * Demonstra encapsulamento (um dos pilares da POO) através de propriedades privadas
 * com getters/setters para acesso controlado
 */
@Entity
data class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    private var _name: String,
    private var _price: BigDecimal,
    private var _quantity: Int,
    private var _active: Boolean = true
) {
    // Getters públicos
    val name: String
        get() = _name

    val price: BigDecimal
        get() = _price

    val quantity: Int
        get() = _quantity

    val isActive: Boolean
        get() = _active

    // Métodos para modificar o estado do produto
    fun updatePrice(newPrice: BigDecimal) {
        require(newPrice > BigDecimal.ZERO) { "Price must be greater than zero" }
        this._price = newPrice
    }

    fun increaseQuantity(amount: Int) {
        require(amount > 0) { "Amount must be positive" }
        this._quantity += amount
    }

    fun decreaseQuantity(amount: Int): Boolean {
        require(amount > 0) { "Amount must be positive" }
        if (amount > _quantity) return false

        this._quantity -= amount
        return true
    }

    fun deactivate() {
        this._active = false
    }

    fun activate() {
        this._active = true
    }

    fun isAvailable(requestedQuantity: Int): Boolean {
        return _active && _quantity >= requestedQuantity
    }
}