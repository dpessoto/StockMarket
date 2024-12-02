package com.pessoto.stockmarket.feature.stockdetail.domain.entity

data class HistoricalDataPrice(
    val date: Long,
    val open: Double,
    val close: Double,
    val high: Double,
    val low: Double,
)
