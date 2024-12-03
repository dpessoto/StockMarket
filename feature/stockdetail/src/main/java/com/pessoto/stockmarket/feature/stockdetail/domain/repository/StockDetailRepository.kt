package com.pessoto.stockmarket.feature.stockdetail.domain.repository

import com.pessoto.stockmarket.feature.stockdetail.domain.entity.StockDetail
import kotlinx.coroutines.flow.Flow

interface StockDetailRepository {

    fun fetchStockDetail(ticker: String, range: String): Flow<StockDetail>
}
