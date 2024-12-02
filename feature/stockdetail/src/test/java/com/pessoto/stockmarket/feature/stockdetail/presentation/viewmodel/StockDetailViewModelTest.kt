package com.pessoto.stockmarket.feature.stockdetail.presentation.viewmodel

import com.pessoto.stockmarket.core.R
import com.pessoto.stockmarket.core.presentation.viewmodel.UiState
import com.pessoto.stockmarket.feature.stockdetail.domain.exception.StockDetailNotFoundException
import com.pessoto.stockmarket.feature.stockdetail.domain.usecase.FetchStockDetailUseCase
import com.pessoto.stockmarket.feature.stockdetail.presention.viewmodel.StockDetailViewModel
import com.pessoto.stockmarket.feature.stockdetail.util.StockDetailHelper.mockedStockDetail
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
class StockDetailViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @MockK
    private lateinit var fetchStockDetailUseCase: FetchStockDetailUseCase
    private lateinit var viewModel: StockDetailViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = StockDetailViewModel(fetchStockDetailUseCase, testDispatcher)
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `fetch stock list updates UI state on success`() = runTest {
        // Given
        coEvery { fetchStockDetailUseCase.invoke("AAPL") } returns flow { emit(mockedStockDetail) }

        // When
        viewModel.fetchStockDetail("AAPL")
        advanceUntilIdle()

        // Then
        val expectedState = UiState.Success(mockedStockDetail)
        assertEquals(expectedState, viewModel.uiState.value)
    }

    @Test
    fun `fetch stock list updates UI state on network error`() = runTest {
        // Given
        coEvery { fetchStockDetailUseCase.invoke("MSFT") } returns flow { throw IOException("Network Error") }

        // When
        viewModel.fetchStockDetail("MSFT")
        advanceUntilIdle()

        // Then
        val expectedState = UiState.Error(R.string.network_error)
        assertEquals(expectedState, viewModel.uiState.value)
    }

    @Test
    fun `fetch stock list updates UI state on unexpected error`() = runTest {
        // Given
        coEvery { fetchStockDetailUseCase.invoke("AMZN") } returns flow { throw Exception("Unexpected Error") }

        // When
        viewModel.fetchStockDetail("AMZN")
        advanceUntilIdle()

        // Then
        val expectedState = UiState.Error(R.string.unexpected_error)
        assertEquals(expectedState, viewModel.uiState.value)
    }

    @Test
    fun `fetch stock list updates UI state on empty list error`() = runTest {
        // Given
        coEvery { fetchStockDetailUseCase.invoke("GOOG") } returns flow {
            throw StockDetailNotFoundException()
        }

        // When
        viewModel.fetchStockDetail("GOOG")
        advanceUntilIdle()

        // Then
        val expectedState = UiState.Error(R.string.stock_detail_not_found_error)
        assertEquals(expectedState, viewModel.uiState.value)
    }
}
