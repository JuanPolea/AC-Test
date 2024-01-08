package com.jfmr.ac.test.presentation.ui.connectivity.model

sealed class InternetConnectivityState {
    data object Idle : InternetConnectivityState()
    data object Connected : InternetConnectivityState()
    data object Disconnected : InternetConnectivityState()
}
