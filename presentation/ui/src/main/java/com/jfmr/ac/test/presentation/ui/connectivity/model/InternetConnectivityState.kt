package com.jfmr.ac.test.presentation.ui.connectivity.model

sealed class InternetConnectivityState {
    object Idle : InternetConnectivityState()
    object Connected : InternetConnectivityState()
    object Disconnected : InternetConnectivityState()
}
