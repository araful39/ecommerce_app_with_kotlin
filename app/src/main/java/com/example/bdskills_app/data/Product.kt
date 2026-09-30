package com.example.bdskills_app.data

data class Product(
    val id: Int,
    val name: String,
    val price: Int,
    val description: String
)

val sampleProducts = listOf(
    Product(1, "Smartphone", 25000, "High performance smartphone with 128GB storage and triple camera."),
    Product(2, "Wireless Headphones", 3500, "Noise-cancelling over-ear Bluetooth headphones with deep bass."),
    Product(3, "Smart Watch", 4500, "Fitness tracker watch with heart rate monitor and AMOLED display."),
    Product(4, "Laptop Backpack", 1800, "Water-resistant durable backpack designed for 15-inch laptops."),
    Product(5, "Running Shoes", 3200, "Comfortable lightweight athletic running shoes for daily use."),
    Product(6, "Bluetooth Speaker", 2200, "Portable wireless speaker with 12-hour battery life.")
)
