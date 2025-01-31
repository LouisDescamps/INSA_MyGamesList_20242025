package com.insa.mygamelist.data



import androidx.compose.foundation.layout.*

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage


@Composable
fun GameItem(game: Game) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        AsyncImage(
            model = "https:"+getCoverUrl(game.cover),
            contentDescription = "Game Cover",
            modifier = Modifier
                .size(100.dp)
                .padding(8.dp)
        )

        Column(
            modifier = Modifier
                .padding(start = 8.dp)
                .align(Alignment.CenterVertically)
        ) {

            Text(
                text = game.name,
                style = MaterialTheme.typography.titleMedium,
                color = Color.Black
            )

            val genreNames = game.genres.mapNotNull { id -> IGDB.genres.find { it.id == id }?.name }
            Text(
                text = "Genres: ${genreNames.joinToString(", ")}",
                style = MaterialTheme.typography.bodySmall
            )


        }

    }
}


fun getCoverUrl(coverId: Long): String {
    val cover = IGDB.covers.find { it.id == coverId }
    return cover?.url ?: "//images.igdb.com/igdb/image/upload/t_cover_big/co8u3a.jpg"
}
