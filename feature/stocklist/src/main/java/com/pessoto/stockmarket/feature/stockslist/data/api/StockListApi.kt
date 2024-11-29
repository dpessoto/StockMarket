package com.pessoto.stockmarket.feature.stockslist.data.api

import com.pessoto.stockmarket.feature.stockslist.data.model.StocksResponse
import retrofit2.http.GET
import retrofit2.http.Query

internal interface StockListApi {

    @GET("api/quote/list")
   suspend fun fetchStockList(
        @Query("limit") limit: String = "10",
        @Query("type") type: String = "stock",
    ): StocksResponse
}
