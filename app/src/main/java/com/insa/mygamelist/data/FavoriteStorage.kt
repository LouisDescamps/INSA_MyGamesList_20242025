package com.insa.mygamelist.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first


object FavoriteStorage {   //Stockage des jeux favoris pour la persistance
    private val Context.dataStore by preferencesDataStore(name = "favorites_prefs")
    private val FAVORITES_KEY = stringSetPreferencesKey("favorites")

    suspend fun saveFavorites(context: Context, favorites: Set<String>) {
        context.dataStore.edit { preferences ->
            preferences[FAVORITES_KEY] = favorites
        }
    }

    suspend fun getFavorites(context: Context): Set<String> {
        val preferences = context.dataStore.data.first()
        return preferences[FAVORITES_KEY] ?: emptySet()
    }

}