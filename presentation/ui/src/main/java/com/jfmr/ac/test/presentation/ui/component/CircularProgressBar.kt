package com.jfmr.ac.test.presentation.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.jfmr.ac.test.presentation.ui.R

@Composable
fun CircularProgressBar(message: String? = "") {
    val description = stringResource(id = R.string.progress_bar_description)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .semantics { contentDescription = description },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
        if (!message.isNullOrEmpty()) {
            Text(text = message, style = typography.bodyMedium)
        }
    }
}
