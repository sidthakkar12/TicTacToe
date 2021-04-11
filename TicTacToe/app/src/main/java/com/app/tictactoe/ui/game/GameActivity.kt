package com.app.tictactoe.ui.game

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.app.tictactoe.R
import com.app.tictactoe.data.model.Player
import com.app.tictactoe.databinding.ActivityGameBinding
import com.app.tictactoe.ui.core.BaseVMBindingActivity
import com.app.tictactoe.utility.dialog.CustomDialog
import com.app.tictactoe.utility.extension.getPlayerObject
import com.app.tictactoe.utility.helper.ApiConstants
import com.app.tictactoe.utility.helper.Constants
import com.app.tictactoe.utility.helper.LogUtils
import nl.dionsegijn.konfetti.models.Shape
import nl.dionsegijn.konfetti.models.Size

/*
* Activity that allows players to play the game.
* Handles both players turn and updates the board accordingly.
* Sockets are managed.
* */
class GameActivity : BaseVMBindingActivity<ActivityGameBinding, GameViewModel>(GameViewModel::class.java) {

    companion object {
        val TAG = GameActivity::class.java.simpleName.toString()

        fun createIntent(context: Context, player: Player): Intent {
            val intentX = Intent(context, GameActivity::class.java)
            intentX.putExtra(Constants.INTENT_PLAYER, player)
            return intentX
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBindingView(R.layout.activity_game)

        initSocket()
        initComponents()
        initializeObservers()
        setListeners()
    }

    /*
    * Initializes ApiConstants.SOCKET_SUBMIT_MOVE socket.
    * */
    private fun initSocket() {
        viewModel.socket.on(ApiConstants.SOCKET_SUBMIT_MOVE) {
            viewModel.player = viewModel.getPlayerObject(it[0])
            updateBoard()

            LogUtils.e(TAG, "Socket -> ${ApiConstants.SOCKET_SUBMIT_MOVE} -> ${viewModel.player}")
        }
    }

    /*
    * Updates the board based on players' turns one after the other received from the socket.
    * */
    private fun updateBoard() {
        runOnUiThread {
            binding.tvTurn.text = getTurnOfText()

            viewModel.player.board.forEachIndexed { index, list ->
                when (index) {
                    0 -> {
                        binding.btnOne.text = list[0]
                        binding.btnTwo.text = list[1]
                        binding.btnThree.text = list[2]
                    }
                    1 -> {
                        binding.btnFour.text = list[0]
                        binding.btnFive.text = list[1]
                        binding.btnSix.text = list[2]
                    }
                    2 -> {
                        binding.btnSeven.text = list[0]
                        binding.btnEight.text = list[1]
                        binding.btnNine.text = list[2]
                    }
                }
            }

            showResult()
        }
    }

    /*
    * Checks if game is over, then shows result accordingly.
    * */
    private fun showResult() {
        if (viewModel.player.gameOver) {
            binding.tvTurn.text = ""
            binding.ivQuit.isVisible = false

            if (viewModel.checkIfAnyWinner() && viewModel.checkIfCurrentPlayerWon()) {
                showWinnerKonfetti()
            }

            CustomDialog.showResultDialog(context = this, message = getGameOverMessage(), actionText = getGameOverActionText(), callback = {
                onBackPressed()
            })
        }
    }

    /*
    * Shows konfetti to the winner.
    * */
    private fun showWinnerKonfetti() {
        binding.viewKonfetti.build()
            .addColors(ContextCompat.getColor(this, R.color.colorAccent), Color.GREEN, Color.YELLOW, Color.RED)
            .setDirection(0.0, 360.0)
            .setSpeed(1f, 3f)
            .setFadeOutEnabled(false)
            .setTimeToLive(100000L)
            .addShapes(Shape.Square, Shape.Circle)
            .addSizes(Size(12))
            .setAccelerationEnabled(true)
            .setPosition(-50f, binding.viewKonfetti.width + 50f, -50f, -50f)
            .streamFor(300, 5000L)
    }

    /*
    * Returns text to show for the particular players' turn.
    * */
    private fun getTurnOfText(): String {
        viewModel.playerId = if (viewModel.checkIsCurrentPlayerTurn()) viewModel.player.turn else viewModel.player.player

        return if (viewModel.checkIsCurrentPlayerTurn()) {
            getString(R.string.your_turn)
        } else {
            getString(R.string.opponents_turn)
        }
    }

    /*
    * Returns appropriate text for the game over message to show to the appropriate player.
    * */
    private fun getGameOverMessage(): String {
        return if (viewModel.checkIfAnyWinner()) {
            if (viewModel.checkIfCurrentPlayerWon()) {
                getString(R.string.winner_message)
            } else {
                getString(R.string.loser_message)
            }
        } else {
            getString(R.string.drawn_message)
        }
    }

    /*
    * Returns appropriate text for the game over message to show to the appropriate player.
    * */
    private fun getGameOverActionText(): String {
        return if (viewModel.checkIfAnyWinner()) {
            if (viewModel.checkIfCurrentPlayerWon()) {
                getString(R.string.play_again)
            } else {
                getString(R.string.try_again)
            }
        } else {
            getString(R.string.try_again)
        }
    }

    /*
    * Initializes components of the screen.
    * */
    private fun initComponents() {
        viewModel.player = intent.getParcelableExtra(Constants.INTENT_PLAYER)!!

        binding.tvTurn.text = getTurnOfText()
    }

    /*
    * Initializes observers of the screen.
    * */
    private fun initializeObservers() {
        viewModel.submittedMoveLiveData.observe(this, Observer {
            invalidateViews(it.getOrNull()!!.clickedBlock)
        })
    }

    /*
    * Invalidates the board based on players' turns one after the other received from the socket.
    * */
    private fun invalidateViews(clickedBlock: Int) {
        when (clickedBlock) {
            1 -> binding.btnOne.text = viewModel.player.player
            2 -> binding.btnTwo.text = viewModel.player.player
            3 -> binding.btnThree.text = viewModel.player.player
            4 -> binding.btnFour.text = viewModel.player.player
            5 -> binding.btnFive.text = viewModel.player.player
            6 -> binding.btnSix.text = viewModel.player.player
            7 -> binding.btnSeven.text = viewModel.player.player
            8 -> binding.btnEight.text = viewModel.player.player
            9 -> binding.btnNine.text = viewModel.player.player
        }
    }

    /*
    * Sets listeners for views.
    * */
    private fun setListeners() {
        binding.btnOne.setOnClickListener {
            viewModel.callSubmitMoveApi(clickedBlock = 1)
        }

        binding.btnTwo.setOnClickListener {
            viewModel.callSubmitMoveApi(clickedBlock = 2)
        }

        binding.btnThree.setOnClickListener {
            viewModel.callSubmitMoveApi(clickedBlock = 3)
        }

        binding.btnFour.setOnClickListener {
            viewModel.callSubmitMoveApi(clickedBlock = 4)
        }

        binding.btnFive.setOnClickListener {
            viewModel.callSubmitMoveApi(clickedBlock = 5)
        }

        binding.btnSix.setOnClickListener {
            viewModel.callSubmitMoveApi(clickedBlock = 6)
        }

        binding.btnSeven.setOnClickListener {
            viewModel.callSubmitMoveApi(clickedBlock = 7)
        }

        binding.btnEight.setOnClickListener {
            viewModel.callSubmitMoveApi(clickedBlock = 8)
        }

        binding.btnNine.setOnClickListener {
            viewModel.callSubmitMoveApi(clickedBlock = 9)
        }

        binding.ivQuit.setOnClickListener { onBackPressed() }
    }

    /*
    * Handles back press and quitting the game by the player.
    * */
    override fun onBackPressed() {
        if (viewModel.player.gameOver) {
            super.onBackPressed()
        } else {
            CustomDialog.showQuitGameDialog(this, callback = {
                super.onBackPressed()
            })
        }
    }
}