package com.insa.mygamelist.data
import android.content.Context
import androidx.compose.runtime.mutableStateOf

object ProfileName {
    var profile_name = mutableStateOf("")

    suspend fun initProfileName(context: Context) {
        val temp_name = NameStorage.getName(context)

        profile_name.value = temp_name
    }
}
