package com.cook.kotlin.model

/**
 * Created by admin on 2017/12/17.
 */
data class Comic (
        val vertical_image_url:String,
        val cover_image_url:String,
        val description:String,
        val created_at:Long,
        val title:String,
        val url:String,
        val comics_count:Int,
        val updated_at:Long,
        val id:Int,
        val user: Author,
        var random:Int
)