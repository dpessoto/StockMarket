package com.pessoto.stockmarket

import android.app.Application
import com.pessoto.stockmarket.core.data.di.coreModuleRemote
import com.pessoto.stockmarket.core.presentation.di.coreModulePresentation
import com.pessoto.stockmarket.feature.stockslist.di.moduleListPhotos
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class StockMarketApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@StockMarketApplication)
            modules(coreModuleRemote, coreModulePresentation, moduleListPhotos)
        }
    }
}
