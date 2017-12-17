package com.cook.kotlin.api

import com.cook.kotlin.model.ComicCollection
import com.cook.kotlin.model.TopicCollection
import com.cook.kotlin.model.NewsCollection
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by DE10035 on 2017/12/14.
 */
interface DataService {

    @GET("wxnew/")
    fun getNews(@Query("page") pno: Int, @Query("num") num: Int = 20, @Query("key") key: String = "0e400e247c0b5197100478e44fbdb9ae")
            : Call<NewsCollection>

    @GET("nba/")
    fun getNba(@Query("page") pno: Int, @Query("num") num: Int = 20, @Query("key") key: String = "0e400e247c0b5197100478e44fbdb9ae")
            : Call<NewsCollection>

    @GET("travel/")
    fun getTravel(@Query("page") pno: Int, @Query("num") num: Int = 20, @Query("key") key: String = "0e400e247c0b5197100478e44fbdb9ae")
            : Call<NewsCollection>

    @GET("huabian/")
    fun getHuabian(@Query("page") pno: Int, @Query("num") num: Int = 20, @Query("key") key: String = "0e400e247c0b5197100478e44fbdb9ae")
            : Call<NewsCollection>

    @GET("http://www.kuaikanmanhua.com/v2/topic/rank_list/3")
    fun getKuaikanComics(@Query("since") since: Int = 0, @Query("limit") limit: Int = 0)
            : Call<TopicCollection>

    @GET("http://api.kuaikanmanhua.com/v1/topic/{id}")
    fun getKuaikanComicById(@Path("id") id:String, @Query("sort") sort: Int = 0, @Query("sortAction") sortAction: Int = 0
                            , @Query("is_new_device") is_new_device: Boolean = false, @Query("is_homepage") is_homepage: Boolean = false
                            , @Query("page_source") page_source: Int = 20)
            : Call<ComicCollection>


}