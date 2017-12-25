package com.cook.kotlin.source

import com.cook.kotlin.api.DataService
import com.cook.kotlin.api.RetrofitManager
import com.cook.kotlin.model.ComicData
import com.cook.kotlin.model.News
import com.cook.kotlin.model.NewsCollection
import com.cook.kotlin.model.base.ArrCallBack
import com.cook.kotlin.model.base.ComicResultWrapper
import com.cook.kotlin.model.base.ObjCallBack
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by DE10035 on 2017/12/14.
 */
class DataSource {
    private val service: DataService

    init {
        service = RetrofitManager.create(DataService::class.java)
    }

    fun getNewsByPageNo(no: Int, arrayCallback: ArrCallBack<News>?) {
        arrayCallback?.start()
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

    fun getComics(type :Int,objCallback: ObjCallBack<ComicData>?) {
        objCallback?.start()
        service.getComics(type).enqueue(object : Callback<ComicResultWrapper<ComicData>> {
            override fun onFailure(call: Call<ComicResultWrapper<ComicData>>?, t: Throwable?) {
                objCallback?.onDataNotAvailable(t?.message)
                objCallback?.onComplete()
            }

            override fun onResponse(call: Call<ComicResultWrapper<ComicData>>?, response: Response<ComicResultWrapper<ComicData>>?) {
                response?.let {
                    if (response.isSuccessful){
                        if (response.body().code == 200) {
                            objCallback?.onTasksLoaded(response.body().data)
                        }else{
                            objCallback?.onDataNotAvailable("请求错误")
                        }
                    }else{
                        objCallback?.onDataNotAvailable("请求错误")
                    }
                    objCallback?.onComplete()
                }
            }
        })
    }

    fun getComicEpisodes(no:Int,objCallback: ObjCallBack<ComicData>?) {
        objCallback?.start()
        service.getEpisodesById(no).enqueue(object : Callback<ComicResultWrapper<ComicData>> {
            override fun onFailure(call: Call<ComicResultWrapper<ComicData>>?, t: Throwable?) {
                objCallback?.onDataNotAvailable(t?.message)
                objCallback?.onComplete()
            }

            override fun onResponse(call: Call<ComicResultWrapper<ComicData>>?, response: Response<ComicResultWrapper<ComicData>>?) {
                response?.let {
                    if (response.isSuccessful){
                        if (response.body().code == 200) {
                            objCallback?.onTasksLoaded(response.body().data)
                        }else{
                            objCallback?.onDataNotAvailable("请求错误2")
                        }
                    }else{
                        objCallback?.onDataNotAvailable("请求错误1")
                    }
                    objCallback?.onComplete()
                }
            }
        })
    }

    fun getComicById(no:Int,objCallback: ObjCallBack<ComicData>?) {
        objCallback?.start()
        service.getComicById(no).enqueue(object : Callback<ComicResultWrapper<ComicData>> {
            override fun onFailure(call: Call<ComicResultWrapper<ComicData>>?, t: Throwable?) {
                objCallback?.onDataNotAvailable(t?.message)
                objCallback?.onComplete()
            }

            override fun onResponse(call: Call<ComicResultWrapper<ComicData>>?, response: Response<ComicResultWrapper<ComicData>>?) {
                response?.let {
                    if (response.isSuccessful){
                        if (response.body().code == 200) {
                            objCallback?.onTasksLoaded(response.body().data)
                        }else{
                            objCallback?.onDataNotAvailable("请求错误2")
                        }
                    }else{
                        objCallback?.onDataNotAvailable("请求错误1")
                    }
                    objCallback?.onComplete()
                }
            }
        })
    }
}

