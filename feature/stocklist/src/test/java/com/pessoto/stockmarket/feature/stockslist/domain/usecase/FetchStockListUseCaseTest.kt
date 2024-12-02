package com.pessoto.stockmarket.feature.stockslist.domain.usecase

import com.pessoto.stockmarket.feature.stockslist.domain.exception.EmptyStockListException
import com.pessoto.stockmarket.feature.stockslist.domain.repository.StockListRepository
import com.pessoto.stockmarket.feature.stockslist.util.StockListHelper.mockedStockList
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
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
        // GIVEN
        coEvery { repository.fetchStockList() } returns flowOf(mockedStockList)

        // WHEN
        val result = useCase()

        // THEN
        result.collect {
            assertEquals(mockedStockList, it)
        }
    }

    @Test
    fun `invoke with empty list throws EmptyStockListException`() = runTest {
        // GIVEN
        coEvery { repository.fetchStockList() } throws EmptyStockListException()

        // WHEN & THEN
        assertThrows(EmptyStockListException::class.java) {
                useCase.invoke()
        }
    }
}
