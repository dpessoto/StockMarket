package com.pessoto.stockmarket.feature.stockdetail.data.mapper

import com.pessoto.stockmarket.core.data.mapper.Mapper
import com.pessoto.stockmarket.feature.stockdetail.data.model.HistoricalDataPriceResponse
import com.pessoto.stockmarket.feature.stockdetail.data.model.StockDetailResponse
import com.pessoto.stockmarket.feature.stockdetail.domain.entity.HistoricalDataPrice
import com.pessoto.stockmarket.feature.stockdetail.domain.entity.StockDetail

class StockDetailMapper : Mapper<StockDetailResponse, StockDetail> {

    override fun map(source: StockDetailResponse): StockDetail {
        return StockDetail(
            symbol = source.symbol,
            currency = source.currency,
            longName = source.longName,
            regularMarketTime = source.regularMarketTime,
            regularMarketPrice = source.regularMarketPrice,
            logourl = source.logourl,
            usedRange = source.usedRange,
            historicalDataPrice = source.historicalDataPrice?.mapHistoricalDataPrice()
        )
    }

    private fun List<HistoricalDataPriceResponse>.mapHistoricalDataPrice(): List<HistoricalDataPrice> {
        return this.map {
            HistoricalDataPrice(
                date = it.date,
                open = it.open,
                close = it.close,
                high = it.high,
                low = it.low
            )
        }
    }
}
