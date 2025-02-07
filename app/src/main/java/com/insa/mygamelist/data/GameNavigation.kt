package com.insa.mygamelist.data

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable


@Serializable
object gamelist
@Serializable
data class gameitem(val id: Long)



@Composable
fun GameNavigation(navController: NavHostController) {

    NavHost(navController, startDestination = gamelist) {
        composable<gamelist> { GameList(navController) }

        composable<gameitem> { backStackEntry ->
            val gameID: gameitem = backStackEntry.toRoute()
            GameDetail(navController, gameID.id)
        }
    }
}

