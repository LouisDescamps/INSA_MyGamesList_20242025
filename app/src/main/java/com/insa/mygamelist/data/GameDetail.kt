package com.insa.mygamelist.data

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameDetail(navController: NavHostController, gameID: Long){
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
        }, modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
                Text(game?.name?:"Unknown game")

                // Affichage des logos des plateformes
                if (game?.platforms?.isNotEmpty() == true) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        game.platforms.forEach { platformId ->
                            val platform = IGDB.platforms.find { it.id == platformId }
                            val logoUrl = IGDB.platform_logos.find { it.id == platform?.platform_logo }?.url

                            if (logoUrl != null) {
                                AsyncImage(
                                    model = "https:$logoUrl",
                                    contentDescription = platform?.name,
                                    modifier = Modifier
                                        .size(80.dp)
                                        .padding(start = 8.dp, end = 8.dp)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Affichage du résumé
                Text(
                    text = game?.summary ?: "No summary available",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp)
                )


        }
    }

}