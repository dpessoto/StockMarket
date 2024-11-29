package com.pessoto.stockmarket.feature.stockslist.data.mapper

import com.pessoto.stockmarket.core.data.mapper.Mapper
import com.pessoto.stockmarket.feature.stockslist.data.model.StocksResponse
import com.pessoto.stockmarket.feature.stockslist.domain.entity.Stock

internal class StocksMapper : Mapper<StocksResponse, List<Stock>> {

    override fun map(source: StocksResponse): List<Stock> {
        return source.stocks.map { stock ->
            Stock(
                stock = stock.stock,
                name = stock.name,
                close = stock.close,
                change = stock.change,
                volume = stock.volume,
                marketCap = stock.marketCap,
                logo = stock.logo,
                type = stock.type
            )
        }
    }
}
