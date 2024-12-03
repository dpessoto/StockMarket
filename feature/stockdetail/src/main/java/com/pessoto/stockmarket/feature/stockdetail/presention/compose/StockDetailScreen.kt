package com.pessoto.stockmarket.feature.stockdetail.presention.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pessoto.stockmarket.core.R
import com.pessoto.stockmarket.core.presentation.compose.ImageShimmer
import com.pessoto.stockmarket.core.presentation.compose.RetryButton
import com.pessoto.stockmarket.core.presentation.extension.shimmerEffect
import com.pessoto.stockmarket.core.presentation.viewmodel.UiState
import com.pessoto.stockmarket.feature.stockdetail.domain.entity.StockDetail
import com.pessoto.stockmarket.feature.stockdetail.presention.viewmodel.StockDetailViewModel
import org.koin.androidx.compose.koinViewModel
import java.text.DateFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Currency
import java.util.Date
import java.util.Locale

@Composable
fun StockDetailScreen(
    modifier: Modifier,
    ticker: String,
    viewModel: StockDetailViewModel = koinViewModel()
) {
    LaunchedEffect(ticker) {
        viewModel.fetchStockDetail(ticker)
    }

    val stockDetailUiState by viewModel.uiState.collectAsStateWithLifecycle()
    val chartLoading by viewModel.chartLoading.collectAsStateWithLifecycle()
    val chartError by viewModel.chartError.collectAsStateWithLifecycle()

    SetupStockDetail(
        modifier = modifier,
        stockDetailState = stockDetailUiState,
        chartLoading = chartLoading,
        chartError = chartError,
        onRetry = { viewModel.fetchStockDetail(ticker) },
        onClickRage = { range -> viewModel.fetchStockDetail(ticker, range) }
    )
}

@Composable
private fun SetupStockDetail(
    modifier: Modifier,
    stockDetailState: UiState<StockDetail>,
    chartLoading: Boolean,
    chartError: Int?,
    onRetry: () -> Unit,
    onClickRage: (range: String) -> Unit
) {
    Box(modifier = modifier.fillMaxSize()) {
        when (stockDetailState) {
            is UiState.Loading -> {
                StockDetailLoading()
            }

            is UiState.Error -> {
                RetryButton(
                    messageError = stockDetailState.errorMessage,
                    onRetry = { onRetry.invoke() })
            }

            is UiState.Success -> {
                stockDetailState.result?.let {
                    StockDetailContent(it, chartLoading, chartError, onClickRage)
                }
            }
        }
    }
}

@Composable
private fun StockDetailContent(
    stockDetail: StockDetail,
    chartLoading: Boolean,
    chartError: Int?,
    onClickRage: (range: String) -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Row(modifier = Modifier.fillMaxWidth()) {
            ImageShimmer(
                url = stockDetail.logourl,
                contentDescription = stockDetail.longName,
                size = 160.dp,
                shape = RoundedCornerShape(8.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(Modifier.align(Alignment.CenterVertically)) {
                Text(
                    text = stockDetail.symbol,
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = stockDetail.longName,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = formatPrice(stockDetail.regularMarketPrice, stockDetail.currency),
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = formatDateTime(stockDetail.regularMarketTime),
                style = MaterialTheme.typography.bodyMedium
            )
        }
        stockDetail.historicalDataPrice?.takeIf { it.isNotEmpty() }?.let {
            if (chartLoading) {
                Spacer(modifier = Modifier.height(16.dp))
                RangeSelectorLoading()
                Spacer(modifier = Modifier.height(16.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .shimmerEffect()
                )
            } else if (chartError != null) {
                Spacer(modifier = Modifier.height(16.dp))
                RangeSelector(select = stockDetail.usedRange) { selectedRange ->
                    onClickRage(selectedRange)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(chartError),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.error
                )
            } else {
                Spacer(modifier = Modifier.height(16.dp))
                RangeSelector(select = stockDetail.usedRange) { selectedRange ->
                    onClickRage(selectedRange)
                }
                Spacer(modifier = Modifier.height(8.dp))
                CandleStick(historicalData = it)
            }
        } ?: run {
            Spacer(modifier = Modifier.height(16.dp))
            RangeSelector(select = stockDetail.usedRange) { selectedRange ->
                onClickRage(selectedRange)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.no_data_available),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    }
}

private fun formatPrice(price: Double, currency: String): String {
    val format = NumberFormat.getCurrencyInstance()
    format.currency = Currency.getInstance(currency)
    return format.format(price)
}

private fun formatDateTime(dateTime: String): String {
    val inputFormatter = DateTimeFormatter.ISO_ZONED_DATE_TIME
    val zonedDateTime = ZonedDateTime.parse(dateTime, inputFormatter)
    val date = Date.from(zonedDateTime.toInstant())

    val dateFormat: DateFormat = SimpleDateFormat.getDateTimeInstance(
        DateFormat.SHORT, DateFormat.MEDIUM, Locale.getDefault()
    )

    return dateFormat.format(date)
}

@Composable
private fun StockDetailLoading() {
    Column(modifier = Modifier.padding(16.dp)) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .size(160.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .shimmerEffect()
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(Modifier.align(Alignment.CenterVertically)) {
                Box(
                    modifier = Modifier
                        .height(24.dp)
                        .width(100.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .shimmerEffect()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .height(20.dp)
                        .width(150.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .shimmerEffect()
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Box(
                modifier = Modifier
                    .height(20.dp)
                    .width(80.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .shimmerEffect()
            )
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .height(20.dp)
                    .width(120.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .shimmerEffect()
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        RangeSelectorLoading()
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(8.dp))
                .shimmerEffect()
        )
    }
}

@Composable
private fun RangeSelectorLoading() {
    Row(modifier = Modifier.fillMaxWidth()) {
        repeat(4) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(40.dp)
                    .padding(end = 8.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .shimmerEffect()
            )
        }
    }
}
