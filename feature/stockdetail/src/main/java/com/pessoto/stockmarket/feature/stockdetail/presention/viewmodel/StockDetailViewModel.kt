package com.pessoto.stockmarket.feature.stockdetail.presention.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pessoto.stockmarket.core.R
import com.pessoto.stockmarket.core.presentation.viewmodel.UiState
import com.pessoto.stockmarket.feature.stockdetail.domain.entity.StockDetail
import com.pessoto.stockmarket.feature.stockdetail.domain.exception.StockDetailNotFoundException
import com.pessoto.stockmarket.feature.stockdetail.domain.usecase.FetchStockDetailUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.io.IOException

class StockDetailViewModel(
    private val fetchStockDetailUseCase: FetchStockDetailUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<StockDetail>>(UiState.Loading)
    val uiState: StateFlow<UiState<StockDetail>> = _uiState

    private val _chartLoading = MutableStateFlow(false)
    val chartLoading: StateFlow<Boolean> = _chartLoading

    private val _chartError = MutableStateFlow<Int?>(null)
    val chartError: StateFlow<Int?> = _chartError

    fun fetchStockDetail(ticker: String) {
        viewModelScope.launch(dispatcher) {
            fetchStockDetailUseCase.invoke(ticker)
                .onStart {
                    _uiState.value = UiState.Loading
                }
                .catch { error ->
                    handleError(error)
                }
                .collect { stock ->
                    handleStockSuccess(stock)
                }
        }
    }

    fun fetchStockDetail(ticker: String, range: String) {
        viewModelScope.launch(dispatcher) {
            fetchStockDetailUseCase.invoke(ticker, range)
                .onStart {
                    _chartLoading.value = true
                    _chartError.value = null
                }
                .catch { error ->
                    handleChartError(error)
                }
                .collect { stock ->
                    _chartLoading.value = false
                    _chartError.value = null
                    handleStockSuccess(stock)
                }
        }
    }

    private fun handleStockSuccess(stocks: StockDetail) {
        _uiState.value = UiState.Success(stocks)
    }

    private fun handleError(error: Throwable) {
        _chartLoading.value = false
        _uiState.value = UiState.Error(getErrorMessage(error))
    }

    private fun handleChartError(error: Throwable) {
        _chartLoading.value = false
        _chartError.value = getErrorMessage(error)
    }

    private fun getErrorMessage(error: Throwable): Int {
        return when (error) {
            is StockDetailNotFoundException -> R.string.stock_detail_not_found_error
            is IOException -> R.string.network_error
            else -> R.string.unexpected_error
        }
    }
}
