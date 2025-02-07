package com.insa.mygamelist.data

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameDetail(navController: NavHostController, gameID: Long){
    val game = IGDB.games.find { it.id==gameID }
    Scaffold(
        topBar = {
            TopAppBar(colors = topAppBarColors(
                containerColor = Color.Magenta,
                titleContentColor = Color.Black,
            ),
                title = { Text(game?.name?:"Unknown game") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                })
        }, modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Text(
                text = game?.name?:"Unknown game",
                modifier = Modifier
                    .fillMaxWidth(),
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline,
                fontSize = 25.sp,
                textAlign = TextAlign.Center,
            )

            Spacer(
                modifier = Modifier
                    .height(40.dp),
            )

            AsyncImage(
                model = "https:" + getCoverUrl(game?.cover),
                contentDescription = "Game Cover",
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally)
                    .size(250.dp),
                contentScale = ContentScale.Fit,
            )

            Spacer(
                modifier = Modifier
                    .height(5.dp),
            )

            val genreNames =
                game?.genres?.mapNotNull { id -> IGDB.genres.find { it.id == id }?.name }
            Text(
                text = "${genreNames?.joinToString(", ")}",
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1,
                fontStyle = FontStyle.Italic,
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
            )
        }
    }
}