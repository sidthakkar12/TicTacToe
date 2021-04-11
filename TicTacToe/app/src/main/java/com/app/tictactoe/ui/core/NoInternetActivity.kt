package com.app.tictactoe.ui.core

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import com.app.tictactoe.R
import com.app.tictactoe.databinding.ActivityNoInternetBinding
import com.app.tictactoe.utility.extension.isNetworkAvailable
import com.app.tictactoe.utility.helper.ConnectionLiveData

class NoInternetActivity : BaseBindingActivity<ActivityNoInternetBinding>() {

    companion object {
        fun createIntent(context: Context): Intent {
            val intentX = Intent(context, NoInternetActivity::class.java)
            return intentX
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBindingView(R.layout.activity_no_internet)

        binding.txtTryAgain.setOnClickListener {
            if (isNetworkAvailable()) {
                onBackPressed()
            }
        }
        binding.txtExit.setOnClickListener {
            onBackPressed()
        }

        ConnectionLiveData(this).observe(this, Observer { isConnected ->
            if (isConnected) {
                finish()
            }
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}