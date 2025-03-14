package com.insa.mygamelist.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first


object NameStorage {   //Stockage du nom de profil pour la persistance
    private val Context.dataStore by preferencesDataStore(name = "profile_name")
    private val NAME_KEY = stringPreferencesKey("name")

    suspend fun saveName(context: Context, name: String) {
        context.dataStore.edit { preferences ->
            preferences[NAME_KEY] = name     //sauvegarde du nom sous forme de String avec la clé "NAME_KEY"
        }
    }

    suspend fun getName(context: Context): String {
        val preferences = context.dataStore.data.first()
        return preferences[NAME_KEY] ?: "No name set yet"
    }

}