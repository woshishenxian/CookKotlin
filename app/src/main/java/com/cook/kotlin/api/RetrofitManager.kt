package com.cook.kotlin.api

import com.cook.kotlin.utils.LogUtils
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


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
        //新建log拦截器
        val loggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message -> LogUtils.d("zcb", "OkHttp====Message:" + message) })
        //日志显示级别
        loggingInterceptor.level =  HttpLoggingInterceptor.Level.NONE

        val httpClientBuilder  = OkHttpClient.Builder()
        httpClientBuilder.addInterceptor(loggingInterceptor)

        val retrofit1 = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.tianapi.com").client(httpClientBuilder.build()).build()

        return retrofit1
    }


    fun <T> create(service: Class<T>): T {
        return retrofit.create(service)
    }
}