package com.cook.kotlin.model.base

/**
 * Created by DE10035 on 2017/12/18.
 */
data class ComicResultWrapper<T>(val code: Int, val message: String, val data: T)