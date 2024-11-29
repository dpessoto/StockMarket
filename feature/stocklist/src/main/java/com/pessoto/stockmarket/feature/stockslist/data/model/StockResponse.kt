package com.pessoto.stockmarket.feature.stockslist.data.model

import com.google.gson.annotations.SerializedName

internal data class StockResponse(
    @SerializedName("stock") val stock: String,
    @SerializedName("name") val name: String,
    @SerializedName("close") val close: Double,
    @SerializedName("change") val change: Double,
    @SerializedName("volume") val volume: Int,
    @SerializedName("market_cap") val marketCap: Double,
    @SerializedName("logo") val logo: String,
    @SerializedName("type") val type: String
)
