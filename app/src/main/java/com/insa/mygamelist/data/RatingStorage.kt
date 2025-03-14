package com.insa.mygamelist.data

import android.content.Context

object RatingStorage{  //Stockage des notes pour la persistance
    private const val PREFS_NAME = "game_ratings_prefs"
    private const val RATING_PREFIX = "rating_"


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

}

