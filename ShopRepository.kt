package com.example.data

import android.util.Log
import com.example.model.Address
import com.example.model.CartItem
import com.example.model.Order
import com.example.model.Product
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow

class ShopRepository(private val shopDao: ShopDao) {
    private val firestore: FirebaseFirestore? = try {
        FirebaseFirestore.getInstance()
    } catch (e: Exception) {
        Log.e("ShopRepository", "Firebase not initialized", e)
        null
    }

    val products: Flow<List<Product>> = shopDao.getAllProducts()
    val orders: Flow<List<Order>> = shopDao.getAllOrders()
    val cartItems: Flow<List<CartItem>> = shopDao.getCartItems()
    val addresses: Flow<List<Address>> = shopDao.getAddresses()
    val wishlist: Flow<List<String>> = shopDao.getWishlistProductIds()

    suspend fun insertProduct(product: Product) {
        shopDao.insertProduct(product)
        firestore?.collection("products")?.document(product.id)?.set(product)
            ?.addOnFailureListener { e -> Log.e("ShopRepository", "Error saving product", e) }
    }

    suspend fun updateProduct(product: Product) {
        shopDao.updateProduct(product)
        firestore?.collection("products")?.document(product.id)?.set(product)
    }

    suspend fun deleteProduct(id: String) {
        shopDao.deleteProductById(id)
        firestore?.collection("products")?.document(id)?.delete()
    }

    suspend fun getProductById(id: String) = shopDao.getProductById(id)

    suspend fun insertOrder(order: Order) {
        shopDao.insertOrder(order)
        firestore?.collection("orders")?.document(order.id)?.set(order)
    }

    suspend fun updateOrderStatus(id: String, status: String) {
        shopDao.updateOrderStatus(id, status)
        firestore?.collection("orders")?.document(id)?.update("status", status)
    }

    suspend fun insertCartItem(cartItem: CartItem) = shopDao.insertCartItem(cartItem)
    suspend fun deleteCartItem(id: Int) = shopDao.deleteCartItem(id)
    suspend fun clearCart() = shopDao.clearCart()

    suspend fun insertAddress(address: Address) {
        if (address.isDefault) {
            shopDao.clearDefaultAddress()
        }
        shopDao.insertAddress(address)
        firestore?.collection("addresses")?.document(address.id)?.set(address)
    }

    suspend fun deleteAddress(id: String) {
        shopDao.deleteAddress(id)
        firestore?.collection("addresses")?.document(id)?.delete()
    }

    suspend fun insertWishlistItem(item: com.example.model.WishlistItem) = shopDao.insertWishlistItem(item)
    suspend fun deleteWishlistItem(productId: String) = shopDao.deleteWishlistItem(productId)
}
