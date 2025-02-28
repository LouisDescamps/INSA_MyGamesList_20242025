package com.insa.mygamelist.data
import androidx.compose.runtime.mutableStateListOf

object GameFavorite {
    val favoris = mutableStateListOf<Long>()

    fun toggleFavori(gameId: Long) {
        if (favoris.contains(gameId)) {
            favoris.remove(gameId)
        } else {
            favoris.add(gameId)
        }
    }

    fun isFavori(gameId: Long): Boolean {
        return favoris.contains(gameId)
    }
}
