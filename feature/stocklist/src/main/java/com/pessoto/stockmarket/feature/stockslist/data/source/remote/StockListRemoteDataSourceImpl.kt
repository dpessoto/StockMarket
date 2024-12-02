package com.pessoto.stockmarket.feature.stockslist.data.source.remote

import com.pessoto.stockmarket.feature.stockslist.data.api.StockListApi
import com.pessoto.stockmarket.feature.stockslist.data.model.StocksResponse

internal class StockListRemoteDataSourceImpl(private val stockListApi: StockListApi) :
    StockListRemoteDataSource {

    override suspend fun fetchStockList(): StocksResponse {
        return stockListApi.fetchStockList()
    }
}
