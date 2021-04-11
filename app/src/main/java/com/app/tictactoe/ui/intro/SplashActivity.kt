package com.app.tictactoe.ui.intro

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.app.tictactoe.R
import com.app.tictactoe.databinding.ActivitySplashBinding
import com.app.tictactoe.ui.core.BaseBindingActivity
import com.app.tictactoe.ui.home.HomeActivity
import com.app.tictactoe.utility.helper.Constants

/*
* Introductory screen of the application
* */
class SplashActivity : BaseBindingActivity<ActivitySplashBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBindingView(R.layout.activity_splash)

        pauseAndMoveForward()
    }

    /*
    * Pauses the screen for Constants.SPLASH_DURATION duration
    * */
    private fun pauseAndMoveForward() {
        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            startActivityWithAnimation(HomeActivity.createNewTaskIntent(this))
        }, Constants.SPLASH_DURATION)
    }
}