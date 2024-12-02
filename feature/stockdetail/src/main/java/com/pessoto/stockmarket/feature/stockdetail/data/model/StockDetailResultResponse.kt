package com.pessoto.stockmarket.feature.stockdetail.data.model

import com.google.gson.annotations.SerializedName

data class StockDetailResultResponse(
    @SerializedName("results") val results: List<StockDetailResponse>
)
