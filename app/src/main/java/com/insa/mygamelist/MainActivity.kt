package com.insa.mygamelist

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.remember
import androidx.fragment.app.FragmentActivity
import androidx.navigation.compose.rememberNavController
import com.insa.mygamelist.data.GameFavorite
import com.insa.mygamelist.data.GameNavigation
import com.insa.mygamelist.data.IGDB
import com.insa.mygamelist.data.ProfileName
import com.insa.mygamelist.data.authenticateUser
import com.insa.mygamelist.data.isBiometricAvailable
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (isBiometricAvailable(this)) {
            authenticateUser(
                context = this,
                onSuccess = { startApp() },
                onFailure = { finish() } // Ferme l'appli si refus
            )
        } else {
            startApp() // Démarre l'application directement si biométrie non dispo
        }
    }

    private fun startApp(){
        IGDB.load(this)

        MainScope().launch {
            GameFavorite.initFavorites(this@MainActivity)  //init des favoris enregistrés
            ProfileName.initProfileName(this@MainActivity)  //init du nom enregistré
        }

        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            GameNavigation(navController)

        }
    }
}