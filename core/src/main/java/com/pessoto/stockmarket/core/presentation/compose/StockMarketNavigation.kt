package com.pessoto.stockmarket.core.presentation.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pessoto.stockmarket.core.presentation.navigation.LocalNavController
import com.pessoto.stockmarket.core.presentation.navigation.StockMarketNavScreen
import com.pessoto.stockmarket.core.presentation.theme.Typography

@Composable
fun StockMarketNavigation(modifier: Modifier) {

    val navController = rememberNavController()
    CompositionLocalProvider(LocalNavController provides navController) {
        NavHost(
            navController = navController,
            startDestination = StockMarketNavScreen.LIST_STOCKS.route,
        ) {
            composable(StockMarketNavScreen.LIST_STOCKS.route) {
                Box(
                    modifier = modifier.fillMaxSize()
                ) {
                    Text(
                        text = "Working in Progress",
                        style = Typography.titleLarge,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}
