package com.cook.kotlin.model

/**
 * Created by admin on 2017/12/17.
 */
data class ComicData(
        val discover_image_url:String,
        val vertical_image_url:String,
        val cover_image_url:String,
        val description:String,
        val created_at:Int,
        val title:String,
        val male_vertical_image_url:String,
        val updated_at:String,
        val id:Int,
        val category:String,
        val label_id:String,
        val male_cover_image_url:String,
        val order:Int,
        val comics_count:Int,
        val status:String,
        val user: Author,
        val comics: ArrayList<Comic>
)