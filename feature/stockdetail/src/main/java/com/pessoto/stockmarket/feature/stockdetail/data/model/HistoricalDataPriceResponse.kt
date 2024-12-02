package com.pessoto.stockmarket.feature.stockdetail.data.model

import com.google.gson.annotations.SerializedName

data class HistoricalDataPriceResponse(
    @SerializedName("date") val date: Long,
    @SerializedName("open") val open: Double,
    @SerializedName("high") val high: Double,
    @SerializedName("low") val low: Double,
    @SerializedName("close") val close: Double,
)
