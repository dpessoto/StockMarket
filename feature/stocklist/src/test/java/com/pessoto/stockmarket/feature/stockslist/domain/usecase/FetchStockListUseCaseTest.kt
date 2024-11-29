package com.pessoto.stockmarket.feature.stockslist.domain.usecase

import com.pessoto.stockmarket.feature.stockslist.domain.entity.Stock
import com.pessoto.stockmarket.feature.stockslist.domain.repository.StockListRepository
import com.pessoto.stockmarket.feature.stockslist.util.StockListHelper.mockedStockList
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class FetchStockListUseCaseTest {

    @MockK
    private lateinit var repository: StockListRepository

    private lateinit var useCase: FetchStockListUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = FetchStockListUseCase(repository)
    }

    @Test
    fun `invoke returns list of stocks`() = runTest {
        coEvery { repository.fetchStockList() } returns flowOf(mockedStockList)

        val result = useCase()

        result.collect {
            assertEquals(mockedStockList, it)
        }
    }

    @Test
    fun `invoke with empty list returns empty list`() = runTest {
        coEvery { repository.fetchStockList() } returns flowOf(emptyList())

        val result = useCase()

        result.collect {
            assertEquals(emptyList<Stock>(), it)
        }
    }
}
