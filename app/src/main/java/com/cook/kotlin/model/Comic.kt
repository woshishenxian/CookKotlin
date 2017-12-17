package com.cook.kotlin.model

/**
 * Created by admin on 2017/12/17.
 */
data class Comic (
        val can_view:Boolean,
        val cover_image_url:String,
        val storyboard_cnt:Int,
        val created_at:Int,
        val has_pay:Boolean,
        val title:String,
        val url:String,
        val updated_at:Int,
        val id:String,
        val topic_id:String,
        val status:String
)