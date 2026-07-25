package com.example.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@Entity(tableName = "products")
@JsonClass(generateAdapter = true)
data class Product(
    @PrimaryKey val id: String,
    val name: String,
    val brand: String,
    val category: String,
    val price: Double,
    val discountPrice: Double?,
    val rating: Double,
    val reviewCount: Int,
    val description: String,
    val imageUrl: String,
    val images: List<String>,
    val sizes: List<String>,
    val colors: List<String>,
    val stockStatus: Boolean
)
