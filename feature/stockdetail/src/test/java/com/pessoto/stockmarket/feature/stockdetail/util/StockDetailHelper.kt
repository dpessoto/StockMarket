package com.pessoto.stockmarket.feature.stockdetail.util

import com.pessoto.stockmarket.feature.stockdetail.data.model.HistoricalDataPriceResponse
import com.pessoto.stockmarket.feature.stockdetail.data.model.StockDetailResponse
import com.pessoto.stockmarket.feature.stockdetail.data.model.StockDetailResultResponse
import com.pessoto.stockmarket.feature.stockdetail.domain.entity.HistoricalDataPrice
import com.pessoto.stockmarket.feature.stockdetail.domain.entity.StockDetail

object StockDetailHelper {

    val mockedStockDetailResponse = StockDetailResponse(
        symbol = "PETR4",
        currency = "BRL",
        longName = "Petróleo Brasileiro S.A. - Petrobras",
        regularMarketTime = "2023-11-17T21:07:47.000Z",
        regularMarketPrice = 36.71,
        logourl = "https://s3-symbol-logo.tradingview.com/brasileiro-petrobras--big.svg",
        historicalDataPrice = listOf(
            HistoricalDataPriceResponse(
                date = 1699621200,
                open = 32.12,
                close = 34.72,
                high = 35.12,
                low = 31.12,
            ),
            HistoricalDataPriceResponse(
                date = 1699880400,
                open = 34.72,
                close = 35.69,
                high = 36.12,
                low = 34.12,
            )
        )
    )

    val mockedStockDetailResultResponse = StockDetailResultResponse(
        listOf(
            mockedStockDetailResponse
        )
    )

    val mockedStockDetail = StockDetail(
        symbol = mockedStockDetailResponse.symbol,
        currency = mockedStockDetailResponse.currency,
        longName = mockedStockDetailResponse.longName,
        regularMarketTime = mockedStockDetailResponse.regularMarketTime,
        regularMarketPrice = mockedStockDetailResponse.regularMarketPrice,
        logourl = mockedStockDetailResponse.logourl,
        historicalDataPrice = mockedStockDetailResponse.historicalDataPrice?.map {
            HistoricalDataPrice(
                date = it.date,
                open = it.open,
                close = it.close,
                high = it.high,
                low = it.low
            )
        }
    )
}
