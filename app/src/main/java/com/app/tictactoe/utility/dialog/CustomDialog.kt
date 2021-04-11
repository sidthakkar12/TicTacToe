package com.app.tictactoe.utility.dialog

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import com.app.tictactoe.R
import com.app.tictactoe.databinding.LayoutAlertDialogBinding
import com.app.tictactoe.databinding.LayoutResultDialogBinding

class CustomDialog(context: Context, theme: Int) : Dialog(context, theme) {
    companion object {
        fun create(context: Context?, cancelable: Boolean): CustomDialog {
            val dialog = CustomDialog(context!!, R.style.ProgressHUD)
            dialog.setTitle("")
            dialog.setContentView(R.layout.layout_custom_dialog)
            dialog.setCancelable(cancelable)
            dialog.window?.attributes?.gravity = Gravity.CENTER
            val lp = dialog.window?.attributes
            lp?.dimAmount = 0.2f
            dialog.window?.attributes = lp
            return dialog
        }

        fun showQuitGameDialog(context: Context, callback: () -> Unit) {
            val dialog = Dialog(context, R.style.ThemeDialog)

            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

            val view = inflater.inflate(R.layout.layout_alert_dialog, null)
            val binding = DataBindingUtil.bind<LayoutAlertDialogBinding>(view)
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            dialog.setContentView(binding?.root!!)
            dialog.setCancelable(false)
            dialog.show()

            binding.tvTitle.text = context.getString(R.string.quit_game)
            binding.tvMessage.text = context.getString(R.string.quit_game_message)

            binding.tvYes.setOnClickListener {
                callback()
                dialog.dismiss()
            }

            binding.tvNo.setOnClickListener {
                dialog.dismiss()
            }
        }

        fun showResultDialog(context: Context, message: String, actionText: String, callback: () -> Unit) {
            val dialog = Dialog(context, R.style.ThemeDialog)

            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

            val view = inflater.inflate(R.layout.layout_result_dialog, null)
            val binding = DataBindingUtil.bind<LayoutResultDialogBinding>(view)
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            dialog.setContentView(binding?.root!!)
            dialog.setCancelable(false)
            dialog.show()

            binding.tvTitle.text = context.getString(R.string.result)
            binding.tvMessage.text = message
            binding.tvYes.text = actionText

            binding.tvYes.setOnClickListener {
                callback()
                dialog.dismiss()
            }
        }
    }
}