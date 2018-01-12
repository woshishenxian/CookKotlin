package com.cook.kotlin.model

/**
 * Created by admin on 2017/12/17.
 */
data class ComicData(
        val discover_image_url:String,
        val vertical_image_url:String,
        val cover_image_url:String,
        val description:String,
        val created_at:Long,
        val title:String,
        val male_vertical_image_url:String,
        val updated_at:Long,
        val id:Int,
        val male_cover_image_url:String,
        val comics_count:Int,
        val user: Author,
        val topics: ArrayList<Comic>,
        val comics: ArrayList<Comic>,
        val images: ArrayList<String>,
        val topic: ComicData,
        val image_infos: ArrayList<ImageInfo>,
        val previous_comic_id:Int,
        val next_comic_id:Int,
        val comic_type:Int
)

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
        val episodeId:Int,
        val user: Author,
        var random:Int
)

data class ImageInfo(val width: Int, val height: Int)

data class Author(
        val pub_feed:String,
        val avatar_url:String,
        val grade:Int,
        val nickname:String,
        val reg_type:String,
        val id:String
)