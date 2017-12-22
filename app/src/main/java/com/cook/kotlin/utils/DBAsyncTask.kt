package com.cook.kotlin.utils

import android.os.AsyncTask
import com.cook.kotlin.db.DBManager
import com.cook.kotlin.db.model.RecentComic
import java.util.*

/**
 * Created by DE10035 on 2017/12/21.
 */
class DBAsyncTask(val callback:Callback? = null) : AsyncTask<Any, Int, List<RecentComic>>() {

    companion object {
        val QUERY: Int = 1
        val INSERT: Int = 2
        val REMOVE: Int = 3
        val CLEAR: Int = 4
        val SEARCH: Int = 5
    }


    final override fun doInBackground(vararg params: Any): List<RecentComic> {
        val type = params[0]
        val param = if (params.size >= 2) {
            params[1]
        } else {
            RecentComic()
        }
        return when (type) {
            CLEAR -> {
                DBManager.clearRecentComic()
                Collections.emptyList<RecentComic>()
            }
            INSERT -> {
                DBManager.insertRecentComic(param as RecentComic)
                Collections.emptyList<RecentComic>()
            }
            REMOVE -> {
                DBManager.removeRecentComic(param as Long)
                Collections.emptyList<RecentComic>()
            }
            QUERY -> {
                DBManager.queryRecentComicsByPageNo(param as Int)?:Collections.emptyList<RecentComic>()
            }
            SEARCH -> {
                DBManager.searchRecentComicById(param as Int)?:Collections.emptyList<RecentComic>()
            }
            else -> Collections.emptyList<RecentComic>()
        }

    }

    override fun onPostExecute(result: List<RecentComic>) {
        callback?.onPostExecute(result)
    }

    interface Callback{
        fun onPostExecute(result: List<RecentComic>)
    }
}