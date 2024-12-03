package com.pessoto.stockmarket.feature.stockdetail.data.source.remote

import com.pessoto.stockmarket.feature.stockdetail.data.model.StockDetailResultResponse

internal interface StockDetailRemoteDataSource {

    suspend fun fetchStockDetail(ticker: String, range: String): StockDetailResultResponse
}
