package com.pessoto.stockmarket.feature.stockslist.data.model

import com.google.gson.annotations.SerializedName

internal data class StockResponse(
    @SerializedName("stock") val stock: String,
    @SerializedName("name") val name: String,
    @SerializedName("logo") val logo: String,
)
