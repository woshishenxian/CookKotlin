package com.vincee.app.source

import com.vincee.app.model.Comic
import com.vincee.app.model.ComicData
import com.vincee.app.model.base.ComicResultWrapper
import com.vincee.core.api.RetrofitManager
import com.vincee.core.base.ArrCallBack
import com.vincee.core.base.ObjCallBack
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

/**
 * Created by DE10035 on 2017/12/14.
 */
class DataSource {
    private val service: com.vincee.app.api.DataService

    init {
        service = RetrofitManager.create(com.vincee.app.api.DataService::class.java)
    }

    fun getComics(type: Int, objCallback: ObjCallBack<ComicData>?) {
        objCallback?.start()
        service.getComics(type).enqueue(object : Callback<ComicResultWrapper<ComicData>> {
            override fun onFailure(call: Call<ComicResultWrapper<ComicData>>?, t: Throwable?) {
                objCallback?.onDataNotAvailable(t?.message)
                objCallback?.onComplete()
            }

            override fun onResponse(call: Call<ComicResultWrapper<ComicData>>, response: Response<ComicResultWrapper<ComicData>>) {
                if (response.isSuccessful) {
                    if (response.body()?.code == 200) {
                        response.body()?.data?.let {
                            objCallback?.onTasksLoaded(it)
                        }
                    } else {
                        objCallback?.onDataNotAvailable("请求错误")
                    }
                } else {
                    objCallback?.onDataNotAvailable("请求错误")
                }
                objCallback?.onComplete()
            }
        })
    }

    fun getComicEpisodes(no: Int, objCallback: ObjCallBack<ComicData>?) {
        objCallback?.start()
        service.getEpisodesById(no).enqueue(object : Callback<ComicResultWrapper<ComicData>> {
            override fun onFailure(call: Call<ComicResultWrapper<ComicData>>?, t: Throwable?) {
                objCallback?.onDataNotAvailable(t?.message)
                objCallback?.onComplete()
            }

            override fun onResponse(call: Call<ComicResultWrapper<ComicData>>, response: Response<ComicResultWrapper<ComicData>>) {
                if (response.isSuccessful) {
                    if (response.body()?.code == 200) {
                        response.body()?.data?.let {
                            objCallback?.onTasksLoaded(it)
                        }
                    } else {
                        objCallback?.onDataNotAvailable("请求错误2")
                    }
                } else {
                    objCallback?.onDataNotAvailable("请求错误1")
                }
                objCallback?.onComplete()
            }
        })
    }

    fun getComicById(no: Int, objCallback: ObjCallBack<ComicData>?) {
        if (no == 0) return
        objCallback?.start()
        service.getComicById(no).enqueue(object : Callback<ComicResultWrapper<ComicData>> {
            override fun onFailure(call: Call<ComicResultWrapper<ComicData>>?, t: Throwable?) {
                objCallback?.onDataNotAvailable(t?.message)
                objCallback?.onComplete()
            }

            override fun onResponse(call: Call<ComicResultWrapper<ComicData>>, response: Response<ComicResultWrapper<ComicData>>) {
                if (response.isSuccessful) {
                    if (response.body()?.code == 200) {
                        response.body()?.data?.let {
                            objCallback?.onTasksLoaded(it)
                        }
                    } else {
                        objCallback?.onDataNotAvailable("请求错误2")
                    }
                } else {
                    objCallback?.onDataNotAvailable("请求错误1")
                }
                objCallback?.onComplete()
            }
        })
    }

    fun getDailyComics(date: Int, arrayCallback: ArrCallBack<Comic>?) {
        arrayCallback?.start()
        service.getDailyComics(date).enqueue(object : Callback<ComicResultWrapper<ComicData>> {
            override fun onFailure(call: Call<ComicResultWrapper<ComicData>>?, t: Throwable?) {
                arrayCallback?.onDataNotAvailable(t?.message)
                arrayCallback?.onComplete()
            }

            override fun onResponse(call: Call<ComicResultWrapper<ComicData>>, response: Response<ComicResultWrapper<ComicData>>) {
                if (response.isSuccessful) {
                    if (response.body()?.code == 200) {
                        arrayCallback?.onTasksLoaded(response.body()?.data?.comics
                                ?: Collections.emptyList())
                    } else {
                        arrayCallback?.onDataNotAvailable("请求错误2")
                    }
                } else {
                    arrayCallback?.onDataNotAvailable("请求错误1")
                }
                arrayCallback?.onComplete()
            }
        })
    }
}

