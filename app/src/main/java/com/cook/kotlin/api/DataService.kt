package com.cook.kotlin.api

import com.cook.kotlin.model.ComicData
import com.cook.kotlin.model.NewsCollection
import com.cook.kotlin.model.base.ComicResultWrapper
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by DE10035 on 2017/12/14.
 */
interface DataService {

    @GET("wxnew/")
    fun getNews(@Query("page") pno: Int, @Query("num") num: Int = 20, @Query("key") key: String = "0e400e247c0b5197100478e44fbdb9ae"): Call<NewsCollection>

    @GET("http://www.kuaikanmanhua.com/v2/topic/rank_list/{type}")
    fun getKuaikanComics(@Path("type") type: Int, @Query("since") since: Int = 0, @Query("limit") limit: Int = 0): Call<ComicResultWrapper<ComicData>>

    @GET("http://api.kuaikanmanhua.com/v1/topics/{id}")
    fun getKuaikanEpisodesById(@Path("id") id: Int, @Query("sort") sort: Int = 0, @Query("sortAction") sortAction: Int = 0
                               , @Query("is_new_device") is_new_device: Boolean = false, @Query("is_homepage") is_homepage: Boolean = false
                               , @Query("page_source") page_source: Int = 20): Call<ComicResultWrapper<ComicData>>

    @GET("http://api.kuaikanmanhua.com/v2/comic/{id}")
    fun getKuaikanComicById(@Path("id") id: Int
                            , @Header("User-Agent") user_agent: String = "Kuaikan/4.9.1/49100(Android;4.4.2;PE-TL10;kuaikan9;WIFI;1776*1080)"
                            , @Query("is_preview") is_preview: Int = 0): Call<ComicResultWrapper<ComicData>>
}