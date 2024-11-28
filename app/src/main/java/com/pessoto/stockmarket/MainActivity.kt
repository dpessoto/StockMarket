package com.pessoto.stockmarket

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.pessoto.stockmarket.core.presentation.compose.StockMarketNavigation
import com.pessoto.stockmarket.core.presentation.theme.StockMarketTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StockMarketTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    StockMarketNavigation(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}
