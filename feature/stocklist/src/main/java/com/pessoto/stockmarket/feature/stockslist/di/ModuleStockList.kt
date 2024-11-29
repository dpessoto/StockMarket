package com.pessoto.stockmarket.feature.stockslist.di

import com.pessoto.stockmarket.feature.stockslist.data.api.StockListApi
import com.pessoto.stockmarket.feature.stockslist.data.mapper.StocksMapper
import com.pessoto.stockmarket.feature.stockslist.data.repository.StockListRepositoryImpl
import com.pessoto.stockmarket.feature.stockslist.data.source.remote.StockListRemoteDataSource
import com.pessoto.stockmarket.feature.stockslist.data.source.remote.StockListRemoteDataSourceImpl
import com.pessoto.stockmarket.feature.stockslist.domain.repository.StockListRepository
import com.pessoto.stockmarket.feature.stockslist.domain.usecase.FetchStockListUseCase
import com.pessoto.stockmarket.feature.stockslist.presentation.viewmodel.StockListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val moduleListPhotos = module {
    factory { get<Retrofit>().create(StockListApi::class.java) }
    factory<StockListRemoteDataSource> { StockListRemoteDataSourceImpl(stockListApi = get()) }
    factory<StockListRepository> {
        StockListRepositoryImpl(
            remoteDataSource = get(),
            mapper = StocksMapper()
        )
    }

    factory { FetchStockListUseCase(repository = get()) }

    viewModel { StockListViewModel(fetchStockListUseCase = get()) }
}
