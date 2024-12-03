package com.pessoto.stockmarket.feature.stockdetail.data.source.remote

import com.pessoto.stockmarket.feature.stockdetail.data.api.StockDetailApi
import com.pessoto.stockmarket.feature.stockdetail.data.model.StockDetailResultResponse

internal class StockDetailRemoteDataSourceImpl(private val stockDetailApi: StockDetailApi) :
    StockDetailRemoteDataSource {

    override suspend fun fetchStockDetail(ticker: String, range: String): StockDetailResultResponse {

        return stockDetailApi.fetchStockDetail(ticker, range)
    }
}
