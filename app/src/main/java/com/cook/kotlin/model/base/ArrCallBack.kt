package com.cook.kotlin.model.base

/**
 * Created by DE10035 on 2017/12/14.
 */
interface ArrCallBack<T> {
    fun onTasksLoaded(tasks: List<T>)

    fun onDataNotAvailable(msg: String?)

    fun start()

    fun onComplete()
}