package com.example.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@Entity(tableName = "addresses")
@JsonClass(generateAdapter = true)
data class Address(
    @PrimaryKey val id: String,
    val name: String,
    val street: String,
    val city: String,
    val state: String,
    val zipCode: String,
    val phone: String,
    val isDefault: Boolean = false
)
