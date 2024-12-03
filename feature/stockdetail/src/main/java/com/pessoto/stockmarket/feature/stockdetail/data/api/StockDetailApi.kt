package com.pessoto.stockmarket.feature.stockdetail.data.api

import com.pessoto.stockmarket.feature.stockdetail.data.model.StockDetailResultResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface StockDetailApi {

    @GET("api/quote/{tickers}")
    suspend fun fetchStockDetail(
        @Path("tickers") tickers: String,
        @Query("range") range: String = "3mo",
        @Query("interval") interval: String = "1d",
    ): StockDetailResultResponse
}
