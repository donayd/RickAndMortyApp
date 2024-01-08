package com.zara.rickandmorty

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.zara.rickandmorty.ui.characterList.CharacterListScreen
import com.zara.rickandmorty.ui.theme.RickAndMortyTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CharacterListInstrumentedTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun tittle_composable_component_test() {
        composeTestRule.activity.setContent {
            RickAndMortyTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "character_list_screen") {
                    composable("character_list_screen") {
                        CharacterListScreen(navController = navController)
                    }
                }
            }
        }
        composeTestRule.onNodeWithContentDescription("Rick and Morty").assertIsDisplayed()
    }

    @Test
    fun search_bar_composable_component_test() {
        composeTestRule.activity.setContent {
            RickAndMortyTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "character_list_screen") {
                    composable("character_list_screen") {
                        CharacterListScreen(navController = navController)
                    }
                }
            }
        }
        composeTestRule.onNode(
            hasText("Search character...")
        ).assertIsDisplayed()
    }

}