package com.wheelr.apiSetup;

import android.content.Context
import android.net.Uri
import android.util.Log
import com.appentusdemo.R
import com.appentusdemo.apiSetup.ResponseListener
import com.appentusdemo.apiSetup.ViewDialog
import com.appentusdemo.model.GetDataResponseBean
import com.wheelr.utils.AppUtil
import okhttp3.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory


import java.util.concurrent.TimeUnit

class RetrofitClient {

    companion object {
        private val TAG = RetrofitClient::class.java.simpleName

        private const val BASE_URL = "https://picsum.photos/"  //  demo

        private var retrofit: Retrofit? = null


        fun getClient(context: Context?): ApiClient {
            val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
            httpClient.addInterceptor { chain: Interceptor.Chain ->
                val original = chain.request()
                // Request customization: add request headers
                val requestBuilder = original.newBuilder()
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("api_type", "mobile")
                val request = requestBuilder.build()
                chain.proceed(request)
            }

            val client = httpClient.build()
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit!!.create(ApiClient::class.java)
        }

        fun callApi(
            context: Context,
            map: HashMap<String, String>,
            listener: ResponseListener,
        ) {
            if (!AppUtil.isNetworkConnected(context)) {
                AppUtil.errorAlert(context, context.getString(R.string.internet_connection_lost));
                return
            }

            getClient(context).callApi(
                map
            )
                .enqueue(object : Callback<List<GetDataResponseBean>> {
                    override fun onFailure(call: Call<List<GetDataResponseBean>>, t: Throwable) {
                        Log.e(TAG, "onFailure: " + t.printStackTrace())
                        listener.getError(t.printStackTrace().toString())
                    }

                    override fun onResponse(
                        call: Call<List<GetDataResponseBean>>,
                        response: Response<List<GetDataResponseBean>>
                    ) {
                        if (response.body() == null) return
                        listener.getData(response.body())
                    }
                })
        }
    }
}