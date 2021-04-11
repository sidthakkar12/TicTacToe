package com.app.tictactoe.utility.helper

import com.app.tictactoe.BuildConfig

object LogUtils {
    private val ENABLE_LOG = BuildConfig.DEBUG
    private val TAG = "TAG"

    fun v(strTag: String = TAG, msg: String? = "", e: Throwable? = null) {
        if (ENABLE_LOG) {
            if (e == null) android.util.Log.v(strTag, msg?:"") else android.util.Log.v(strTag, msg?:"", e)
        }
    }

    fun d(strTag: String = TAG, msg: String? = "", e: Throwable? = null) {
        if (ENABLE_LOG) {
            if (e == null) android.util.Log.d(strTag, msg?:"") else android.util.Log.d(strTag, msg?:"", e)
        }
    }

    fun e(strTag: String = TAG, msg: String? = "", e: Throwable? = null) {
        if (ENABLE_LOG) {
            if (e == null) android.util.Log.e(strTag, msg?:"") else android.util.Log.e(strTag, msg?:"", e)
        }
    }

    fun w(strTag: String = TAG, msg: String? = "", e: Throwable? = null) {
        if (ENABLE_LOG) {
            if (e == null) android.util.Log.w(strTag, msg?:"") else android.util.Log.w(strTag, msg?:"", e)
        }
    }

    fun i(strTag: String = TAG, msg: String? = "", e: Throwable? = null) {
        if (ENABLE_LOG) {
            if (e == null) android.util.Log.i(strTag, msg?:"") else android.util.Log.i(strTag, msg?:"", e)
        }
    }
}