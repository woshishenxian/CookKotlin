package com.vincee.app.utils

import android.os.AsyncTask
import com.vincee.app.db.DBManager
import java.util.*

/**
 * Created by DE10035 on 2017/12/21.
 */
class DBAsyncTask(val callback: Callback? = null) : AsyncTask<Any, Int, List<com.vincee.app.db.model.RecentComic>>() {

    companion object {
        const val QUERY: Int = 1
        const val INSERT: Int = 2
        const val REMOVE: Int = 3
        const val CLEAR: Int = 4
        const val SEARCH: Int = 5
    }


    final override fun doInBackground(vararg params: Any): List<com.vincee.app.db.model.RecentComic> {
        try {
            val type = params[0]
            val param = if (params.size >= 2) {
                params[1]
            } else {
                com.vincee.app.db.model.RecentComic()
            }
            return when (type) {
                CLEAR -> {
                    DBManager.clearRecentComic()
                    Collections.emptyList<com.vincee.app.db.model.RecentComic>()
                }
                INSERT -> {
                    DBManager.insertRecentComic(param as com.vincee.app.db.model.RecentComic)
                    Collections.emptyList<com.vincee.app.db.model.RecentComic>()
                }
                REMOVE -> {
                    DBManager.removeRecentComic(param as Long)
                    Collections.emptyList<com.vincee.app.db.model.RecentComic>()
                }
                QUERY -> {
                    DBManager.queryRecentComicsByPageNo(param as Int)
                            ?: Collections.emptyList<com.vincee.app.db.model.RecentComic>()
                }
                SEARCH -> {
                    DBManager.searchRecentComicById(param as Int)
                            ?: Collections.emptyList<com.vincee.app.db.model.RecentComic>()
                }
                else -> Collections.emptyList<com.vincee.app.db.model.RecentComic>()
            }
        } catch (e: Exception) {
            return Collections.emptyList<com.vincee.app.db.model.RecentComic>()
        }
    }

    override fun onPostExecute(result: List<com.vincee.app.db.model.RecentComic>) {
        callback?.onPostExecute(result)
    }

    interface Callback {
        fun onPostExecute(result: List<com.vincee.app.db.model.RecentComic>)
    }
}