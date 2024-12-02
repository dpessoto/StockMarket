package com.pessoto.stockmarket.feature.stockdetail.di

import com.pessoto.stockmarket.feature.stockdetail.data.api.StockDetailApi
import com.pessoto.stockmarket.feature.stockdetail.data.mapper.StockDetailMapper
import com.pessoto.stockmarket.feature.stockdetail.data.repository.StockDetailRepositoryImpl
import com.pessoto.stockmarket.feature.stockdetail.data.source.remote.StockDetailRemoteDataSource
import com.pessoto.stockmarket.feature.stockdetail.data.source.remote.StockDetailRemoteDataSourceImpl
import com.pessoto.stockmarket.feature.stockdetail.domain.repository.StockDetailRepository
import com.pessoto.stockmarket.feature.stockdetail.domain.usecase.FetchStockDetailUseCase
import com.pessoto.stockmarket.feature.stockdetail.presention.viewmodel.StockDetailViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val moduleStockDetail = module {
    factory { get<Retrofit>().create(StockDetailApi::class.java) }
    factory<StockDetailRemoteDataSource> { StockDetailRemoteDataSourceImpl(stockDetailApi = get()) }
    factory<StockDetailRepository> {
        StockDetailRepositoryImpl(
            remoteDataSource = get(),
            mapper = StockDetailMapper()
        )
    }

    factory { FetchStockDetailUseCase(repository = get()) }

    viewModel { StockDetailViewModel(fetchStockDetailUseCase = get()) }
}
