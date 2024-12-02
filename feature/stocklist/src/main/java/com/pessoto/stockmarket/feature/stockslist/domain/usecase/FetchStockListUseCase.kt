package com.pessoto.stockmarket.feature.stockslist.domain.usecase

import com.pessoto.stockmarket.feature.stockslist.domain.entity.Stock
import com.pessoto.stockmarket.feature.stockslist.domain.repository.StockListRepository
import kotlinx.coroutines.flow.Flow

class FetchStockListUseCase(private val repository: StockListRepository) {

    operator fun invoke(): Flow<List<Stock>> {
        return repository.fetchStockList()
    }
}
