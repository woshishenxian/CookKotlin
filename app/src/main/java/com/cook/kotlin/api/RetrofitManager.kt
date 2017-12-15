package com.cook.kotlin.api

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

/**
 * Created by DE10035 on 2017/12/13.
 */
object RetrofitManager {


    internal val retrofit: Retrofit


    init {
        retrofit = initRetrofit()
    }


    fun getRetrofit(): Retrofit {
        return retrofit
    }

    private fun initRetrofit(): Retrofit {

        val client = OkHttpClient.Builder()
                .build()


        val retrofit1 = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.tianapi.com").client(client).build()

        return retrofit1
    }


    fun <T> create(service: Class<T>): T {
        return retrofit.create(service)
    }
}