package com.insa.mygamelist.data
import androidx.compose.runtime.mutableStateListOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import android.content.Context

object GameFavorite {
    val favoris = mutableStateListOf<Long>()

    suspend fun initFavorites(context: Context) {
        val savedFavorites = FavoriteStorage.getFavorites(context)
        favoris.clear()
        favoris.addAll(savedFavorites.map { it.toLong() })
    }


    fun toggleFavori(gameId: Long, context: Context) {
        if (favoris.contains(gameId)) {
            favoris.remove(gameId)
        } else {
            favoris.add(gameId)
        }

        // On met à jour la liste de favoris dans le DataStore
        CoroutineScope(Dispatchers.IO).launch {
            FavoriteStorage.saveFavorites(context, favoris.map { it.toString() }.toSet())
        }
    }

    fun isFavori(gameId: Long): Boolean {
        return favoris.contains(gameId)
    }
}
