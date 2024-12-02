package com.pessoto.stockmarket.feature.stockdetail.presention.viewmodel

import android.util.Log
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
                    handleSuccess(stock)
                }
        }
    }

    private fun handleSuccess(stocks: StockDetail) {
        _uiState.value = UiState.Success(stocks)
    }

    private fun handleError(error: Throwable) {
        val errorMessage = when (error) {
            is StockDetailNotFoundException -> {
                R.string.stock_detail_not_found_error
            }

            is IOException -> {
                R.string.network_error
            }

            else -> {
                R.string.unexpected_error
            }
        }

        _uiState.value = UiState.Error(errorMessage)
    }
}
