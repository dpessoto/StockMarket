package com.pessoto.stockmarket.feature.stockslist.presentation.viewmodel

import com.pessoto.stockmarket.core.R
import com.pessoto.stockmarket.feature.stockslist.domain.exception.EmptyStockListException
import com.pessoto.stockmarket.feature.stockslist.domain.usecase.FetchStockListUseCase
import com.pessoto.stockmarket.feature.stockslist.presentation.model.StockListUiState
import com.pessoto.stockmarket.feature.stockslist.util.StockListHelper.mockedStockList
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class StockListViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @MockK
    private lateinit var fetchStockListUseCase: FetchStockListUseCase
    private lateinit var viewModel: StockListViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = StockListViewModel(fetchStockListUseCase, testDispatcher)
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `fetch stock list updates UI state on success`() = runTest {
        // Given
        coEvery { fetchStockListUseCase.invoke() } returns flow { emit(mockedStockList) }

        // When
        viewModel.fetchStockList()
        advanceUntilIdle()

        // Then
        val expectedState = StockListUiState(stocks = mockedStockList, isLoading = false)
        assertEquals(expectedState, viewModel.uiState.value)
    }

    @Test
    fun `fetch stock list updates UI state on network error`() = runTest {
        coEvery { fetchStockListUseCase.invoke() } returns flow { throw IOException("Network Error") }

        viewModel.fetchStockList()
        advanceUntilIdle()

        val expectedState = StockListUiState(
            stocks = emptyList(),
            isLoading = false,
            errorMessage = R.string.network_error
        )
        assertEquals(expectedState, viewModel.uiState.value)
    }

    @Test
    fun `fetch stock list updates UI state on unexpected error`() = runTest {
        coEvery { fetchStockListUseCase.invoke() } returns flow { throw Exception("Unexpected Error") }

        viewModel.fetchStockList()
        advanceUntilIdle()

        val expectedState = StockListUiState(
            stocks = emptyList(), isLoading = false,
            errorMessage = R.string.unexpected_error
        )
        assertEquals(expectedState, viewModel.uiState.value)
    }

    @Test
    fun `fetch stock list updates UI state on empty list error`() = runTest {
        coEvery { fetchStockListUseCase.invoke() } returns flow { throw EmptyStockListException("Error") }

        viewModel.fetchStockList()
        advanceUntilIdle()

        val expectedState = StockListUiState(
            stocks = emptyList(),
            isLoading = false,
            errorMessage = R.string.empty_stock_list_error
        )
        assertEquals(expectedState, viewModel.uiState.value)
    }
}
