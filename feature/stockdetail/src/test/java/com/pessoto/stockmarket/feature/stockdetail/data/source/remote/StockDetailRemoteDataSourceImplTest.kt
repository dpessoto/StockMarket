package com.pessoto.stockmarket.feature.stockdetail.data.source.remote

import com.pessoto.stockmarket.feature.stockdetail.data.api.StockDetailApi
import com.pessoto.stockmarket.feature.stockdetail.util.StockDetailHelper.mockedStockDetailResultResponse
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class StockDetailRemoteDataSourceImplTest {

    @MockK
    private lateinit var api: StockDetailApi

    private lateinit var remoteDataSource: StockDetailRemoteDataSourceImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        remoteDataSource = StockDetailRemoteDataSourceImpl(api)
    }

    @Test
    fun `fetchStockDetail emits data from api`() = runTest {
        // Given
        val ticker = "PETR4"
        coEvery { api.fetchStockDetail(ticker) } returns mockedStockDetailResultResponse

        // When
        val result = remoteDataSource.fetchStockDetail(ticker)

        // Then
        assert(result == mockedStockDetailResultResponse)
        coVerify { api.fetchStockDetail(ticker) }
    }
}