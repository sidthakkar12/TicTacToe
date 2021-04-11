package com.app.tictactoe.data.model

import com.google.gson.annotations.SerializedName

data class PlayedMove(
    @SerializedName("result") val isSuccess: Boolean = false,
    var clickedBlock: Int = 0
)