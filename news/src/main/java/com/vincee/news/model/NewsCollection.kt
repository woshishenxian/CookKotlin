package com.vincee.news.model

/**
 * Created by DE10035 on 2017/12/14.
 */
data class NewsCollection(val code: Int,val msg :String,val newslist :ArrayList<News>)

data class News(val ctime: String, val title: String, val description: String, val picUrl: String, val url: String)