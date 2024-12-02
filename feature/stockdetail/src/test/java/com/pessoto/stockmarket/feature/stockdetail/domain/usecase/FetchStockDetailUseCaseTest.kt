package com.pessoto.stockmarket.feature.stockdetail.domain.usecase

import com.pessoto.stockmarket.feature.stockdetail.domain.repository.StockDetailRepository
import com.pessoto.stockmarket.feature.stockdetail.util.StockDetailHelper.mockedStockDetail
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class FetchStockDetailUseCaseTest {

    @MockK
    private lateinit var repository: StockDetailRepository

    private lateinit var useCase: FetchStockDetailUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = FetchStockDetailUseCase(repository)
    }

    @Test
    fun `invoke returns stock detail`() = runTest {
        // GIVEN
        val ticker = "PETR4"
        coEvery { repository.fetchStockDetail(ticker) } returns flowOf(mockedStockDetail)

        // WHEN
        val result = useCase(ticker)

        // THEN
        assertEquals(mockedStockDetail, result.last())
    }
}