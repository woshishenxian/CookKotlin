package com.cook.kotlin.api

import com.cook.kotlin.model.NewsCollection
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by DE10035 on 2017/12/14.
 */
interface DataService {

    @GET("wxnew/")
    fun getNews(@Query("page") pno: Int, @Query("num") num: Int = 20, @Query("key") key: String = "0e400e247c0b5197100478e44fbdb9ae")
            : Call<NewsCollection>

}