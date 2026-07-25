package com.example.data

import androidx.room.TypeConverter
import com.example.model.CartItem
import com.example.model.Product
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class Converters {
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @TypeConverter
    fun fromStringList(value: List<String>?): String? {
        if (value == null) return null
        val type = Types.newParameterizedType(List::class.java, String::class.java)
        val adapter = moshi.adapter<List<String>>(type)
        return adapter.toJson(value)
    }

    @TypeConverter
    fun toStringList(value: String?): List<String>? {
        if (value == null) return null
        val type = Types.newParameterizedType(List::class.java, String::class.java)
        val adapter = moshi.adapter<List<String>>(type)
        return adapter.fromJson(value)
    }

    @TypeConverter
    fun fromCartItemList(value: List<CartItem>?): String? {
        if (value == null) return null
        val type = Types.newParameterizedType(List::class.java, CartItem::class.java)
        val adapter = moshi.adapter<List<CartItem>>(type)
        return adapter.toJson(value)
    }

    @TypeConverter
    fun toCartItemList(value: String?): List<CartItem>? {
        if (value == null) return null
        val type = Types.newParameterizedType(List::class.java, CartItem::class.java)
        val adapter = moshi.adapter<List<CartItem>>(type)
        return adapter.fromJson(value)
    }

    @TypeConverter
    fun fromProduct(value: Product?): String? {
        if (value == null) return null
        val adapter = moshi.adapter(Product::class.java)
        return adapter.toJson(value)
    }

    @TypeConverter
    fun toProduct(value: String?): Product? {
        if (value == null) return null
        val adapter = moshi.adapter(Product::class.java)
        return adapter.fromJson(value)
    }

    @TypeConverter
    fun fromAddress(value: com.example.model.Address?): String? {
        if (value == null) return null
        val adapter = moshi.adapter(com.example.model.Address::class.java)
        return adapter.toJson(value)
    }

    @TypeConverter
    fun toAddress(value: String?): com.example.model.Address? {
        if (value == null) return null
        val adapter = moshi.adapter(com.example.model.Address::class.java)
        return adapter.fromJson(value)
    }
}
