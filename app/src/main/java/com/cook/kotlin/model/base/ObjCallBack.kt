package com.cook.kotlin.model.base

/**
 * Created by DE10035 on 2017/12/14.
 */
interface ObjCallBack<T> {
    fun onTasksLoaded(task: T)

    fun onDataNotAvailable(msg: String?)

    fun start()

    fun onComplete()
}