package com.insa.mygamelist.data

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.ui.platform.LocalFocusManager
import kotlinx.coroutines.launch


var research : String = ""



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameList(navController: NavHostController){
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    var query by remember { mutableStateOf(research) }
    var isSearchBarActive by remember { mutableStateOf(false) }
    val filterGames = IGDB.games.filter { game ->
        game.name.contains(query, ignoreCase = true) ||
        (game.genres.mapNotNull { genreId -> IGDB.genres.find { it.id == genreId }?.name }).any { genre -> genre.contains(query, ignoreCase = true) } ||
        (game.platforms.mapNotNull { platformId -> IGDB.platforms.find { it.id == platformId }?.name }).any { genre -> genre.contains(query, ignoreCase = true) }
    }
    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = {
            TopAppBar(colors = topAppBarColors(
                containerColor = Color.Magenta,
                titleContentColor = Color.Black,
                ), title = { Text("My Games List") }
            )

        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    coroutineScope.launch { listState.animateScrollToItem(0) }
                },
                containerColor = Color.Magenta,
                contentColor = Color.White
            ) {
                Icon(Icons.Default.KeyboardArrowUp, contentDescription = "Remonter en haut")
            }
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            SearchBar(
                inputField = {
                    TextField(
                        value = query,
                        onValueChange = {
                            query = it
                            research =  it}, // Met à jour la recherche
                        label = { Text("Rechercher un jeu") },
                    )
                },
                expanded = isSearchBarActive, // Indique si la SearchBar est ouverte
                onExpandedChange = { isSearchBarActive = it },
                content = { Text("Tapez pour rechercher un jeu...") },
                windowInsets = WindowInsets(0),
                modifier = Modifier
                    .fillMaxWidth()
            )

            if (query.isNotEmpty() && filterGames.isEmpty()) {
                LaunchedEffect(query) {
                    focusManager.clearFocus()
                    navController.navigate(NoResearch)
                    research = ""
                    query = ""
                }
            }else{

                LazyColumn(state = listState) {
                    items(filterGames){
                        game -> GameItem(game){
                             navController.navigate(gameitem(game.id))
                        }
                    }
                }

            }
        }
    }
}