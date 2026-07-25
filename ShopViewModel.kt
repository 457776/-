package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.AppDatabase
import com.example.data.MockData
import com.example.data.ShopRepository
import com.example.model.Address
import com.example.model.CartItem
import com.example.model.Order
import com.example.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ShopViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = ShopRepository(AppDatabase.getDatabase(application).shopDao())

    val products: StateFlow<List<Product>> = repository.products
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val cartItems: StateFlow<List<CartItem>> = repository.cartItems
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val addresses: StateFlow<List<Address>> = repository.addresses
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val wishlist: StateFlow<Set<String>> = repository.wishlist
        .map { it.toSet() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptySet())

    val orders: StateFlow<List<Order>> = repository.orders
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        viewModelScope.launch {
            if (repository.products.first().isEmpty()) {
                MockData.products.forEach { repository.insertProduct(it) }
            }
            if (repository.addresses.first().isEmpty()) {
                repository.insertAddress(
                    Address(
                        id = "ADDR-1",
                        name = "John Doe",
                        street = "123 Main Street, Apt 4B",
                        city = "Kolkata",
                        state = "West Bengal",
                        zipCode = "700001",
                        phone = "+91 9876543210",
                        isDefault = true
                    )
                )
            }
            if (repository.orders.first().isEmpty()) {
                repository.insertOrder(
                    Order(
                        id = "ORD-98231",
                        date = "2026-07-10",
                        totalAmount = MockData.products[0].price,
                        status = "Delivered",
                        items = listOf(CartItem(0, MockData.products[0], 1, "M", "Black"))
                    )
                )
            }
        }
    }

    fun updateOrderStatus(orderId: String, newStatus: String) {
        viewModelScope.launch {
            repository.updateOrderStatus(orderId, newStatus)
        }
    }

    fun placeOrder(items: List<CartItem>, address: Address?, totalAmount: Double) {
        viewModelScope.launch {
            val order = Order(
                id = "ORD-${java.util.UUID.randomUUID().toString().take(6).uppercase()}",
                date = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault()).format(java.util.Date()),
                totalAmount = totalAmount,
                status = "Processing",
                items = items,
                address = address
            )
            repository.insertOrder(order)
        }
    }

    fun addAddress(address: Address) {
        viewModelScope.launch {
            repository.insertAddress(address)
        }
    }

    fun addToCart(product: Product, size: String, color: String) {
        viewModelScope.launch {
            val currentCart = cartItems.value
            val existingItem = currentCart.find { it.product.id == product.id && it.selectedSize == size && it.selectedColor == color }
            if (existingItem != null) {
                repository.insertCartItem(existingItem.copy(quantity = existingItem.quantity + 1))
            } else {
                repository.insertCartItem(CartItem(0, product, 1, size, color))
            }
        }
    }

    fun removeFromCart(item: CartItem) {
        viewModelScope.launch {
            repository.deleteCartItem(item.id)
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            repository.clearCart()
        }
    }

    fun updateQuantity(item: CartItem, newQuantity: Int) {
        viewModelScope.launch {
            if (newQuantity <= 0) {
                repository.deleteCartItem(item.id)
            } else {
                repository.insertCartItem(item.copy(quantity = newQuantity))
            }
        }
    }

    fun toggleWishlist(productId: String) {
        viewModelScope.launch {
            val currentWishlist = wishlist.value
            if (currentWishlist.contains(productId)) {
                repository.deleteWishlistItem(productId)
            } else {
                repository.insertWishlistItem(com.example.model.WishlistItem(productId))
            }
        }
    }

    private val _isDarkMode = MutableStateFlow(false)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode.asStateFlow()

    fun toggleDarkMode(isDark: Boolean) {
        _isDarkMode.value = isDark
    }

    fun addProduct(product: Product) {
        viewModelScope.launch {
            repository.insertProduct(product)
        }
    }

    fun updateProduct(updatedProduct: Product) {
        viewModelScope.launch {
            repository.updateProduct(updatedProduct)
        }
    }

    fun deleteProduct(productId: String) {
        viewModelScope.launch {
            repository.deleteProduct(productId)
        }
    }

    fun getProductById(id: String): Product? {
        return products.value.find { it.id == id }
    }
}
