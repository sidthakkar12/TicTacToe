package com.app.tictactoe.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Players(
    @SerializedName("X") val X: String = "",
    @SerializedName("O") val O: String = ""
) : Parcelable