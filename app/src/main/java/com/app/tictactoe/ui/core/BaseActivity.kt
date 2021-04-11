package com.app.tictactoe.ui.core

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.app.tictactoe.R
import com.app.tictactoe.utility.dialog.CustomDialog

open class BaseActivity : AppCompatActivity() {

    lateinit var customDialog: CustomDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        customDialog = CustomDialog.create(this, false)
    }

    open fun showLoading() {
        customDialog.show()
    }

    open fun hideLoading() {
        customDialog.dismiss()
    }

    open fun onNetworkDisconnected() {
        startActivity(NoInternetActivity.createIntent(this))
    }

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun startActivityWithAnimation(intent: Intent) {
        startActivity(intent)
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left)
    }

    fun startActivityForResultWithAnimation(intent: Intent, requestCode: Int) {
        startActivityForResult(intent, requestCode)
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right)
    }
}