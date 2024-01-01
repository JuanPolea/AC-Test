package com.jfmr.ac.test.presentation.ui.connectivity.viewmodel

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jfmr.ac.test.presentation.ui.connectivity.model.InternetConnectivityState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class InternetConnectivityViewModel @Inject constructor() : ViewModel() {

    private val _internetConnectivityState =
        MutableStateFlow<InternetConnectivityState>(InternetConnectivityState.Idle)
    val internetConnectivityState = _internetConnectivityState
        .asStateFlow()
        .shareIn(viewModelScope, SharingStarted.Eagerly)

    private var isConnectionLost = false

    val networkCallback = object : ConnectivityManager.NetworkCallback() {

        override fun onAvailable(network: Network) {
            if (isConnectionLost) {
                _internetConnectivityState.update { InternetConnectivityState.Connected }
            }
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            isConnectionLost = true
            _internetConnectivityState.update { InternetConnectivityState.Disconnected }
        }
    }
    val networkRequest: NetworkRequest = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
        .build()
}
