package com.insa.mygamelist.data

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
@Serializable
object Home
@Serializable
data class gameitem(val id: Long)


@Composable
fun GameNavigation(navController: NavHostController) {

    NavHost(navController, startDestination = Home) {
        composable<Home> { GameList(navController) }

        composable<gameitem> { backStackEntry ->
            val item: gameitem = backStackEntry.toRoute()
            GameDetail(navController, item.id)
        }
    }
}

