package com.app.tictactoe.ui.game

import com.app.tictactoe.MainApplication
import com.app.tictactoe.data.model.PlayedMove
import com.app.tictactoe.data.model.Player
import com.app.tictactoe.ui.core.BaseViewModel
import com.app.tictactoe.utility.extension.emitValue
import com.app.tictactoe.utility.extension.getDeviceId
import com.app.tictactoe.utility.helper.ApiConstants
import com.app.tictactoe.utility.helper.Constants
import com.app.tictactoe.utility.helper.SingleLiveData

class GameViewModel : BaseViewModel() {

    private val gameRepository = GameRepository()

    val submittedMoveLiveData = SingleLiveData<Result<PlayedMove>>()

    var player = Player()
    var playerId = ""

    val socket = MainApplication.socket

    fun callSubmitMoveApi(clickedBlock: Int) {
        val params = HashMap<String, String>()
        params[ApiConstants.PARAM_PLAYER] = playerId
        params[ApiConstants.PARAM_ROW] = getRow(clickedBlock)
        params[ApiConstants.PARAM_COLUMN] = getColumn(clickedBlock)
        params[ApiConstants.PARAM_MATCH_ID] = player.matchId

        invalidateLoading(false)

        gameRepository.callSubmitMove(params, onResult = {
            if (it.isSuccess) {
                it.clickedBlock = clickedBlock
                submittedMoveLiveData.emitValue(Result.success(it))
            } else {
                errorLiveData.emitValue("Please wait for your turn..")
            }
            invalidateLoading(false)

        }, onFailure = {
            invalidateLoading(false)
        })
    }

    /*
    * Returns particular Row from board based on clickedBlock.
    * */
    private fun getRow(clickedBlock: Int): String {
        return when (clickedBlock) {
            1, 2, 3 -> "0"
            4, 5, 6 -> "1"
            else -> "2"
        }
    }

    /*
    * Returns particular Column from board based on clickedBlock.
    * */
    private fun getColumn(clickedBlock: Int): String {
        return when (clickedBlock) {
            1, 4, 7 -> "0"
            2, 5, 8 -> "1"
            else -> "2"
        }
    }

    /*
    * Checks if any of the players is winner and returns Boolean accordingly.
    * */
    fun checkIfAnyWinner(): Boolean {
        return player.winner().isNotEmpty()
    }

    /*
    * Checks if current player is winner and returns Boolean accordingly.
    * */
    fun checkIfCurrentPlayerWon(): Boolean {
        return if (player.players.X == MainApplication.application.applicationContext.getDeviceId()) {
            player.winner == Constants.PLAYER_X
        } else {
            player.winner != Constants.PLAYER_X
        }
    }

    /*
    * Checks if it's the turn of current player and returns Boolean accordingly.
    * */
    fun checkIsCurrentPlayerTurn(): Boolean {
        return if (player.players.X == MainApplication.application.applicationContext.getDeviceId()) {
            player.turn == Constants.PLAYER_X
        } else {
            player.turn != Constants.PLAYER_X
        }
    }
}