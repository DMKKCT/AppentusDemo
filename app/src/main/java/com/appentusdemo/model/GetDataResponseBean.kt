package com.appentusdemo.model

import androidx.databinding.BaseObservable
import com.google.gson.annotations.SerializedName

class GetDataResponseBean : BaseObservable() {

    @SerializedName("id") val id : Int = 0
    @SerializedName("author") val author : String = ""
    @SerializedName("width") val width : Int = 0
    @SerializedName("height") val height : Int= 0
    @SerializedName("url") val url : String = ""
    @SerializedName("download_url") val download_url : String = ""
}