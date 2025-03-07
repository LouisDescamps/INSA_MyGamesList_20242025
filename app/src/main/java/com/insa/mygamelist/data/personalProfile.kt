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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext

/*
@Composable
fun PersonalProfile(navController: NavHostController) {

    val user = remember { User(avatarUrl = "https://example.com/avatar.png", id = "User123", favoriteGames = IGDB.games) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Personal Profile") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            AsyncImage(
                model = user.avatarUrl,
                contentDescription = "Avatar",
                modifier = Modifier.size(100.dp).clip(CircleShape).align(Alignment.CenterHorizontally)
            )
            Text(text = "User ID: ${user.id}", style = MaterialTheme.typography.h6, modifier = Modifier.align(Alignment.CenterHorizontally))


            Text(text = "Favorite Games", style = MaterialTheme.typography.h6)
            LazyColumn {
                items(user.favoriteGames) { game ->
                    GameItem(game = game, onClick = {
                        navController.navigate("game_detail/${game.id}")
                    })
                }
            }
        }
    }
}

data class User(val avatarUrl: String, val id: String, val favoriteGames: List<Game>)
*/