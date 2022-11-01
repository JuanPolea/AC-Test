package com.jfmr.ac.test.presentation.ui.main

import android.net.ConnectivityManager
import android.os.Build
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.content.getSystemService
import androidx.hilt.navigation.compose.hiltViewModel
import com.jfmr.ac.test.presentation.ui.R
import com.jfmr.ac.test.presentation.ui.connectivity.model.InternetConnectivityState
import com.jfmr.ac.test.presentation.ui.connectivity.viewmodel.InternetConnectivityViewModel
import com.jfmr.ac.test.presentation.ui.main.theme.ACTestTheme
import com.jfmr.ac.test.presentation.ui.navigation.AppBottomNavigation
import com.jfmr.ac.test.presentation.ui.navigation.Navigation
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RickAndMortyApp(
    internetConnectivityViewModel: InternetConnectivityViewModel = hiltViewModel(),
) {
    val appState = rememberAppState()

    val internetState by internetConnectivityViewModel.internetConnectivityState.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val internetConnectionError = stringResource(id = R.string.connection_lost)
    val internetConnectionSuccess = stringResource(id = R.string.connection_established)

    val connectivityManager = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        LocalContext.current.getSystemService(ConnectivityManager::class
            .java) as
                ConnectivityManager
    } else {
        LocalContext.current.getSystemService()
    }
    connectivityManager?.requestNetwork(internetConnectivityViewModel.networkRequest, internetConnectivityViewModel.networkCallback)

    if (internetState is InternetConnectivityState.Disconnected) {
        ShowSnackBar(
            snackbarHostState = snackbarHostState,
            message = internetConnectionError
        )
    } else if (internetState == InternetConnectivityState.Connected) {
        ShowSnackBar(
            snackbarHostState = snackbarHostState,
            message = internetConnectionSuccess
        )
    }
    ThemeAndSurfaceWrapper {
        Scaffold(
            bottomBar = {
                AppBottomNavigation(
                    currentRoute = appState.currentRoute,
                    onNavItemClick = {
                        appState.onNavItemClick(it.navCommand.route)
                    }
                )
            },
            snackbarHost = {
                SnackbarHost(snackbarHostState) {
                    Snackbar(
                        snackbarData = it,
                    )
                }
            }
        ) { padding ->
            Box(modifier = Modifier.padding(padding)) {
                Navigation(appState)
            }
        }
    }
}

@Composable
private fun ShowSnackBar(
    snackbarHostState: SnackbarHostState,
    message: String,
) {
    val scope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        scope.launch {
            snackbarHostState.showSnackbar(
                message = message,
            )
        }
    }
}

@Composable
private fun ThemeAndSurfaceWrapper(content: @Composable () -> Unit) {
    ACTestTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            content()
        }
    }
}
