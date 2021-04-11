package com.app.tictactoe.utility.extension

import androidx.lifecycle.ViewModel
import com.app.tictactoe.data.model.Player
import com.google.gson.Gson
import org.json.JSONObject

fun ViewModel.getPlayerObject(any: Any): Player {
    try {
        val data = any as JSONObject
        return Gson().fromJson(data.toString(), Player::class.java)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return Player()
}