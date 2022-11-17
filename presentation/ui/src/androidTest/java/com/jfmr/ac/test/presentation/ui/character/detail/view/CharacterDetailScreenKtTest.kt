package com.jfmr.ac.test.presentation.ui.character.detail.view

import android.content.Context
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.printToLog
import androidx.test.platform.app.InstrumentationRegistry
import com.jfmr.ac.test.presentation.ui.InstrumentationTestUtils.pause
import com.jfmr.ac.test.presentation.ui.InstrumentationTestUtils.toast
import com.jfmr.ac.test.presentation.ui.R
import com.jfmr.ac.test.presentation.ui.character.detail.model.CharacterDetailError
import com.jfmr.ac.test.presentation.ui.character.detail.model.CharacterDetailUI
import com.jfmr.ac.test.presentation.ui.character.list.model.CharacterUI
import com.jfmr.ac.test.presentation.ui.character.list.model.LocationUI
import com.jfmr.ac.test.presentation.ui.character.list.model.OriginUI
import com.jfmr.ac.test.presentation.ui.component.ExpandableButtonTest
import com.jfmr.ac.test.presentation.ui.component.FavoriteButton
import com.jfmr.ac.test.presentation.ui.component.NavigateUpIcon
import com.jfmr.ac.test.presentation.ui.main.theme.ACTestTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class CharacterDetailScreenKtTest {

    @get:Rule
    var composeTestRule = createComposeRule()
    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    private val characterUI = CharacterUI(
        id = 1,
        image = "",
        gender = "Male",
        species = "Human",
        created = "",
        origin = OriginUI(name = "Earth", url = ""),
        name = "Rick Sánchez",
        LocationUI(name = "Earth", url = ""),
        episode = emptyList(),
        type = "Type",
        url = "",
        status = "Alive",
        isFavorite = false,
    )

    private val characterDetailUI = CharacterDetailUI(
        character = characterUI,
        episodes = emptyList(),
        isLoading = true,
        error = null,
    )

    private val chatacterDetailState = MutableStateFlow(characterDetailUI)

    @OptIn(ExperimentalMaterial3Api::class)
    @Before
    fun setUp() {
        composeTestRule.setContent {
            ACTestTheme {
                val character by chatacterDetailState.collectAsState()
                Scaffold(topBar = {
                    SmallTopAppBar(
                        title = {
                            Text(
                                text = stringResource(R.string.character_detail),
                                style = MaterialTheme.typography.titleLarge
                            )
                        },
                        navigationIcon = {
                            NavigateUpIcon { context.toast("Navigate Up") }
                        },
                        actions = {
                            FavoriteButton(
                                isFavorite = {
                                    character.character.isFavorite

                                },
                                action = {
                                    chatacterDetailState.update {
                                        characterDetailUI
                                            .copy(
                                                characterDetailUI.character
                                                    .copy(
                                                        isFavorite = !characterDetailUI.character.isFavorite
                                                    )
                                            )
                                    }
                                    context.toast("Fav button $it")
                                },
                            )
                        },
                        colors = TopAppBarDefaults.smallTopAppBarColors(
                            scrolledContainerColor = MaterialTheme.colorScheme.primary,
                            containerColor = MaterialTheme.colorScheme.background,
                        ),
                    )
                }) {
                    CharacterDetailContent(
                        characterDetail = { character },
                        action = {
                            context.toast("Retry clicked")
                        })
                }
            }
        }
    }


    @Test
    fun characterDetailContent() {
        with(composeTestRule) {
            waitForIdle()
            onRoot().printToLog(ExpandableButtonTest::class.java.name)

            onNodeWithContentDescription(context.getString(R.string.fav_description))
                .assertExists()
                .assertIsDisplayed()
                .assertHasClickAction()
                .performClick()


            onNodeWithContentDescription(context.getString(R.string.navigation_description))
                .assertExists()
                .assertIsDisplayed()
                .assertHasClickAction()
                .performClick()


            onNodeWithContentDescription(context.getString(R.string.progress_bar_description))
                .assertExists()

            pause()
            chatacterDetailState.update {
                characterDetailUI.copy(isLoading = false)
            }
            pause()
            onNodeWithContentDescription(context.getString(R.string.progress_bar_description))
                .assertDoesNotExist()

            chatacterDetailState.update {
                characterDetailUI.copy(isLoading = false, error = CharacterDetailError.CharacterNotFound)
            }

            pause()
            onNodeWithContentDescription(context.getString(R.string.image_detail_description))
                .assertDoesNotExist()

            onNodeWithText(context.getString(R.string.character_detail_not_found))
                .assertExists()

            onNodeWithText(context.getString(R.string.retry))
                .assertExists()
                .assertHasClickAction()
                .performClick()
            chatacterDetailState.update {
                characterDetailUI.copy(isLoading = false, error = null)
            }
            pause(2000)
            onNodeWithText(characterUI.name)
                .assertExists()
            onNodeWithContentDescription(context.getString(R.string.expand_Button_description))
                .assertExists()
                .assertHasClickAction()
                .performClick()
            chatacterDetailState.update {
                characterDetailUI.copy(character = characterUI.copy(id = -1))
            }
            pause(3000)
        }
    }
}
