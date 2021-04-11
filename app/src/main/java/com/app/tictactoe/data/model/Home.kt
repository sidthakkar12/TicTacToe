package com.app.tictactoe.data.model

import com.google.gson.annotations.SerializedName

data class Home(
    @SerializedName("result") val player: Player
)