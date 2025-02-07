package com.insa.mygamelist.data



import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage


@Composable
fun GameItem(game: Game,onClick:()->Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(14.dp)
            .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            AsyncImage(
                model = "https:" + getCoverUrl(game.cover),
                contentDescription = "Game Cover",
                modifier = Modifier
                    .size(100.dp)
                    .padding(8.dp)
            )

            Column(
                modifier = Modifier
                    .padding(start = 4.dp)
                    .align(Alignment.CenterVertically)
            ) {

                Text(
                    text = game.name,
                    style = TextStyle(fontWeight = FontWeight.Bold,  textDecoration = TextDecoration.Underline),
                    color = Color.Black

                )
                Spacer(modifier = Modifier.height(4.dp))
                val genreNames =
                    game.genres.mapNotNull { id -> IGDB.genres.find { it.id == id }?.name }
                Text(
                    text = "Genres: ${genreNames.joinToString(", ")}",
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )


            }

        }
    }
}



fun getCoverUrl(coverId: Long): String {
    val cover = IGDB.covers.find { it.id == coverId }
    return cover?.url ?: "//images.igdb.com/igdb/image/upload/t_cover_big/co8u3a.jpg"
}
