package com.cook.kotlin.application

import android.app.Application
import android.content.Context
import com.cook.kotlin.utils.LogUtils

/**
 * Created by DE10035 on 2017/12/14.
 */
class App :Application(){

    companion object{
        var context:Context? =null
        private set
    }

    override fun onCreate() {
        super.onCreate()
        LogUtils.debug = true
        context = this
    }
}