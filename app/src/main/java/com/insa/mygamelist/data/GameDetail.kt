package com.insa.mygamelist.data
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameDetail(navController: NavHostController, gameID: Long) {
    val games = IGDB.games
    var currentIndex by remember { mutableStateOf(games.indexOfFirst { it.id == gameID }) }
    if (currentIndex == -1) return
    val game = games[currentIndex]
    val previousIndex = if (currentIndex > 0) currentIndex - 1 else games.lastIndex
    val nextIndex = if (currentIndex < games.lastIndex) currentIndex + 1 else 0

    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(containerColor = Color.Magenta, titleContentColor = Color.Black),
                title = { Text(game.name) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { GameFavorite.toggleFavori(game.id) }) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Favori",
                            tint = if (GameFavorite.isFavori(game.id)) Color.Yellow else Color.Gray
                        )
                    }
                }
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        var offsetX by remember { mutableStateOf(0f) }
        var offsetY by remember { mutableStateOf(0f) }

        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = game.name,
                    modifier = Modifier.fillMaxWidth(),
                    fontWeight = FontWeight.Bold,
                    textDecoration = TextDecoration.Underline,
                    fontSize = 25.sp,
                    textAlign = TextAlign.Center,
                )
            }

            item {
                AsyncImage(
                    model = "https:" + getCoverUrl(game.cover),
                    contentDescription = "Game Cover",
                    modifier = Modifier.fillMaxWidth().size(250.dp),
                    contentScale = ContentScale.Fit,
                )
            }

            item {
                val genreNames = game.genres.mapNotNull { id -> IGDB.genres.find { it.id == id }?.name }
                Text(
                    text = genreNames.joinToString(", "),
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    fontStyle = FontStyle.Italic,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                )
            }

            // Liste horizontale des plateformes
            if (game.platforms.isNotEmpty()) {
                item {
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(game.platforms) { platformId ->
                            val platform = IGDB.platforms.find { it.id == platformId }
                            val logoUrl = IGDB.platform_logos.find { it.id == platform?.platform_logo }?.url
                            if (logoUrl != null) {
                                AsyncImage(
                                    model = "https:$logoUrl",
                                    contentDescription = platform?.name,
                                    modifier = Modifier.size(80.dp).padding(horizontal = 8.dp)
                                )
                            }
                        }
                    }
                }
            }

            item {
                Text(
                    text = game.summary,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            // Boutons "Précédent" et "Suivant"
            item {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(onClick = { currentIndex = previousIndex }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Précédent")
                    }

                    IconButton(onClick = { currentIndex = nextIndex }) {
                        Icon(Icons.Default.ArrowForward, contentDescription = "Suivant")
                    }
                }
            }
        }
    }
}
