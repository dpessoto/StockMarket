package com.pessoto.stockmarket.core.presentation.di

import android.content.Context
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.disk.DiskCache
import coil.memory.MemoryCache
import com.pessoto.stockmarket.core.presentation.di.CacheConfig.IMAGE_CACHE_DIR
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

internal object CacheConfig {
    const val IMAGE_CACHE_DIR = "image_cache_stock_market"
}

val coreModulePresentation = module {
    single {
        val context: Context = androidContext()
        ImageLoader.Builder(context)
            .components {
                add(SvgDecoder.Factory())
            }
            .memoryCache {
                MemoryCache.Builder(context)
                    .maxSizePercent(0.45)
                    .build()
            }
            .diskCache {
                DiskCache.Builder()
                    .directory(context.cacheDir.resolve(IMAGE_CACHE_DIR))
                    .maxSizePercent(0.10)
                    .build()
            }
            .build()
    }
}
