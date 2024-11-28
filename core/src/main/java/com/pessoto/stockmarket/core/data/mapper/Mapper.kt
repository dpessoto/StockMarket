package com.pessoto.stockmarket.core.data.mapper

interface Mapper<S, T> {
    fun map(source: S): T
}