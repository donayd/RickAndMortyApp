package com.zara.rickandmorty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.zara.rickandmorty.ui.characterDetail.CharacterDetailScreen
import com.zara.rickandmorty.ui.characterList.CharacterListScreen
import com.zara.rickandmorty.ui.theme.RickAndMortyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RickAndMortyTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "character_list_screen") {
                    composable("character_list_screen") {
                        CharacterListScreen(navController = navController)
                    }
                    composable(
                        "character_detail_screen/{characterId}",
                        arguments = listOf(
                            navArgument("characterId") {
                                type = NavType.IntType
                            }
                        )
                    ) {
                        val characterId = remember {
                            it.arguments?.getInt("characterId")
                        }
                        CharacterDetailScreen(
                            id = characterId ?: 1,
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}