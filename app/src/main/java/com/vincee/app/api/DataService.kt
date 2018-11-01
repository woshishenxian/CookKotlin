package com.vincee.app.api

import com.vincee.app.model.ComicData
import com.vincee.app.model.base.ComicResultWrapper
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by DE10035 on 2017/12/14.
 */
interface DataService {

    @GET("http://www.kuaikanmanhua.com/v2/topic/rank_list/{type}")
    fun getComics(@Path("type") type: Int
                  , @Query("since") since: Int = 0
                  , @Query("limit") limit: Int = 0)
            : Call<ComicResultWrapper<ComicData>>

    @GET("http://api.kuaikanmanhua.com/v1/topics/{id}")
    fun getEpisodesById(@Path("id") id: Int
                        , @Query("sort") sort: Int = 0, @Query("sortAction") sortAction: Int = 0
                        , @Query("is_new_device") is_new_device: Boolean = false
                        , @Query("is_homepage") is_homepage: Boolean = false
                        , @Query("page_source") page_source: Int = 20)
            : Call<ComicResultWrapper<ComicData>>

    @GET("http://api.kuaikanmanhua.com/v2/comic/{id}")
    fun getComicById(@Path("id") id: Int
                     , @Header("User-Agent") user_agent: String = "Kuaikan/4.9.1/49100(Android;4.4.2;PE-TL10;kuaikan9;WIFI;1776*1080)"
                     , @Query("is_preview") is_preview: Int = 0)
            : Call<ComicResultWrapper<ComicData>>

    @GET("http://api.kuaikanmanhua.com/v1/daily/comic_lists/{date}")
    fun getDailyComics(@Path("date") date: Int
                       , @Header("User-Agent") user_agent: String = "Kuaikan/4.9.1/49100(Android;4.4.2;PE-TL10;kuaikan9;WIFI;1776*1080)"
                       , @Query("sa_event") saevent: String = "eyJhbmRyb2lkX2lkIjoiZTM5MjdjMGI0YWE1OTYyNSIsImFwcF9zZWNyZXRfc2lnbiI6IiIsImJkIjoidml2byIsImNhIjowLCJjdCI6MjAsImRldnQiOjEsImhlaWdodCI6MTkyMCwiaW1laSI6Ijg2MzMzMjAzMTgwNTIxNCIsIm1hYyI6IjE4OmUyOjlmOmExOmIwOjkzIiwibW9kZWwiOiJ2aXZvIFg3UGx1cyIsIm92IjoiNS4xLjEiLCJ3aWR0aCI6MTA4MH0="
                       , @Query("gender") gender: String = "0"
                       , @Query("since") since: String = "0")
            : Call<ComicResultWrapper<ComicData>>
}