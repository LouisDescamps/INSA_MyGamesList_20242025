package com.insa.mygamelist.data

import androidx.compose.runtime.mutableStateListOf

object DeletedManagement {  //Gérer les jeux qui sont supprimés
    var deletedGames = mutableStateListOf<Game>()
    var displayedGames = mutableStateListOf<Game>()

    fun deleteGame(game: Game){
        displayedGames.remove(game)   //On l'enlève des jeux affichés

        deletedGames.add(game)     //On l'ajoute dans les jeux supprimés
    }

    fun reloadAll(){  //Lorsqu'on les reload, il n'y en a plus de supprimés et ils sont tous affichés
        deletedGames.map { game -> if(!displayedGames.contains(game)) displayedGames.add(game)}
        deletedGames.clear()
    }
}