package com.app.tictactoe.ui.game

import android.annotation.SuppressLint
import com.app.tictactoe.data.model.PlayedMove
import com.app.tictactoe.data.remote.ApiClient
import com.app.tictactoe.data.remote.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

class GameRepository {

    @SuppressLint("CheckResult")
    fun callSubmitMove(param: HashMap<String, String>, onResult: (response: PlayedMove) -> Unit, onFailure: (message: String) -> Unit) {
        ApiClient.retrofitService.create(ApiService::class.java).submitMove(param)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ genericResponseModel ->
                onResult(genericResponseModel)
            }, {
                onFailure(it.message!!)
                it.printStackTrace()
            })
    }
}