package com.pessoto.stockmarket.feature.stockslist.data.repository

import com.pessoto.stockmarket.feature.stockslist.data.mapper.StocksMapper
import com.pessoto.stockmarket.feature.stockslist.data.source.remote.StockListRemoteDataSource
import com.pessoto.stockmarket.feature.stockslist.domain.entity.Stock
import com.pessoto.stockmarket.feature.stockslist.domain.exception.EmptyStockListException
import com.pessoto.stockmarket.feature.stockslist.domain.repository.StockListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class StockListRepositoryImpl(
    private val remoteDataSource: StockListRemoteDataSource,
    private val mapper: StocksMapper
) : StockListRepository {

    override fun fetchStockList(): Flow<List<Stock>> {

        return flow {
            val stocksResponse = remoteDataSource.fetchStockList()
            if (stocksResponse.stocks.isEmpty()) {
                throw EmptyStockListException()
            }
            emit(mapper.map(stocksResponse))
        }
    }
}
