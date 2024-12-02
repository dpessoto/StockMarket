package com.pessoto.stockmarket.feature.stockslist.presentation.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.ImageLoader
import coil.compose.SubcomposeAsyncImage
import com.pessoto.stockmarket.core.R
import com.pessoto.stockmarket.core.presentation.extension.shimmerEffect
import com.pessoto.stockmarket.feature.stockslist.domain.entity.Stock
import com.pessoto.stockmarket.feature.stockslist.presentation.viewmodel.StockListViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.getKoin

@Composable
fun StockListScreen(modifier: Modifier, viewModel: StockListViewModel = koinViewModel()) {
    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        viewModel.fetchStockList()
    }

    val customCardColors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        disabledContainerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
        disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
    )

    val stockListUiState by viewModel.uiState.collectAsStateWithLifecycle()

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        when {
            stockListUiState.isLoading -> {
                StockListLoading()
            }

            stockListUiState.errorMessage != null -> {
                RetryButton(
                    messageError = stockListUiState.errorMessage,
                    onRetry = { viewModel.fetchStockList() })
            }

            stockListUiState.stocks.isNotEmpty() -> {
                StockList(stockListUiState.stocks, customCardColors)
            }
        }
    }
}

@Composable
private fun BoxScope.StockList(stocks: List<Stock>, customCardColors: CardColors) {
    LazyColumn(Modifier.align(Alignment.TopStart)) {
        items(stocks) { stock ->
            StockItem(stock, customCardColors)
        }
    }
}

@Composable
private fun StockItem(
    stock: Stock,
    customCardColors: CardColors,
    imageLoader: ImageLoader = getKoin().get()
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        colors = customCardColors,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            SubcomposeAsyncImage(
                model = stock.logo,
                contentDescription = stock.name,
                imageLoader = imageLoader,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop,
                loading = {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .shimmerEffect()
                    )
                },
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
                    .height(96.dp)
                    .padding(8.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .shimmerEffect()
            )
        }
    }
}

@Composable
private fun RetryButton(messageError: Int?, onRetry: () -> Unit) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (messageError != null) {
            Text(text = context.getString(messageError))
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onRetry) {
            Text(text = context.getString(R.string.try_again))
        }
    }
}

