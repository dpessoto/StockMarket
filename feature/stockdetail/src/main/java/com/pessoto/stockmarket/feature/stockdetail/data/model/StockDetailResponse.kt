package com.pessoto.stockmarket.feature.stockdetail.data.model

import com.google.gson.annotations.SerializedName

data class StockDetailResponse(
    @SerializedName("symbol") val symbol: String,
    @SerializedName("currency") val currency: String,
    @SerializedName("longName") val longName: String,
    @SerializedName("regularMarketTime") val regularMarketTime: String,
    @SerializedName("regularMarketPrice") val regularMarketPrice: Double,
    @SerializedName("logourl") val logourl: String,
    @SerializedName("usedRange") val usedRange: String,
    @SerializedName("historicalDataPrice") val historicalDataPrice: List<HistoricalDataPriceResponse>? = null
)
