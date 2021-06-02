package com.wheelr.apiSetup

import com.appentusdemo.model.GetDataResponseBean
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface ApiClient {
    @GET("v2/list?")
    fun callApi(@QueryMap map: HashMap<String, String>): Call<List<GetDataResponseBean>>
}