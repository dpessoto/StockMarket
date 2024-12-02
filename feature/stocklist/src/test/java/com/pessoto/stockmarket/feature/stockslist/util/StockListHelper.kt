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
                logo = "https://s3-symbol-logo.tradingview.com/northern-trust--big.svg",
            ),
            StockResponse(
                stock = "AAPL",
                name = "Apple Inc.",
                logo = "https://s3-symbol-logo.tradingview.com/apple--big.svg",
            )
        )
    )

    internal val mockedStockList = listOf(
        Stock(
            stock = "N1TR34",
            name = "NORTHERN TRUDRN ED",
            logo = "https://s3-symbol-logo.tradingview.com/northern-trust--big.svg",
        ),
        Stock(
            stock = "AAPL",
            name = "Apple Inc.",
            logo = "https://s3-symbol-logo.tradingview.com/apple--big.svg",
        )
    )
}