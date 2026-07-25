package com.example.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@Entity(tableName = "orders")
@JsonClass(generateAdapter = true)
data class Order(
    @PrimaryKey val id: String,
    val date: String,
    val totalAmount: Double,
    val status: String,
    val items: List<CartItem>,
    val address: Address? = null
)
