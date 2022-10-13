package com.jfmr.ac.test.presentation.ui.main.component

import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.jfmr.ac.test.presentation.ui.R

@Preview
@Composable
fun MainAppBar() {
    SmallTopAppBar(
        title = { Text(text = stringResource(id = R.string.app_name)) },
    )
}
