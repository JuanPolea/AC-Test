package com.jfmr.ac.test.presentation.ui

import android.content.Context
import android.widget.Toast

object InstrumentationTestUtils {
    internal fun pause(timeInMillis: Long = 0) {
        Thread.sleep(timeInMillis)
    }

    internal fun Context.toast(string: String) = Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
}
