package com.pessoto.stockmarket.feature.stockslist.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pessoto.stockmarket.feature.stockslist.domain.entity.Stock
import com.pessoto.stockmarket.feature.stockslist.domain.usecase.FetchStockListUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class StockListViewModel(
    private val fetchStockListUseCase: FetchStockListUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _stockList = MutableStateFlow<List<Stock>>(emptyList())
    val stockList: StateFlow<List<Stock>> = _stockList

    fun fetchStockList() {
        viewModelScope.launch(dispatcher) {
            fetchStockListUseCase.invoke()
                .catch { e ->
                    _stockList.value = emptyList()
                }
                .collect { stocks ->
                    _stockList.value = stocks
                }
        }
    }
}
