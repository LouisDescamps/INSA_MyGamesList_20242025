package com.insa.mygamelist.data
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.Dialog
import com.insa.mygamelist.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalProfile(navController: NavHostController) {
    var isDialogVisible by remember { mutableStateOf(false) }
    var username by remember { mutableStateOf("Unknown") }
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
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Image(
                painter = painterResource(id = R.drawable.profile),
                contentDescription = "Image sauvegardée",
                modifier = Modifier
                    .size(130.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Row(modifier = Modifier.align(Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically){
                Text(text = username, )
                IconButton(
                    onClick = { isDialogVisible=true; }
                ) {
                    Icon(
                        imageVector = Icons.Default.Create,
                        contentDescription = "UserName"
                        )
                }
            }



            Text(text = "Favorite Games")

            LazyColumn {
                items(GameFavorite.favoris) { game ->
                    GameItem(game = IGDB.games.find  { it.id == game }?:IGDB.games[0]  , onClick = {
                        navController.navigate(gameitem(game))
                    })
                }

            }


        }
    }
    if (isDialogVisible) {
        AlertDialog(
            onDismissRequest = { isDialogVisible = false },
            confirmButton = {
                TextButton(onClick = { isDialogVisible = false }) {
                    Text("OK")
                }
            },
            text = {
                TextField(
                    value = username,
                    onValueChange = { username = it },
                    singleLine = true
                )
            }
        )
    }
}
