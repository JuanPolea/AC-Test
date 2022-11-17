package com.jfmr.ac.test.presentation.ui.component

import android.content.Context
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.printToLog
import androidx.test.platform.app.InstrumentationRegistry
import com.jfmr.ac.test.presentation.ui.InstrumentationTestUtils.toast
import com.jfmr.ac.test.presentation.ui.R
import com.jfmr.ac.test.presentation.ui.main.theme.ACTestTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class FavoriteIconTest {

    @get:Rule
    var composeTestRule = createComposeRule()
    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
    private var _isFavorite = MutableStateFlow(false)
        private set

    @Before
    fun setUp() {
        composeTestRule.setContent {


            ACTestTheme {
                val favoriteState by _isFavorite.collectAsState()
                FavoriteButton(
                    isFavorite = { favoriteState },
                    action = { value ->
                        _isFavorite.update {
                            value
                        }
                        context.toast("isFavorite = $value")
                    },
                )
            }
        }
    }

    @Test
    fun favoriteIcon() {
        with(composeTestRule) {
            waitForIdle()

            onRoot().printToLog(FavoriteIconTest::class.java.name)

            onNodeWithContentDescription(context.getString(R.string.fav_description))
                .assertExists()
                .assertIsDisplayed()
                .assertHasClickAction()
                .performClick()

            waitForIdle()
        }
    }
}