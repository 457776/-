package com.example.data

import com.example.model.Product

object MockData {
    val categories = listOf("All", "Men's Shoes", "Women's Shoes", "Running", "Sneakers", "Boots")
    val brands = listOf("Nike", "Adidas", "Puma", "Reebok", "New Balance")
    
    val products = listOf(
        Product(
            id = "1",
            name = "Air Max 270 React",
            brand = "Nike",
            category = "Sneakers",
            price = 299.99,
            discountPrice = 150.00,
            rating = 4.8,
            reviewCount = 124,
            description = "The Nike Air Max 270 React merges the best of both worlds. The React foam midsole is ultra lightweight, while the 270 Max Air unit provides unparalleled comfort.",
            imageUrl = "https://images.unsplash.com/photo-1542291026-7eec264c27ff?q=80&w=1000&auto=format&fit=crop",
            images = listOf(
                "https://images.unsplash.com/photo-1542291026-7eec264c27ff?q=80&w=1000&auto=format&fit=crop",
                "https://images.unsplash.com/photo-1608231387042-66d1773070a5?q=80&w=1000&auto=format&fit=crop"
            ),
            sizes = listOf("US 7", "US 8", "US 9", "US 10", "US 11"),
            colors = listOf("Red", "Black", "White"),
            stockStatus = true
        ),
        Product(
            id = "2",
            name = "Ultraboost 22",
            brand = "Adidas",
            category = "Running",
            price = 190.00,
            discountPrice = null,
            rating = 4.9,
            reviewCount = 312,
            description = "Say hello to supreme energy return. We rethought our Ultraboost running shoes to give you a more responsive ride.",
            imageUrl = "https://images.unsplash.com/photo-1587563871167-1ee9c731aefb?q=80&w=1000&auto=format&fit=crop",
            images = listOf(
                "https://images.unsplash.com/photo-1587563871167-1ee9c731aefb?q=80&w=1000&auto=format&fit=crop"
            ),
            sizes = listOf("US 8", "US 9", "US 10"),
            colors = listOf("White", "Grey"),
            stockStatus = true
        ),
        Product(
            id = "3",
            name = "Puma RS-X3",
            brand = "Puma",
            category = "Sneakers",
            price = 110.00,
            discountPrice = 85.00,
            rating = 4.5,
            reviewCount = 89,
            description = "X marks extreme. Exaggerated. Remixed. The new RS-X3 takes the signature RS design and dials it up to the third power.",
            imageUrl = "https://images.unsplash.com/photo-1606107557195-0e29a4b5b4aa?q=80&w=1000&auto=format&fit=crop",
            images = listOf(
                "https://images.unsplash.com/photo-1606107557195-0e29a4b5b4aa?q=80&w=1000&auto=format&fit=crop"
            ),
            sizes = listOf("US 6", "US 7", "US 8", "US 9"),
            colors = listOf("Green", "Black"),
            stockStatus = true
        ),
        Product(
            id = "4",
            name = "Air Force 1 '07",
            brand = "Nike",
            category = "Casual",
            price = 115.00,
            discountPrice = null,
            rating = 4.7,
            reviewCount = 520,
            description = "The radiance lives on in the Nike Air Force 1 '07, the b-ball icon that puts a fresh spin on what you know best.",
            imageUrl = "https://images.unsplash.com/photo-1595950653106-6c9ebd614d3a?q=80&w=1000&auto=format&fit=crop",
            images = listOf(
                "https://images.unsplash.com/photo-1595950653106-6c9ebd614d3a?q=80&w=1000&auto=format&fit=crop"
            ),
            sizes = listOf("US 7", "US 8", "US 9", "US 10"),
            colors = listOf("White", "White/Black"),
            stockStatus = true
        ),
        Product(
            id = "5",
            name = "Classic Leather",
            brand = "Reebok",
            category = "Sneakers",
            price = 85.00,
            discountPrice = 65.00,
            rating = 4.6,
            reviewCount = 205,
            description = "Always classic. These shoes feature a soft garment leather upper for superior comfort.",
            imageUrl = "https://images.unsplash.com/photo-1525966222134-fcfa99b8ae77?q=80&w=1000&auto=format&fit=crop",
            images = listOf(
                "https://images.unsplash.com/photo-1525966222134-fcfa99b8ae77?q=80&w=1000&auto=format&fit=crop"
            ),
            sizes = listOf("US 8", "US 9", "US 10"),
            colors = listOf("Vans Check", "White"),
            stockStatus = true
        ),
        Product(
            id = "6",
            name = "New Balance 574 Core",
            brand = "New Balance",
            category = "Casual",
            price = 89.99,
            discountPrice = null,
            rating = 4.8,
            reviewCount = 410,
            description = "The most New Balance shoe ever says it all, right? No, actually. The 574 might be our unlikeliest icon.",
            imageUrl = "https://images.unsplash.com/photo-1532298229144-0ec0c57515c7?q=80&w=1000&auto=format&fit=crop",
            images = listOf(
                "https://images.unsplash.com/photo-1532298229144-0ec0c57515c7?q=80&w=1000&auto=format&fit=crop"
            ),
            sizes = listOf("US 7", "US 8", "US 9", "US 10", "US 11"),
            colors = listOf("Grey", "Navy"),
            stockStatus = true
        )
    )
}
