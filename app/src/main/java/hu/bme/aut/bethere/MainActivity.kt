package hu.bme.aut.bethere

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.bethere.ui.screen.NavGraphs
import hu.bme.aut.bethere.ui.theme.BeThereTheme
import hu.bme.aut.bethere.ui.view.dialog.NoInternetDialog
import hu.bme.aut.bethere.ui.view.network.ConnectionState
import hu.bme.aut.bethere.ui.view.network.connectivityState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            delay(50)
            window.setBackgroundDrawableResource(android.R.color.transparent)
        }
        setContent {
            BeThereTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val connection by connectivityState()
                    val isConnected = connection === ConnectionState.Available
                    NoInternetDialog(isConnected = isConnected)
                    DestinationsNavHost(navGraph = NavGraphs.root)
                }
            }
        }
    }
}

