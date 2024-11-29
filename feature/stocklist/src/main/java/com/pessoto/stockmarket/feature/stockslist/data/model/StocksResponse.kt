package com.pessoto.stockmarket.feature.stockslist.data.model

import com.google.gson.annotations.SerializedName

internal data class StocksResponse(
    @SerializedName("stocks") val stocks: List<StockResponse>
)
