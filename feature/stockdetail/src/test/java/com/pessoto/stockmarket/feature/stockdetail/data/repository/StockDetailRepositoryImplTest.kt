package com.pessoto.stockmarket.feature.stockdetail.data.repository

import com.pessoto.stockmarket.feature.stockdetail.data.mapper.StockDetailMapper
import com.pessoto.stockmarket.feature.stockdetail.data.model.StockDetailResultResponse
import com.pessoto.stockmarket.feature.stockdetail.data.source.remote.StockDetailRemoteDataSource
import com.pessoto.stockmarket.feature.stockdetail.domain.exception.StockDetailNotFoundException
import com.pessoto.stockmarket.feature.stockdetail.util.StockDetailHelper.mockedStockDetailResultResponse
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test

class StockDetailRepositoryImplTest {

    @MockK
    private lateinit var remoteDataSource: StockDetailRemoteDataSource

    private lateinit var mapper: StockDetailMapper
    private lateinit var repository: StockDetailRepositoryImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        mapper = StockDetailMapper()
        repository = StockDetailRepositoryImpl(remoteDataSource, mapper)
    }

    @Test
    fun `fetchStockDetail returns mapped stock detail`() = runTest {
        // GIVEN
        val ticker = "PETR4"
        val range = "1d"
        coEvery { remoteDataSource.fetchStockDetail(ticker, range) } returns mockedStockDetailResultResponse

        // WHEN
        val result = repository.fetchStockDetail(ticker, range)

        // THEN
        result.collect { stockDetail ->
            assertEquals(mapper.map(mockedStockDetailResultResponse.results[0]), stockDetail)
        }
        coVerify { remoteDataSource.fetchStockDetail(ticker, range) }
    }

    @Test
    fun `fetchStockDetail empty response throws StockDetailNotFoundException`() = runTest {
        // GIVEN
        val ticker = "PETR4"
        val range = "1d"
        coEvery { remoteDataSource.fetchStockDetail(ticker, range) } returns StockDetailResultResponse(
            emptyList()
        )

        // WHEN
        val result = repository.fetchStockDetail(ticker, range)

        // THEN
        assertThrows(StockDetailNotFoundException::class.java) {
            runBlocking {
                result.last()
            }
        }
    }
}
