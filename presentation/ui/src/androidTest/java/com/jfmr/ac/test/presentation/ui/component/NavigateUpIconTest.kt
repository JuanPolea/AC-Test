package com.jfmr.ac.test.presentation.ui.component

import android.content.Context
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.printToLog
import androidx.test.platform.app.InstrumentationRegistry
import com.jfmr.ac.test.presentation.ui.InstrumentationTestUtils.pause
import com.jfmr.ac.test.presentation.ui.InstrumentationTestUtils.toast
import com.jfmr.ac.test.presentation.ui.R
import com.jfmr.ac.test.presentation.ui.main.theme.ACTestTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class NavigateUpIconTest {

    @get:Rule
    var composeTestRule = createComposeRule()

    private val context: Context = InstrumentationRegistry.getInstrumentation().context

    @Before
    fun setUp() {
        composeTestRule.setContent {
            ACTestTheme {
                NavigateUpIcon(onUpClick = {
                    context.toast("Navigate Up")
                })
            }
        }
    }

    @Test
    fun navigateUpIcon() {
        composeTestRule.waitForIdle()
        composeTestRule.onRoot().printToLog(NavigateUpIconTest::class.java.name)

        composeTestRule
            .onNodeWithContentDescription(context.getString(R.string.navigation_description))
            .assertExists()
            .assertHasClickAction()
            .performClick()
        pause()
    }
}
