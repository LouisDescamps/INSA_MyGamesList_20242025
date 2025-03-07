package com.insa.mygamelist.data
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import androidx.compose.ui.window.Dialog


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GameItem(game : Game,onClick:()->Unit) {
    val context = LocalContext.current
    var isDialogVisible by remember { mutableStateOf(false) }

    val gameRating = gameRatings[game.id] ?: 0f
    val ratingText = if (gameRating == 0f) "Unrated" else String.format("%.1f", gameRating)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(14.dp)
            .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
            .combinedClickable(
                onClick = { onClick() },
                onLongClick = { isDialogVisible = true }
            )
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = ratingText,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )


            IconButton(
                onClick = { GameFavorite.toggleFavori(game.id, context) }
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Favori",
                    tint = if (GameFavorite.isFavori(game.id)) Color.Yellow else Color.Gray,
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
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

    // Affichage de la popup si appuie long sur un jeu
    if (isDialogVisible) {
        Dialog(
            onDismissRequest = { isDialogVisible = false }
        ) {
            Box(
                modifier = Modifier
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                        modifier = Modifier
                            .clickable {
                                DeletedManagement.deleteGame(game)
                                isDialogVisible = false
                            }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Supprimer",
                            tint = Color.Red
                        )
                        Text(
                            buildAnnotatedString {
                                append("Delete ")
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, textDecoration = TextDecoration.Underline)) {
                                    append(game.name)
                                }
                            },
                            color = Color.Black
                        )
                    }
                }

            }
        }
    }


}



fun getCoverUrl(coverId: Long?): String {
    val cover = IGDB.covers.find { it.id == coverId }
    return cover?.url ?: "//images.igdb.com/igdb/image/upload/t_cover_big/co8u3a.jpg"
}
