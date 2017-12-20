package com.cook.kotlin.utils.glide

import android.content.Context
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory
import java.io.File

/**
 * Created by DE10035 on 2017/12/19.
 */
class ImageDiskLruCacheFactory
    : DiskLruCacheFactory {

    constructor(context: Context?, diskCacheSize: Int = ImageDiskLruCacheFactory.CACHE_SIZE)
            : super(CacheDirectoryGetter {
        getDirectory(context)
    }, diskCacheSize)


    companion object {
        val CACHE_SIZE = 80 * 1024 * 1024 // 80MB
        fun getDirectory(context: Context?): File? {
            context?.let {
                return File(context.cacheDir, DiskLruCacheFactory.DEFAULT_DISK_CACHE_DIR)
            }
            return null
        }
    }
}