package com.pessoto.stockmarket.feature.stockslist.presentation.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pessoto.stockmarket.core.presentation.compose.ImageShimmer
import com.pessoto.stockmarket.core.presentation.compose.RetryButton
import com.pessoto.stockmarket.core.presentation.extension.shimmerEffect
import com.pessoto.stockmarket.core.presentation.navigation.LocalNavController
import com.pessoto.stockmarket.core.presentation.navigation.StockMarketNavScreen
import com.pessoto.stockmarket.core.presentation.viewmodel.UiState
import com.pessoto.stockmarket.feature.stockslist.domain.entity.Stock
import com.pessoto.stockmarket.feature.stockslist.presentation.viewmodel.StockListViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun StockListScreen(modifier: Modifier, viewModel: StockListViewModel = koinViewModel()) {
    LaunchedEffect(Unit) {
        viewModel.fetchStockList()
    }

    val stockListUiState = viewModel.uiState.collectAsStateWithLifecycle()

    SetupStockList(
        modifier = modifier,
        stockListUiState = stockListUiState,
    ) {
        viewModel.fetchStockList()
    }
}

@Composable
private fun SetupStockList(
    modifier: Modifier,
    stockListUiState: State<UiState<List<Stock>>>,
    onRetry: () -> Unit
) {
    val customCardColors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        disabledContainerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
        disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
    )
    val navController = LocalNavController.current
    val stockListUi = stockListUiState.value
    Box(modifier = modifier.fillMaxSize()) {
        when (stockListUi) {
            is UiState.Loading -> {
                StockListLoading()
            }

            is UiState.Error -> {
                RetryButton(
                    messageError = stockListUi.errorMessage,
                    onRetry = { onRetry.invoke() })
            }

            is UiState.Success -> {
                stockListUi.result?.let {
                    StockList(it, customCardColors) { ticker ->
                        navController.navigate(
                            StockMarketNavScreen.STOCK_DETAIL.route + ticker
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun BoxScope.StockList(
    stocks: List<Stock>,
    customCardColors: CardColors,
    onClick: (ticker: String) -> Unit
) {
    LazyColumn(Modifier.align(Alignment.TopStart)) {
        items(stocks) { stock ->
            StockItem(stock = stock, customCardColors = customCardColors, onClick = onClick)
        }
    }
}

@Composable
private fun StockItem(
    stock: Stock,
    customCardColors: CardColors,
    onClick: (ticker: String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick.invoke(stock.stock) },
        shape = RoundedCornerShape(8.dp),
        colors = customCardColors,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            ImageShimmer(
                url = stock.logo,
                contentDescription = stock.name,
                size = 80.dp,
                shape = RoundedCornerShape(8.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(Modifier.align(Alignment.CenterVertically)) {
                Text(text = stock.stock)
                Text(text = stock.name)
            }
        }
    }
}

@Composable
private fun BoxScope.StockListLoading(size: Int = 16) {
    LazyColumn(Modifier.align(Alignment.TopStart)) {
        items(size) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(112.dp)
                    .padding(8.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .shimmerEffect()
            )
        }
    }
}
