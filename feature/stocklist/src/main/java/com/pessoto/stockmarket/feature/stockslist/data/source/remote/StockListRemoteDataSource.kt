package com.pessoto.stockmarket.feature.stockslist.data.source.remote

import com.pessoto.stockmarket.feature.stockslist.data.model.StocksResponse

internal interface StockListRemoteDataSource {

    suspend fun fetchStockList(): StocksResponse
}
