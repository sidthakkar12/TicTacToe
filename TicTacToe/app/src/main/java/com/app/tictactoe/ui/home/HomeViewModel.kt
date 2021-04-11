package com.app.tictactoe.ui.home

import com.app.tictactoe.MainApplication
import com.app.tictactoe.data.model.Player
import com.app.tictactoe.ui.core.BaseViewModel
import com.app.tictactoe.utility.extension.emitValue
import com.app.tictactoe.utility.helper.ApiConstants
import com.google.gson.Gson
import org.json.JSONObject

class HomeViewModel : BaseViewModel() {

    private val homeRepository = HomeRepository()

    val socket = MainApplication.socket

    fun callPlayApi(deviceId: String) {
        val params = HashMap<String, String>()
        params[ApiConstants.PARAM_PLAYER_ID] = deviceId

        invalidateLoading(true)

        homeRepository.callPlayApi(params, onResult = {
            invalidateLoading(false)

        }, onFailure = {
            errorLiveData.emitValue(it)
            invalidateLoading(false)
        })
    }

    /*
    * Checks if both players have joined and returns Boolean accordingly.
    * */
    fun isGameReady(player: Player): Boolean {
        return player.players.X.isNotEmpty() && player.players.O.isNotEmpty()
    }
}