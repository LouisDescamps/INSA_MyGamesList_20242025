package com.insa.mygamelist.data

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable
@Serializable
object Profile
@Serializable
object FriendsList


@Composable
fun GameNavigation(navController: NavHostController) {

    NavHost(navController, startDestination = "profile") {
        composable("profile") { GameList(navController) }

        composable("gameDetail/{gameID}") { backStackEntry ->
            val gameID = backStackEntry.arguments?.getString("gameID")?.toLongOrNull() ?: -1
            GameDetail(navController, gameID)
        }
    }
}

