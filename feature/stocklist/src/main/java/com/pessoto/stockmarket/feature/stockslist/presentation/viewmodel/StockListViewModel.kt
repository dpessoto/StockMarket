package com.pessoto.stockmarket.feature.stockslist.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pessoto.stockmarket.core.R
import com.pessoto.stockmarket.feature.stockslist.domain.entity.Stock
import com.pessoto.stockmarket.feature.stockslist.domain.exception.EmptyStockListException
import com.pessoto.stockmarket.feature.stockslist.domain.usecase.FetchStockListUseCase
import com.pessoto.stockmarket.feature.stockslist.presentation.model.StockListUiState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import okio.IOException

class StockListViewModel(
    private val fetchStockListUseCase: FetchStockListUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _uiState = MutableStateFlow(StockListUiState())
    val uiState: StateFlow<StockListUiState> = _uiState

    fun fetchStockList() {
        viewModelScope.launch(dispatcher) {
            fetchStockListUseCase.invoke()
                .onStart { _uiState.value = StockListUiState(isLoading = true) }
                .catch { error ->
                    handleError(error)
                }
                .collect { stocks ->
                    handleSuccess(stocks)
                }
        }
    }

    private fun handleSuccess(stocks: List<Stock>) {
        _uiState.value = StockListUiState(stocks = stocks, isLoading = false)
    }

    private fun handleError(error: Throwable) {
        val errorMessage = when (error) {
            is EmptyStockListException -> {
                R.string.empty_stock_list_error
            }
            is IOException -> {
                R.string.network_error
            }
            else -> {
                R.string.unexpected_error
            }
        }

        _uiState.value = StockListUiState(
            stocks = emptyList(),
            isLoading = false,
            errorMessage = errorMessage
        )
    }
}
