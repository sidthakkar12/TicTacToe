package com.app.tictactoe.utility.extension

import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<T>.emitValue(value: T) {
    this.value = value
}