package com.cook.kotlin.db

import com.cook.kotlin.application.App
import com.cook.kotlin.db.model.RecentComic
import java.util.*

/**
 * Created by DE10035 on 2017/12/21.
 */
object DBManager {
    internal val dbName: String = "kotlin_db"
    internal val mDaoMaster: DaoMaster
    internal val mDaoSession: DaoSession
    internal val pageSize = 10

    init {
        val mOpenHelper = DaoMaster.DevOpenHelper(App.context, dbName, null)
        mDaoMaster = DaoMaster(mOpenHelper.writableDatabase)
        mDaoSession = mDaoMaster.newSession()
    }

    fun insertRecentComic(comic: RecentComic) {
        val recentComic = mDaoSession.recentComicDao.queryBuilder().where(RecentComicDao.Properties.TitleId.eq(comic.titleId)).unique()
        if (recentComic == null) {
            mDaoSession.recentComicDao.insert(comic)
        } else if (comic.episodeIds == null) {

        } else if (recentComic.episodeIds == null) {
            recentComic.episodeIds = comic.episodeIds
            mDaoSession.recentComicDao.insertOrReplace(recentComic)
        } else if (!recentComic.episodeIds.containsAll(comic.episodeIds)) {
            recentComic.episodeIds.addAll(comic.episodeIds)
            mDaoSession.recentComicDao.insertOrReplace(recentComic)
        }

    }

    fun searchRecentComicById(id: Int): List<RecentComic>? {
        return mDaoSession.recentComicDao.queryBuilder().where(RecentComicDao.Properties.TitleId.eq(id)).list()
    }

    fun queryRecentComicsByPageNo(no: Int): List<RecentComic>? {
        val queryBuilder = mDaoSession.recentComicDao.queryBuilder().orderDesc(RecentComicDao.Properties.Id).limit(pageSize)
        if (no <= 1) {
            return queryBuilder.list()
        } else {
            return queryBuilder.offset((no - 1) * pageSize).list()
        }
    }

    fun removeRecentComic(comic: RecentComic) {
        mDaoSession.recentComicDao.delete(comic)
    }

    fun clearRecentComic() {
        mDaoSession.recentComicDao.deleteAll()
    }


}