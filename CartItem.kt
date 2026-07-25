package com.example.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@Entity(tableName = "cart_items")
@JsonClass(generateAdapter = true)
data class CartItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val product: Product,
    val quantity: Int,
    val selectedSize: String,
    val selectedColor: String
)
