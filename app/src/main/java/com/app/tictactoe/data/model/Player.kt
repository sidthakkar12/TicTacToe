package com.app.tictactoe.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Player(
    @SerializedName("matchId") val matchId: String = "",
    @SerializedName("gameOver") val gameOver: Boolean = false,
    @SerializedName("turn") val turn: String = "",
    @SerializedName("isNewUser") val isNewUser: Boolean = false,
    @SerializedName("player") val player: String = "",
    @SerializedName("winner") val winner: String = "",
    @SerializedName("board") val board: List<List<String>> = arrayListOf(),
    @SerializedName("players") val players: Players = Players(),

    var playerId: String = ""
) : Parcelable {

    fun winner(): String {
        return if (winner == "null") "" else winner
    }
}