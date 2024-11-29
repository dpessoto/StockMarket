package com.pessoto.stockmarket.feature.stockslist.data.repository

import com.pessoto.stockmarket.feature.stockslist.data.mapper.StocksMapper
import com.pessoto.stockmarket.feature.stockslist.data.model.StocksResponse
import com.pessoto.stockmarket.feature.stockslist.data.source.remote.StockListRemoteDataSource
import com.pessoto.stockmarket.feature.stockslist.domain.entity.Stock
import com.pessoto.stockmarket.feature.stockslist.domain.repository.StockListRepository
import com.pessoto.stockmarket.feature.stockslist.util.StockListHelper.mockedStockList
import com.pessoto.stockmarket.feature.stockslist.util.StockListHelper.mockedStocksResponse
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class StockListRepositoryImplTest {

    @MockK
    private lateinit var remoteDataSource: StockListRemoteDataSource

    private lateinit var mapper: StocksMapper
    private lateinit var repository: StockListRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        mapper = StocksMapper()
        repository = StockListRepositoryImpl(remoteDataSource, mapper)
    }

    @Test
    fun `fetchStockList returns mapped stocks`() = runTest {
        //GIVEN
        coEvery { remoteDataSource.fetchStockList() } returns mockedStocksResponse

        //WHEN
        val result = repository.fetchStockList()

        //THEN
        result.collect { stockList ->
            assertEquals(mockedStockList, stockList)

        }
        coVerify { remoteDataSource.fetchStockList() }
    }

    @Test
    fun `fetchStockList empty response returns empty list`() = runTest {
        //GIVEN
        coEvery { remoteDataSource.fetchStockList() } returns StocksResponse(emptyList())

        //WHEN
        val result = repository.fetchStockList()

        //THEN
        result.collect { stockList ->
            assertEquals(emptyList<Stock>(), stockList)

        }
        coVerify { remoteDataSource.fetchStockList() }
    }
}
