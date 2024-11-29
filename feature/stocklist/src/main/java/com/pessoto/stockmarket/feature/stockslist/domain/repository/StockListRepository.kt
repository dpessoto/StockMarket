package com.pessoto.stockmarket.feature.stockslist.domain.repository

import com.pessoto.stockmarket.feature.stockslist.domain.entity.Stock
import kotlinx.coroutines.flow.Flow

interface StockListRepository {

    fun fetchStockList(): Flow<List<Stock>>
}
