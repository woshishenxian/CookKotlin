package com.cook.kotlin.model

/**
 * Created by admin on 2017/12/17.
 */
data class TopicData(val next_update_date:String, val topics: ArrayList<Topic>,
                     val banner_image_url:String, val since:Int)