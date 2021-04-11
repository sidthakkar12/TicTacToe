package com.app.tictactoe.data.remote

import com.app.tictactoe.utility.helper.ApiConstants
import com.app.tictactoe.utility.helper.Constants
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {
    var retrofit: Retrofit? = null

    val retrofitService: Retrofit
        get() {
            if (retrofit == null) {
                retrofit = callRetrofit()
            }
            return retrofit!!
        }

    private fun callRetrofit(): Retrofit? {
        if (retrofit == null) {

            try {
                val logging = HttpLoggingInterceptor()
                logging.apply { logging.level = HttpLoggingInterceptor.Level.BODY }

                val builder = OkHttpClient.Builder()
                builder.addInterceptor(logging)

                builder.connectTimeout(Constants.API_TIMEOUT, TimeUnit.MINUTES)
                builder.readTimeout(Constants.API_TIMEOUT, TimeUnit.MINUTES)
                builder.writeTimeout(Constants.API_TIMEOUT, TimeUnit.MINUTES)

                retrofit = Retrofit.Builder()
                    .baseUrl(ApiConstants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(builder.build())
                    .build()

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return retrofit
    }
}