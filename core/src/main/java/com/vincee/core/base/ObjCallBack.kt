package com.vincee.core.base

/**
 * Created by DE10035 on 2017/12/14.
 */
interface ObjCallBack<T> {
    fun onTasksLoaded(task: T)

    fun onDataNotAvailable(msg: String?)

    fun start()

    fun onComplete()
}


interface ArrCallBack<T> {
    fun onTasksLoaded(tasks: List<T>)

    fun onDataNotAvailable(msg: String?)

    fun start()

    fun onComplete()
}