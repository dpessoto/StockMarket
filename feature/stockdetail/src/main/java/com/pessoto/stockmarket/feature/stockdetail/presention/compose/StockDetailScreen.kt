package com.pessoto.stockmarket.feature.stockdetail.presention.compose

import android.text.Layout
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
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberAxisGuidelineComponent
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottom
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStart
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberCandlestickCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.common.component.fixed
import com.patrykandpatrick.vico.compose.common.component.rememberLayeredComponent
import com.patrykandpatrick.vico.compose.common.component.rememberShapeComponent
import com.patrykandpatrick.vico.compose.common.component.rememberTextComponent
import com.patrykandpatrick.vico.compose.common.component.shadow
import com.patrykandpatrick.vico.compose.common.dimensions
import com.patrykandpatrick.vico.compose.common.fill
import com.patrykandpatrick.vico.compose.common.shape.markerCorneredShape
import com.patrykandpatrick.vico.core.cartesian.CartesianMeasuringContext
import com.patrykandpatrick.vico.core.cartesian.HorizontalDimensions
import com.patrykandpatrick.vico.core.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.cartesian.data.CandlestickCartesianLayerModel
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModel
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.marker.CartesianMarker
import com.patrykandpatrick.vico.core.cartesian.marker.DefaultCartesianMarker
import com.patrykandpatrick.vico.core.common.Fill
import com.patrykandpatrick.vico.core.common.Insets
import com.patrykandpatrick.vico.core.common.LayeredComponent
import com.patrykandpatrick.vico.core.common.component.Shadow
import com.patrykandpatrick.vico.core.common.component.ShapeComponent
import com.patrykandpatrick.vico.core.common.component.TextComponent
import com.patrykandpatrick.vico.core.common.shape.Corner
import com.patrykandpatrick.vico.core.common.shape.CorneredShape
import com.pessoto.stockmarket.core.R
import com.pessoto.stockmarket.core.presentation.compose.ImageShimmer
import com.pessoto.stockmarket.core.presentation.compose.RetryButton
import com.pessoto.stockmarket.core.presentation.extension.shimmerEffect
import com.pessoto.stockmarket.core.presentation.viewmodel.UiState
import com.pessoto.stockmarket.feature.stockdetail.domain.entity.HistoricalDataPrice
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

private const val LABEL_BACKGROUND_SHADOW_RADIUS_DP = 4f
private const val LABEL_BACKGROUND_SHADOW_DY_DP = 2f
private const val CLIPPING_FREE_SHADOW_RADIUS_MULTIPLIER = 1.4f

@Composable
fun StockDetailScreen(
    modifier: Modifier,
    ticker: String,
    viewModel: StockDetailViewModel = koinViewModel()
) {
    LaunchedEffect(ticker) {
        viewModel.fetchStockDetail(ticker)
    }

    val stockDetailUiState = viewModel.uiState.collectAsStateWithLifecycle()

    SetupStockDetail(modifier, stockDetailUiState) {
        viewModel.fetchStockDetail(ticker)
    }
}

@Composable
private fun SetupStockDetail(
    modifier: Modifier,
    stockDetailUiState: State<UiState<StockDetail>>,
    onRetry: () -> Unit,
) {
    val stockDetailUi = stockDetailUiState.value
    Box(modifier = modifier.fillMaxSize()) {
        when (stockDetailUi) {
            is UiState.Loading -> {
                StockDetailLoading()
            }

            is UiState.Error -> {
                RetryButton(
                    messageError = stockDetailUi.errorMessage,
                    onRetry = { onRetry.invoke() })
            }

            is UiState.Success -> {
                stockDetailUi.result?.let {
                    StockDetailContent(it)
                }
            }
        }
    }
}

@Composable
private fun StockDetailContent(stockDetail: StockDetail) {
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
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
            CandleStick(Modifier.padding(top = 16.dp), it)
        } ?: Text(
            text = stringResource(R.string.no_data_available),
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun CandleStick(modifier: Modifier = Modifier, historicalData: List<HistoricalDataPrice>) {
    historicalData.size
    val modelProducer = remember { CartesianChartModelProducer() }
    LaunchedEffect(Unit) {
        modelProducer.runTransaction {
            val opening = historicalData.map { it.open }
            val closing = historicalData.map { it.close }
            val low = historicalData.map { it.low }
            val high = historicalData.map { it.high }
            add(
                CandlestickCartesianLayerModel.partial(
                    opening = opening,
                    closing = closing,
                    low = low,
                    high = high
                )
            )
        }
    }

    val marker = rememberMarker(showIndicator = false)
    CartesianChartHost(
        chart =
        rememberCartesianChart(
            rememberCandlestickCartesianLayer(),
            startAxis = VerticalAxis.rememberStart(),
            bottomAxis =
            HorizontalAxis.rememberBottom(
                guideline = null,
                itemPlacer =
                remember {
                    HorizontalAxis.ItemPlacer.aligned(spacing = 1)
                },
            ),
            marker = marker
        ),
        modelProducer = modelProducer,
        modifier = modifier,
    )
}

@Composable
private fun rememberMarker(
    labelPosition: DefaultCartesianMarker.LabelPosition = DefaultCartesianMarker.LabelPosition.Top,
    showIndicator: Boolean = true,
): CartesianMarker {
    val labelBackgroundShape = markerCorneredShape(Corner.FullyRounded)
    val labelBackground =
        rememberShapeComponent(
            fill = fill(MaterialTheme.colorScheme.surfaceBright),
            shape = labelBackgroundShape,
            shadow =
            shadow(
                radius = LABEL_BACKGROUND_SHADOW_RADIUS_DP.dp,
                dy = LABEL_BACKGROUND_SHADOW_DY_DP.dp
            ),
        )
    val label =
        rememberTextComponent(
            color = MaterialTheme.colorScheme.onSurface,
            textAlignment = Layout.Alignment.ALIGN_CENTER,
            padding = dimensions(8.dp, 4.dp),
            background = labelBackground,
            minWidth = TextComponent.MinWidth.fixed(40.dp),
        )
    val indicatorFrontComponent =
        rememberShapeComponent(fill(MaterialTheme.colorScheme.surface), CorneredShape.Pill)
    val indicatorCenterComponent = rememberShapeComponent(shape = CorneredShape.Pill)
    val indicatorRearComponent = rememberShapeComponent(shape = CorneredShape.Pill)
    val indicator =
        rememberLayeredComponent(
            rear = indicatorRearComponent,
            front =
            rememberLayeredComponent(
                rear = indicatorCenterComponent,
                front = indicatorFrontComponent,
                padding = dimensions(5.dp),
            ),
            padding = dimensions(10.dp),
        )
    val guideline = rememberAxisGuidelineComponent()
    return remember(label, labelPosition, indicator, showIndicator, guideline) {
        object :
            DefaultCartesianMarker(
                label = label,
                labelPosition = labelPosition,
                indicator =
                if (showIndicator) {
                    { color ->
                        LayeredComponent(
                            rear =
                            ShapeComponent(
                                Fill(ColorUtils.setAlphaComponent(color, 38)),
                                CorneredShape.Pill
                            ),
                            front =
                            LayeredComponent(
                                rear =
                                ShapeComponent(
                                    fill = Fill(color),
                                    shape = CorneredShape.Pill,
                                    shadow = Shadow(radiusDp = 12f, color = color),
                                ),
                                front = indicatorFrontComponent,
                                padding = dimensions(5.dp),
                            ),
                            padding = dimensions(10.dp),
                        )
                    }
                } else {
                    null
                },
                indicatorSizeDp = 36f,
                guideline = guideline,
            ) {
            override fun updateInsets(
                context: CartesianMeasuringContext,
                horizontalDimensions: HorizontalDimensions,
                model: CartesianChartModel,
                insets: Insets,
            ) {
                with(context) {
                    val baseShadowInsetDp =
                        CLIPPING_FREE_SHADOW_RADIUS_MULTIPLIER * LABEL_BACKGROUND_SHADOW_RADIUS_DP
                    var topInset = (baseShadowInsetDp - LABEL_BACKGROUND_SHADOW_DY_DP).pixels
                    var bottomInset = (baseShadowInsetDp + LABEL_BACKGROUND_SHADOW_DY_DP).pixels
                    when (labelPosition) {
                        LabelPosition.Top,
                        LabelPosition.AbovePoint -> topInset += label.getHeight(context) + tickSizeDp.pixels

                        LabelPosition.Bottom -> bottomInset += label.getHeight(context) + tickSizeDp.pixels
                        LabelPosition.AroundPoint -> {}
                    }
                    insets.ensureValuesAtLeast(top = topInset, bottom = bottomInset)
                }
            }
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
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(top = 16.dp)
                .clip(RoundedCornerShape(8.dp))
                .shimmerEffect()
        )
    }
}
