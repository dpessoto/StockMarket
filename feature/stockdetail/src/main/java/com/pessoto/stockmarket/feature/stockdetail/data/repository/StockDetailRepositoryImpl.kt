package com.pessoto.stockmarket.feature.stockdetail.data.repository

import com.pessoto.stockmarket.feature.stockdetail.data.mapper.StockDetailMapper
import com.pessoto.stockmarket.feature.stockdetail.data.source.remote.StockDetailRemoteDataSource
import com.pessoto.stockmarket.feature.stockdetail.domain.entity.StockDetail
import com.pessoto.stockmarket.feature.stockdetail.domain.exception.StockDetailNotFoundException
import com.pessoto.stockmarket.feature.stockdetail.domain.repository.StockDetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class StockDetailRepositoryImpl(
    private val remoteDataSource: StockDetailRemoteDataSource,
    private val mapper: StockDetailMapper
) : StockDetailRepository {

    override fun fetchStockDetail(ticker: String): Flow<StockDetail> {

        return flow {
            val result = remoteDataSource.fetchStockDetail(ticker)

            if (result.results.isEmpty()) {
                throw StockDetailNotFoundException()
            } else {
                emit(mapper.map(result.results[0]))
            }
        }
    }
}
