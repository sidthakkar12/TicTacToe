package com.app.tictactoe.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.app.tictactoe.MainApplication
import com.app.tictactoe.R
import com.app.tictactoe.databinding.ActivityHomeBinding
import com.app.tictactoe.ui.core.BaseVMBindingActivity
import com.app.tictactoe.ui.game.GameActivity
import com.app.tictactoe.utility.extension.getDeviceId
import com.app.tictactoe.utility.extension.getPlayerObject
import com.app.tictactoe.utility.helper.ApiConstants
import com.app.tictactoe.utility.helper.Constants
import com.app.tictactoe.utility.helper.LogUtils

/*
* Main activity for the application.
* Handles connection and disconnection of Sockets.
* Allows user to start the game.
* */
class HomeActivity : BaseVMBindingActivity<ActivityHomeBinding, HomeViewModel>(HomeViewModel::class.java) {

    companion object {
        val TAG = HomeActivity::class.java.simpleName.toString()

        fun createNewTaskIntent(context: Context): Intent {
            val intentX = Intent(context, HomeActivity::class.java)
            intentX.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            return intentX
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBindingView(R.layout.activity_home)

        initSocket()
        setListeners()
    }

    /*
    * Initializes ApiConstants.SOCKET_JOIN socket.
    * */
    private fun initSocket() {
        viewModel.socket.on(ApiConstants.SOCKET_JOIN) {
            val player = viewModel.getPlayerObject(it[0])
            player.playerId = if (player.players.X == getDeviceId()) Constants.PLAYER_X else Constants.PLAYER_O

            if (viewModel.isGameReady(player)) {
                runOnUiThread {
                    showToast(getString(R.string.match_found_message))
                }
                startActivityWithAnimation(GameActivity.createIntent(context = this, player = player))

            } else {
                if (player.players.X == getDeviceId()) {
                    runOnUiThread {
                        showToast(getString(R.string.match_not_found_message))
                    }
                }
            }

            LogUtils.e(TAG, "Socket -> ${ApiConstants.SOCKET_JOIN} -> $player")
        }
    }

    /*
    * Handles on/off/connection for socket.
    * */
    override fun onResume() {
        super.onResume()
        viewModel.socket.off()
        initSocket()
        viewModel.socket.connect()
    }

    /*
    * Sets listeners for views.
    * */
    private fun setListeners() {
        binding.btnPlay.setOnClickListener {
            viewModel.callPlayApi(getDeviceId())
        }
    }

    /*
    * Handles disconnection of socket.
    * */
    override fun onDestroy() {
        super.onDestroy()
        MainApplication.socket.disconnect()
    }
}