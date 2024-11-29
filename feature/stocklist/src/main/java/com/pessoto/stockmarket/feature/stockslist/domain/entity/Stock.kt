package com.pessoto.stockmarket.feature.stockslist.domain.entity

data class Stock(
    val stock: String,
    val name: String,
    val close: Double,
    val change: Double,
    val volume: Int,
    val marketCap: Double,
    val logo: String,
    val type: String
)
