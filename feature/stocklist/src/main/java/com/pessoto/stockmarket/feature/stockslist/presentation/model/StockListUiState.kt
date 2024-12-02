package com.pessoto.stockmarket.feature.stockslist.presentation.model

import com.pessoto.stockmarket.feature.stockslist.domain.entity.Stock

data class StockListUiState(
    val stocks: List<Stock> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: Int? = null
)
