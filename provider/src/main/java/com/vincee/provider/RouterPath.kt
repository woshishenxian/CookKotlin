package com.vincee.provider

object RouterPath {

    /** 壁纸模块*/
    class WallPager {
        /** 壁纸页面 */
        companion object {
            const val PATH_WALL_PAPER = "/wall/paper"
        }
    }

    /** 新闻模块*/
    class News {
        companion object {
            /** 新闻List页面 */
            const val PATH = "/news/list"
            /** 新闻页面 */
            const val PATH_WEB = "/news/web"

            const val PARAM_TITLE = "Title"
            const val PARAM_URL = "Url"
        }
    }
}