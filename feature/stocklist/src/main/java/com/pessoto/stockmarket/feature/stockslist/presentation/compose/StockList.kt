package com.pessoto.stockmarket.feature.stockslist.presentation.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pessoto.stockmarket.feature.stockslist.domain.entity.Stock
import com.pessoto.stockmarket.feature.stockslist.presentation.viewmodel.StockListViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun StockListScreen(modifier: Modifier, viewModel: StockListViewModel = koinViewModel()) {
    viewModel.fetchStockList()
    val stockList by viewModel.stockList.collectAsStateWithLifecycle()

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        if (stockList.isNotEmpty())
            LazyColumn(Modifier.align(Alignment.TopStart)) {
                items(stockList) { stock ->
                    StockItem(stock)
                }
            }

    }
}

@Composable
fun StockItem(stock: Stock) {
    Text(text = stock.stock)
}
