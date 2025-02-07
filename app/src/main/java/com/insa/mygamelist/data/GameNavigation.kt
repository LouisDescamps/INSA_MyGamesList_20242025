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
    val navController = rememberNavController()
    NavHost(navController, startDestination = Profile) {
        composable<Profile> { GameList(navController) }
        composable<FriendsList> {
            //onClick=navController.navigate(FriendsList...)
            //GameDetail(navController,gameId)
        }
    }
}
