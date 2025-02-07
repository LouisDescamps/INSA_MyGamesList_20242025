package com.insa.mygamelist.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.insa.mygamelist.R
import android.util.Log
object IGDB {

    lateinit var covers: List<Cover>
    lateinit var games: List<Game>
    lateinit var genres: List<Genre>
    lateinit var platform_logos: List<Platform_logos>
    lateinit var platforms: List<Platform>

    private inline fun <reified T> loadForAll(context: Context, ressourceId : Int): List<T>{
        return Gson().fromJson(
            context.resources.openRawResource(ressourceId).bufferedReader(),
            object : TypeToken<List<T>>() {}.type
        )
    }

    fun load(context: Context) {
        covers = loadForAll<Cover>(context, R.raw.covers);
        Log.d("IGDB","covers")
        games = loadForAll<Game>(context, R.raw.games);
        Log.d("IGDB","games")
        genres = loadForAll<Genre>(context, R.raw.genres);
        Log.d("IGDB","genres")
        platform_logos = loadForAll<Platform_logos>(context, R.raw.platform_logos);
        Log.d("IGDB","platforms_logos")
        platforms = loadForAll<Platform>(context, R.raw.platforms);
        Log.d("IGDB","platforms")
    }

}

data class Cover(val id: Long, val url: String)
data class Game(val id: Long, val cover: Long, val first_release_date : Long, val genres: List<Long>, val name: String, val platforms: List<Long>, val summary: String, val total_rating: Float)
data class Genre(val id: Long, val name: String)
data class Platform_logos(val id: Long, val url: String)
data class Platform(val id: Long, val name: String, val platform_logo : Long)