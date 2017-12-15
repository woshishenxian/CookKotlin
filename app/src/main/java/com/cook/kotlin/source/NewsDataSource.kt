package com.cook.kotlin.source

import com.cook.kotlin.api.DataService
import com.cook.kotlin.api.RetrofitManager
import com.cook.kotlin.model.News
import com.cook.kotlin.model.NewsCollection
import com.cook.kotlin.model.base.ArrCallBack
import com.cook.kotlin.utils.LogUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by DE10035 on 2017/12/14.
 */
class NewsDataSource {
    private val service: DataService

    init {
        service = RetrofitManager.create(DataService::class.java)
    }

    fun getNewsByPageNo(no: Int, arrayCallback: ArrCallBack<News>?) {
        service.getNews(no).enqueue(object : Callback<NewsCollection> {
            override fun onFailure(call: Call<NewsCollection>?, t: Throwable?) {
                arrayCallback?.onDataNotAvailable(t?.message)
                arrayCallback?.onComplete()
            }

            override fun onResponse(call: Call<NewsCollection>?, response: Response<NewsCollection>?) {
                response?.let {
                    if (response.isSuccessful){
                        if (response.body().code == 200) {
                            arrayCallback?.onTasksLoaded(response.body().newslist)
                        }else{
                            arrayCallback?.onDataNotAvailable("请求错误")
                        }
                    }else{
                        arrayCallback?.onDataNotAvailable("请求错误")
                    }
                    arrayCallback?.onComplete()
                }
            }
        })
    }
}

