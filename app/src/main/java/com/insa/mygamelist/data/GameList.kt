package com.insa.mygamelist.data

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch
import androidx.compose.ui.window.Dialog


var research : String = ""



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameList(navController: NavHostController){                 //Page principale
    var isRefreshing by remember { mutableStateOf(false) }
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isRefreshing)

    var isDialogVisible by remember { mutableStateOf(false) }

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    var query by remember { mutableStateOf(research) }
    var isSearchBarActive by remember { mutableStateOf(false) }
    val filterGames = IGDB.games.filter { game ->  //Filtre pour la recherche selon les attendus du TP
        game.name.contains(query, ignoreCase = true) ||
        (game.genres.mapNotNull { genreId -> IGDB.genres.find { it.id == genreId }?.name }).any { genre -> genre.contains(query, ignoreCase = true) } ||
        (game.platforms.mapNotNull { platformId -> IGDB.platforms.find { it.id == platformId }?.name }).any { genre -> genre.contains(query, ignoreCase = true) }
    }

    DeletedManagement.displayedGames.clear()  //On affiche seulement les jeux qui ne sont pas dans la liste des supprimés
    filterGames.map { game -> DeletedManagement.displayedGames.add(game) }
    DeletedManagement.deletedGames.map { game -> if(DeletedManagement.displayedGames.contains(game)) DeletedManagement.displayedGames.remove(game) }


    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = Color.Magenta,
                    titleContentColor = Color.Black,
                ),
                title = { Text("My Games List") },
                actions = {
                    IconButton(onClick = { navController.navigate(Profile) }) {
                        Icon(Icons.Default.AccountCircle, contentDescription = "Profil Personnel")
                    }
                }
            )
        },
        floatingActionButton = {   // Boutton pour remonter en haut
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
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        TextField(              //Barre de recherche
                            value = query,
                            onValueChange = {
                                query = it
                                research =  it}, // Met à jour la recherche
                            label = { Text("Rechercher un jeu") },
                            modifier = Modifier
                                .weight(1f)
                        )

                        IconButton(
                            onClick = {
                                research = query // Met à jour la variable globale

                                val filteredGames = IGDB.games.filter { game ->
                                    game.name.contains(research, ignoreCase = true) ||
                                            (game.genres.mapNotNull { genreId -> IGDB.genres.find { it.id == genreId }?.name }).any { genre -> genre.contains(research, ignoreCase = true) } ||
                                            (game.platforms.mapNotNull { platformId -> IGDB.platforms.find { it.id == platformId }?.name }).any { platform -> platform.contains(research, ignoreCase = true) }
                                }

                                if (filteredGames.isEmpty()) {
                                    navController.navigate(NoResearch)  //Redirection vers la page si aucun jeu n'est trouvé avec la recherche
                                    research = ""
                                    query = ""
                                } else {
                                    DeletedManagement.displayedGames.clear()
                                    DeletedManagement.displayedGames.addAll(filteredGames)
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Rechercher",
                                tint = Color.Magenta // Couleur de l’icône
                            )
                        }
                    }

                },
                expanded = isSearchBarActive, // Indique si la SearchBar est ouverte
                onExpandedChange = { isSearchBarActive = it },
                content = { Text("Tapez pour rechercher un jeu...") },
                windowInsets = WindowInsets(0),
                modifier = Modifier
                    .fillMaxWidth()
            )

            if (query.isEmpty() && DeletedManagement.displayedGames.isEmpty()){   //cas où on a tout supprimé
                SwipeRefresh(
                    state = swipeRefreshState,
                    onRefresh = {
                        isRefreshing = true
                        isDialogVisible = true
                        isRefreshing = false
                    },
                    modifier = Modifier.fillMaxSize()
                ){
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillParentMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "All games have been deleted, try to refresh !",
                                    color = Color.Black,
                                )
                            }
                        }
                    }
                }
            }
            else{
                SwipeRefresh(   //Refresh pour afficher la popup pour reload les jeux supprimes (ne le sont plus donc ensuite)
                    state = swipeRefreshState,
                    onRefresh = {
                        isRefreshing = true
                        isDialogVisible = true
                        isRefreshing = false
                    },
                    modifier = Modifier.fillMaxSize()
                ){
                    LazyColumn(state = listState) {
                        items(DeletedManagement.displayedGames){
                            game -> GameItem(game){
                                navController.navigate(gameitem(game.id))
                            }
                        }
                    }
                }
            }
        }
    }
    if (isDialogVisible) {
        DisplayRefreshPopup(
            onDismiss = { isDialogVisible = false }
        )
    }
}

@Composable
fun DisplayRefreshPopup(onDismiss: () -> Unit){
    Dialog(
        onDismissRequest = onDismiss
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
                            DeletedManagement.reloadAll();  //on remet toutes les games qui avaient été supprimées
                            onDismiss() }
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Supprimer",
                        tint = Color.Magenta
                    )
                    Text("Reload all deleted games", color = Color.Black)
                }
            }
        }
    }
}