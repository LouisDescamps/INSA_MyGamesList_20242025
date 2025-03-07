package com.insa.mygamelist.data

import androidx.compose.runtime.mutableStateListOf

object DeletedManagement {
    var deletedGames = mutableStateListOf<Game>()
    var displayedGames = mutableStateListOf<Game>()

    fun deleteGame(game: Game){
        displayedGames.remove(game)   //On l'enlève des jeux affichés

        deletedGames.add(game)     //On l'ajoute dans les jeux supprimés
    }

    fun reloadAll(){
        deletedGames.map { game -> if(!displayedGames.contains(game)) displayedGames.add(game)}
        deletedGames.clear()
    }
}