package com.cook.kotlin.model

/**
 * Created by admin on 2017/12/17.
 */
data class Topic(val discover_image_url:String,
                 val vertical_image_url:String,
                 val cover_image_url:String,
                 val description:String,
                 val created_at:Long,
                 val title:String,
                 val male_vertical_image_url:String,
                 val updated_at:Long,
                 val id:Int,
                 val male_cover_image_url:String,
                 val order:Int,
                 val comics_count:Int,
                 val status:String,
                 val user: Author,
                 var random:Int)