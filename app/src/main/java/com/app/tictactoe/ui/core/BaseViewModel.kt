package com.app.tictactoe.ui.core

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.tictactoe.MainApplication
import com.app.tictactoe.utility.helper.ConnectionLiveData

open class BaseViewModel : ViewModel() {

    val loadingLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val errorLiveData: MutableLiveData<String> = MutableLiveData()
    val networkLiveData = ConnectionLiveData(MainApplication.application.applicationContext)

    fun invalidateLoading(show: Boolean) {
        loadingLiveData.postValue(show)
    }
}