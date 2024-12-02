package com.pessoto.stockmarket.presentation.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pessoto.stockmarket.core.presentation.navigation.LocalNavController
import com.pessoto.stockmarket.core.presentation.navigation.StockMarketNavScreen
import com.pessoto.stockmarket.feature.stockdetail.presention.compose.StockDetailScreen
import com.pessoto.stockmarket.feature.stockslist.presentation.compose.StockListScreen

@Composable
internal fun StockMarketNavigation(modifier: Modifier) {

    val navController = rememberNavController()
    CompositionLocalProvider(LocalNavController provides navController) {
        NavHost(
            navController = navController,
            startDestination = StockMarketNavScreen.STOCK_LIST.route,
        ) {
            composable(StockMarketNavScreen.STOCK_LIST.route) {
                StockListScreen(modifier = modifier)
            }
            composable("${StockMarketNavScreen.STOCK_DETAIL.route}{ticker}") { navBackStackEntry ->
                val ticker = navBackStackEntry.arguments?.getString("ticker")
                ticker?.let {
                    StockDetailScreen(modifier = modifier, ticker = it)
                }
            }
        }
    }
}
