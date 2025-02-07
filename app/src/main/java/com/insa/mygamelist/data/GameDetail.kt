package com.insa.mygamelist.data

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
import androidx.compose.foundation.lazy.items


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameDetail(navController: NavHostController, gameID: Long) {
    val game = IGDB.games.find { it.id == gameID }
    Scaffold(
        topBar = {
            TopAppBar(colors = topAppBarColors(
                containerColor = Color.Magenta,
                titleContentColor = Color.Black,
            ), title = { Text(game?.name?:"Unknown game") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp) // Ajoute un espace vertical entre les éléments
        ) {
            item {
                Text(
                    text = game?.name ?: "Unknown game",
                    modifier = Modifier.fillMaxWidth(),
                    fontWeight = FontWeight.Bold,
                    textDecoration = TextDecoration.Underline,
                    fontSize = 25.sp,
                    textAlign = TextAlign.Center,
                )
            }

            item {
                AsyncImage(
                    model = "https:" + getCoverUrl(game?.cover),
                    contentDescription = "Game Cover",
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(250.dp),
                    contentScale = ContentScale.Fit,
                )
            }

            item {
                val genreNames =
                    game?.genres?.mapNotNull { id -> IGDB.genres.find { it.id == id }?.name }
                Text(
                    text = genreNames?.joinToString(", ") ?: "",
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    fontStyle = FontStyle.Italic,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                )
            }

            // Liste défilante horizontalement pour les plateformes
            if (game?.platforms?.isNotEmpty() == true) {
                item {
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(game.platforms) { platformId ->
                            val platform = IGDB.platforms.find { it.id == platformId }
                            val logoUrl =
                                IGDB.platform_logos.find { it.id == platform?.platform_logo }?.url

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
                    text = game?.summary ?: "No summary available",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    }
}