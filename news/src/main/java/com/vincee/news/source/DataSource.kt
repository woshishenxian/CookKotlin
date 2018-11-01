package com.vincee.news.source

import com.vincee.core.api.RetrofitManager
import com.vincee.core.base.ArrCallBack
import com.vincee.news.model.News
import com.vincee.news.model.NewsCollection
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

/**
 * Created by DE10035 on 2017/12/14.
 */
class DataSource {
    private val service: DataApi

    init {
        service = RetrofitManager.create(DataApi::class.java)
    }

    fun getNewsByPageNo(no: Int, arrayCallback: ArrCallBack<News>?) {
        arrayCallback?.start()
        service.getNews(no).enqueue(object : Callback<NewsCollection> {
            override fun onFailure(call: Call<NewsCollection>?, t: Throwable?) {
                arrayCallback?.onDataNotAvailable(t?.message)
                arrayCallback?.onComplete()
            }

            override fun onResponse(call: Call<NewsCollection>, response: Response<NewsCollection>) {
                if (response.isSuccessful) {
                    if (response.body()?.code == 200) {
                        arrayCallback?.onTasksLoaded(response.body()?.newslist
                                ?: Collections.emptyList())
                    } else {
                        arrayCallback?.onDataNotAvailable("请求错误")
                    }
                } else {
                    arrayCallback?.onDataNotAvailable("请求错误")
                }
                arrayCallback?.onComplete()
            }
        })
    }

}

/**
 * Created by DE10035 on 2017/12/14.
 */
interface DataApi {

    @GET("wxnew/")
    fun getNews(@Query("page") pno: Int
                , @Query("num") num: Int = 20
                , @Query("key") key: String = "0e400e247c0b5197100478e44fbdb9ae")
            : Call<NewsCollection>

}



