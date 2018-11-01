package com.vincee.app.db

import com.vincee.app.db.model.RecentComic
import com.vincee.core.base.CoreApplication

/**
 * Created by DE10035 on 2017/12/21.
 */
object DBManager {
    internal val dbName: String = "kotlin_db"
    internal val mDaoMaster: com.vincee.app.db.DaoMaster
    internal val mDaoSession: com.vincee.app.db.DaoSession
    internal val pageSize = 10

    init {
        val mOpenHelper = com.vincee.app.db.DaoMaster.DevOpenHelper(CoreApplication.context, dbName, null)
        mDaoMaster = com.vincee.app.db.DaoMaster(mOpenHelper.writableDatabase)
        mDaoSession = mDaoMaster.newSession()
    }

    fun insertRecentComic(comic: com.vincee.app.db.model.RecentComic) {
        val recentComic = mDaoSession.recentComicDao.queryBuilder().where(com.vincee.app.db.RecentComicDao.Properties.TitleId.eq(comic.titleId)).unique()
        if (recentComic == null) {
            mDaoSession.recentComicDao.insert(comic)
        } else if (comic.episodeIds == null) {

        } else if (recentComic.episodeIds == null) {
            recentComic.episodeIds = comic.episodeIds
            recentComic.episodeId = comic.episodeId
            recentComic.episodeTitle = comic.episodeTitle
            mDaoSession.recentComicDao.insertOrReplace(recentComic)
        } else if (!recentComic.episodeIds.containsAll(comic.episodeIds)) {
            recentComic.episodeIds.addAll(comic.episodeIds)
            recentComic.episodeId = comic.episodeId
            recentComic.episodeTitle = comic.episodeTitle
            mDaoSession.recentComicDao.insertOrReplace(recentComic)
        }

    }

    fun searchRecentComicById(id: Int): List<com.vincee.app.db.model.RecentComic>? {
        return mDaoSession.recentComicDao.queryBuilder().where(com.vincee.app.db.RecentComicDao.Properties.TitleId.eq(id)).list()
    }

    fun queryRecentComicsByPageNo(no: Int): List<com.vincee.app.db.model.RecentComic>? {
        val queryBuilder = mDaoSession.recentComicDao.queryBuilder().orderDesc(com.vincee.app.db.RecentComicDao.Properties.Id).limit(pageSize)
        if (no <= 1) {
            return queryBuilder.list()
        } else {
            return queryBuilder.offset((no - 1) * pageSize).list()
        }
    }

    fun removeRecentComic(comicId: Long) {
       val recentComic = mDaoSession.recentComicDao.queryBuilder().where(com.vincee.app.db.RecentComicDao.Properties.Id.eq(comicId)).unique()
        mDaoSession.recentComicDao.delete(recentComic)
    }

    fun clearRecentComic() {
        mDaoSession.recentComicDao.deleteAll()
    }


}