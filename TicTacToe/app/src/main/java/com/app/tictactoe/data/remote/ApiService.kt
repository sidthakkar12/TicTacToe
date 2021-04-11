package com.app.tictactoe.data.remote

import com.app.tictactoe.data.model.Home
import com.app.tictactoe.data.model.PlayedMove
import com.app.tictactoe.utility.helper.ApiConstants
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

    @FormUrlEncoded
    @POST(ApiConstants.PLAY)
    fun play(@FieldMap hashMap: HashMap<String, String>): Single<Home>

    @FormUrlEncoded
    @POST(ApiConstants.SUBMIT_MOVE)
    fun submitMove(@FieldMap hashMap: HashMap<String, String>): Single<PlayedMove>
}