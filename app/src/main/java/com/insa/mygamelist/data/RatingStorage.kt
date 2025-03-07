package com.insa.mygamelist.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first



private val Context.dataStore by preferencesDataStore(name = "favorites_prefs")
private val FAVORITES_KEY = stringSetPreferencesKey("favorites")

private const val PREFS_NAME = "game_ratings_prefs"
private const val RATING_PREFIX = "rating_"

suspend fun saveFavorites(context: Context, favorites: Set<String>) {
    context.dataStore.edit { preferences ->
        preferences[FAVORITES_KEY] = favorites
    }
}

suspend fun getFavorites(context: Context): Set<String> {
    val preferences = context.dataStore.data.first()
    return preferences[FAVORITES_KEY] ?: emptySet()
}



fun saveRating(context: Context, gameId: Long, rating: Float) {
    val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    prefs.edit()
        .putFloat("$RATING_PREFIX$gameId", rating)
        .apply()
}

fun getRating(context: Context, gameId: Long): Float {
    val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    return prefs.getFloat("$RATING_PREFIX$gameId", 0f)  // 0 par défaut
}
