package com.jfmr.ac.test.presentation.ui.main.component

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalContext
import com.jfmr.ac.test.presentation.broadcast.EventBusInterface

@Composable
fun SystemBroadcastReceiver(
    systemAction: String,
    sharedEventBus: EventBusInterface,
) {
    val context = LocalContext.current
    val currentOnSystemEvent by rememberUpdatedState(sharedEventBus)

    DisposableEffect(context, systemAction, sharedEventBus) {
        val intentFilter = IntentFilter(systemAction)
        val broadcast = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                intent?.let {
                    currentOnSystemEvent.produceIntent(it)
                }

            }
        }

        context.registerReceiver(broadcast, intentFilter)

        onDispose {
            context.unregisterReceiver(broadcast)
        }
    }
}
