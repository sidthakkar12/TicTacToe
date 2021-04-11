package com.app.tictactoe.ui.home

import android.annotation.SuppressLint
import com.app.tictactoe.data.model.Player
import com.app.tictactoe.data.remote.ApiClient
import com.app.tictactoe.data.remote.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

class HomeRepository {

    @SuppressLint("CheckResult")
    fun callPlayApi(param: HashMap<String, String>, onResult: (response: Player) -> Unit, onFailure: (message: String) -> Unit) {
        ApiClient.retrofitService.create(ApiService::class.java).play(param)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ genericResponseModel ->
                onResult(genericResponseModel.player)
            }, {
                onFailure(it.message!!)
                it.printStackTrace()
            })
    }
}