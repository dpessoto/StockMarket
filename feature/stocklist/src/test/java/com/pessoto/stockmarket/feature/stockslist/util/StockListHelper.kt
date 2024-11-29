package com.pessoto.stockmarket.feature.stockslist.util

import com.pessoto.stockmarket.feature.stockslist.data.model.StockResponse
import com.pessoto.stockmarket.feature.stockslist.data.model.StocksResponse
import com.pessoto.stockmarket.feature.stockslist.domain.entity.Stock

object StockListHelper {

    internal val mockedStocksResponse = StocksResponse(
        stocks = listOf(
            StockResponse(
                stock = "N1TR34",
                name = "NORTHERN TRUDRN ED",
                close = 209.42999,
                change = 0.129083,
                volume = 5,
                marketCap = 87580484054.45544,
                logo = "https://s3-symbol-logo.tradingview.com/northern-trust--big.svg",
                type = "bdr"
            ),
            StockResponse(
                stock = "AAPL",
                name = "Apple Inc.",
                close = 150.0,
                change = 1.5,
                volume = 10000,
                marketCap = 2500000000000.0,
                logo = "https://s3-symbol-logo.tradingview.com/apple--big.svg",
                type = "stock"
            )
        )
    )

    internal val mockedStockList = listOf(
        Stock(
            stock = "N1TR34",
            name = "NORTHERN TRUDRN ED",
            close = 209.42999,
            change = 0.129083,
            volume = 5,
            marketCap = 87580484054.45544,
            logo = "https://s3-symbol-logo.tradingview.com/northern-trust--big.svg",
            type = "bdr"
        ),
        Stock(
            stock = "AAPL",
            name = "Apple Inc.",
            close = 150.0,
            change = 1.5,
            volume = 10000,
            marketCap = 2500000000000.0,
            logo = "https://s3-symbol-logo.tradingview.com/apple--big.svg",
            type = "stock"
        )
    )
}