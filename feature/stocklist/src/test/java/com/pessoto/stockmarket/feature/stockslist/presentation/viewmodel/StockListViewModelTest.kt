package com.pessoto.stockmarket.feature.stockslist.presentation.viewmodel

import com.pessoto.stockmarket.feature.stockslist.domain.entity.Stock
import com.pessoto.stockmarket.feature.stockslist.domain.usecase.FetchStockListUseCase
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
    fun fetchStockList_updatesStockList_onSuccess() = runTest {
        // Given
        coEvery { fetchStockListUseCase.invoke() } returns flow { emit(mockedStockList) }

        // When
        viewModel.fetchStockList()
        advanceUntilIdle()

        // Then
        assertEquals(mockedStockList, viewModel.stockList.value)
    }

    @Test
    fun fetchStockList_updatesStockList_onError() = runTest {
        coEvery { fetchStockListUseCase.invoke() } returns flow { throw Exception("Error") }

        viewModel.fetchStockList()
        advanceUntilIdle()

        assertEquals(emptyList<Stock>(), viewModel.stockList.value)
    }
}
