package com.pessoto.stockmarket.feature.stockdetail.domain.entity

data class StockDetail(
    val symbol: String,
    val currency: String,
    val longName: String,
    val regularMarketTime: String,
    val regularMarketPrice: Double,
    val logourl: String,
    val historicalDataPrice: List<HistoricalDataPrice>?
)
