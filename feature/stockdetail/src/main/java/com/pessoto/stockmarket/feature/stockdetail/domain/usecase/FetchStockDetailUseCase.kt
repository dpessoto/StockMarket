package com.pessoto.stockmarket.feature.stockdetail.domain.usecase

import com.pessoto.stockmarket.feature.stockdetail.domain.entity.StockDetail
import com.pessoto.stockmarket.feature.stockdetail.domain.repository.StockDetailRepository
import kotlinx.coroutines.flow.Flow

class FetchStockDetailUseCase(private val repository: StockDetailRepository) {

    operator fun invoke(ticker: String): Flow<StockDetail> {
        return repository.fetchStockDetail(ticker)
    }
}
