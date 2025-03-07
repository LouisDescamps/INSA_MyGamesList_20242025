package com.insa.mygamelist.data
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import androidx.compose.ui.platform.LocalContext



val gameRatings = mutableStateMapOf<Long, Float>()

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameDetail(navController: NavHostController, gameID: Long) {
    val games = IGDB.games
    var currentIndex by remember { mutableStateOf(games.indexOfFirst { it.id == gameID }) }
    if (currentIndex == -1) return
    val gameIndex = games[currentIndex]
    val previousIndex = if (currentIndex > 0) currentIndex - 1 else games.lastIndex
    val nextIndex = if (currentIndex < games.lastIndex) currentIndex + 1 else 0
    val context = LocalContext.current

    var showRatingDialog by remember { mutableStateOf(false) }
    var gameRating by remember { mutableStateOf(RatingStorage.getRating(context, gameID)) }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(containerColor = Color.Magenta, titleContentColor = Color.Black),
                title = { Text(gameIndex.name) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(
                        onClick = { GameFavorite.toggleFavori(gameID, context) }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Favori",
                            tint = if (GameFavorite.isFavori(gameID)) Color.Yellow else Color.Gray
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
                    text = gameIndex.name,
                    modifier = Modifier.fillMaxWidth(),
                    fontWeight = FontWeight.Bold,
                    textDecoration = TextDecoration.Underline,
                    fontSize = 25.sp,
                    textAlign = TextAlign.Center,
                )
            }

            item {
                AsyncImage(
                    model = "https:" + getCoverUrl(gameIndex.cover),
                    contentDescription = "Game Cover",
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(250.dp),
                    contentScale = ContentScale.Fit,
                )
            }

            item {
                val genreNames = gameIndex.genres.mapNotNull { id -> IGDB.genres.find { it.id == id }?.name }
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
            if (gameIndex.platforms.isNotEmpty()) {
                item {
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(gameIndex.platforms) { platformId ->
                            val platform = IGDB.platforms.find { it.id == platformId }
                            val logoUrl = IGDB.platform_logos.find { it.id == platform?.platform_logo }?.url
                            if (logoUrl != null) {
                                AsyncImage(
                                    model = "https:$logoUrl",
                                    contentDescription = platform?.name,
                                    modifier = Modifier
                                        .size(80.dp)
                                        .padding(horizontal = 8.dp)
                                )
                            }
                        }
                    }
                }
            }

            item {
                Text(
                    text = gameIndex.summary,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            // Boutons "Précédent" et "Suivant"
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
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
            item {
                val formattedRating = String.format("%.1f", gameRating)
                Button(
                    onClick = { showRatingDialog = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(text = "Give me a rating ($formattedRating/10)")
                }
            }
        }
    }

    // Boîte de dialogue pour la notation
    if (showRatingDialog) {
        AlertDialog(
            onDismissRequest = { showRatingDialog = false },
            confirmButton = {
                TextButton(onClick = { showRatingDialog = false }) {
                    Text("OK")
                }
            },
            title = { Text("Game rating") },
            text = {
                Column {
                    var sliderValue by remember { mutableStateOf(gameRating) }
                    Slider(
                        value = sliderValue,
                        onValueChange = { sliderValue = it },
                        valueRange = 0f..10f,
                        steps = 10
                    )
                    Text(text = "Rating: ${String.format("%.1f", sliderValue)}")
                    Button(onClick = {
                        gameRatings[gameID] = sliderValue
                        RatingStorage.saveRating(context, gameID, sliderValue)
                        showRatingDialog = false
                    }) {
                        Text("Save")
                    }
                }
            }
        )
    }
}