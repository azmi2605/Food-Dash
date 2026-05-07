package com.fooddelivery.viewmodel

import androidx.lifecycle.ViewModel
import com.fooddelivery.data.model.CartItem
import com.fooddelivery.data.model.Category
import com.fooddelivery.data.model.FoodItem
import com.fooddelivery.data.repository.MockData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

enum class PaymentMethod {
    ONLINE, OFFLINE
}

class FoodViewModel : ViewModel() {

    // ── User State ───────────────────────────────────────────────────────────────
    private val _userAddress = MutableStateFlow("123 Street, New York")
    val userAddress: StateFlow<String> = _userAddress.asStateFlow()

    private val _paymentMethod = MutableStateFlow(PaymentMethod.ONLINE)
    val paymentMethod: StateFlow<PaymentMethod> = _paymentMethod.asStateFlow()

    fun updateAddress(newAddress: String) {
        _userAddress.update { if (newAddress.isBlank()) "Set your address" else newAddress }
    }

    fun updatePaymentMethod(method: PaymentMethod) {
        _paymentMethod.update { method }
    }

    // ── Home State ──────────────────────────────────────────────────────────────
    val categories: List<Category> = MockData.categories
    val allFoodItems: List<FoodItem> = MockData.foodItems
    val popularItems: List<FoodItem> = MockData.getPopular()

    private val _selectedCategoryId = MutableStateFlow<Int?>(null)
    val selectedCategoryId: StateFlow<Int?> = _selectedCategoryId.asStateFlow()

    val filteredItems: StateFlow<List<FoodItem>> get() = _filteredItems
    private val _filteredItems = MutableStateFlow(MockData.foodItems)

    fun selectCategory(id: Int?) {
        _selectedCategoryId.update { id }
        _filteredItems.update {
            if (id == null) MockData.foodItems else MockData.getByCategory(id)
        }
    }

    // ── Cart State ───────────────────────────────────────────────────────────────
    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

    val cartTotal: StateFlow<Double> get() = _cartTotal
    private val _cartTotal = MutableStateFlow(0.0)

    val cartCount: StateFlow<Int> get() = _cartCount
    private val _cartCount = MutableStateFlow(0)

    fun addToCart(foodItem: FoodItem) {
        _cartItems.update { current ->
            val existing = current.find { it.foodItem.id == foodItem.id }
            if (existing != null) {
                current.map { if (it.foodItem.id == foodItem.id) it.copy(quantity = it.quantity + 1) else it }
            } else {
                current + CartItem(foodItem, 1)
            }
        }
        recalculate()
    }

    fun removeFromCart(foodItem: FoodItem) {
        _cartItems.update { current ->
            val existing = current.find { it.foodItem.id == foodItem.id }
            if (existing != null && existing.quantity > 1) {
                current.map { if (it.foodItem.id == foodItem.id) it.copy(quantity = it.quantity - 1) else it }
            } else {
                current.filter { it.foodItem.id != foodItem.id }
            }
        }
        recalculate()
    }

    fun deleteFromCart(foodItem: FoodItem) {
        _cartItems.update { current -> current.filter { it.foodItem.id != foodItem.id } }
        recalculate()
    }

    fun clearCart() {
        _cartItems.update { emptyList() }
        recalculate()
    }

    private fun recalculate() {
        _cartTotal.update { _cartItems.value.sumOf { it.foodItem.price * it.quantity } }
        _cartCount.update { _cartItems.value.sumOf { it.quantity } }
    }

    fun getItemById(id: Int): FoodItem? = MockData.getById(id)

    fun quantityInCart(foodItem: FoodItem): Int =
        _cartItems.value.find { it.foodItem.id == foodItem.id }?.quantity ?: 0
}
