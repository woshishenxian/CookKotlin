package com.cook.kotlin.utils

import android.util.Log

/**
 * Created by DE10035 on 2017/12/14.
 */
object LogUtils {

    var debug = false

    fun i(tag: String, msg: String) {
        if (debug)
            Log.i(tag, msg)
    }

    fun d(tag: String, msg: String) {
        if (debug)
            Log.d(tag, msg)
    }

    fun e(tag: String, msg: String) {
        if (debug)
            Log.e(tag, msg)
    }

    fun w(tag: String, msg: String) {
        if (debug)
            Log.w(tag, msg)
    }

    fun v(tag: String, msg: String) {
        if (debug)
            Log.v(tag, msg)
    }

    fun wtf(tag: String, msg: String) {
        if (debug)
            Log.wtf(tag, msg)
    }
}