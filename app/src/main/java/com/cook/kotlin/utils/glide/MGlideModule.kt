package com.cook.kotlin.utils.glide

import android.content.Context
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.integration.okhttp3.OkHttpGlideModule
import com.bumptech.glide.load.DecodeFormat

/**
 * Created by DE10035 on 2017/12/19.
 */
class MGlideModule : OkHttpGlideModule(){
    override fun applyOptions(context: Context?, builder: GlideBuilder?) {
        builder?.setDiskCache(ImageDiskLruCacheFactory(context))
        builder?.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888)
    }
}