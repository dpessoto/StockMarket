package com.pessoto.stockmarket.feature.stockslist.data.source.remote

import com.pessoto.stockmarket.feature.stockslist.data.api.StockListApi
import com.pessoto.stockmarket.feature.stockslist.util.StockListHelper.mockedStocksResponse
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class StockListRemoteDataSourceImplTest {

    @MockK
    private lateinit var api: StockListApi

    private lateinit var remoteDataSource: StockListRemoteDataSourceImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        remoteDataSource = StockListRemoteDataSourceImpl(api)
    }

    @Test
    fun `fetchStockList emits data from api`() = runTest {
        // Given
        coEvery { api.fetchStockList(limit = any(), type = any()) } returns mockedStocksResponse

        // When
        val result = remoteDataSource.fetchStockList()

        // Then
        assert(result == mockedStocksResponse)
        coVerify { api.fetchStockList(limit = any(), type = any()) }
    }
}