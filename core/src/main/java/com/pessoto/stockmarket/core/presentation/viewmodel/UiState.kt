package com.pessoto.stockmarket.core.presentation.viewmodel

sealed class UiState<out T> {
    data object Loading : UiState<Nothing>()
    data class Success<out T>(val result: T?) : UiState<T>()
    data class Error(val errorMessage: Int) : UiState<Nothing>()
}
