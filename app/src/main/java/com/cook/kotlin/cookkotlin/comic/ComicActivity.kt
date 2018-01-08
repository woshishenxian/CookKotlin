package com.cook.kotlin.cookkotlin.comic

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.cook.kotlin.cookkotlin.BaseActivity
import com.cook.kotlin.cookkotlin.R
import com.cook.kotlin.cookkotlin.comic.adapter.ComicAdapter
import com.cook.kotlin.db.model.RecentComic
import com.cook.kotlin.model.Comic
import com.cook.kotlin.model.ComicData
import com.cook.kotlin.model.base.ObjCallBack
import com.cook.kotlin.utils.DBAsyncTask
import com.cook.kotlin.utils.SimpleOnItemTouchjListener
import kotlinx.android.synthetic.main.activity_comic.*
import kotlinx.android.synthetic.main.activity_comic_menu.*
import kotlinx.android.synthetic.main.toolbar_comic.*

class ComicActivity : BaseActivity() {

    companion object {
        fun startActivity(context: Context, no: Int) {
            val intent = Intent(context, ComicActivity::class.java)
            intent.putExtra("comic_id", no)
            context.startActivity(intent)
        }

        fun startActivityForResult(activity: Activity, no: Int, reqCode: Int) {
            val intent = Intent(activity, ComicActivity::class.java)
            intent.putExtra("comic_id", no)
            activity.startActivityForResult(intent,reqCode)
        }
    }

    private var next_comic_id = 0
    private var previous_comic_id = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comic)

        toolbar.setBackgroundColor(Color.parseColor("#44000000"))

        mRecyclerView.layoutManager = LinearLayoutManager(this)
        val animator = DefaultItemAnimator()
        animator.addDuration = 0
        animator.supportsChangeAnimations = false
        animator.changeDuration = 0
        mRecyclerView.itemAnimator = animator

        source.getComicById(intent.getIntExtra("comic_id", 0), objCallback = ComicCallback())

        mRecyclerView.addOnItemTouchListener(object : SimpleOnItemTouchjListener(mRecyclerView) {
            override fun onItemClick(vh: RecyclerView.ViewHolder) {
                showNavigation()
            }
        })

        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            var moveY = 0

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                moveY += dy
                if (moveY > 5) {
                    hideNavigation()
                }
            }
        })
        comic_menu_next.setOnClickListener { source.getComicById(next_comic_id, objCallback = ComicCallback()) }
        comic_menu_previous.setOnClickListener { source.getComicById(previous_comic_id, objCallback = ComicCallback()) }
    }

    private fun initComicMenu(task: ComicData) {
        previous_comic_id = task.previous_comic_id
        next_comic_id = task.next_comic_id
        comic_menu_next.isEnabled = next_comic_id != 0
        comic_menu_previous.isEnabled = previous_comic_id != 0
    }

    private fun addRecentComic(comicData: ComicData) {
        val recentComic = RecentComic()
        recentComic.authorName = comicData.topic.user.nickname
        recentComic.titleId = comicData.topic.id
        recentComic.picUrl = comicData.topic.cover_image_url
        recentComic.title = comicData.topic.title
        recentComic.episodeIds = listOf(comicData.id)
        recentComic.episodeId = comicData.id
        recentComic.episodeTitle = comicData.title
        DBAsyncTask().execute(DBAsyncTask.INSERT, recentComic)
    }


    override fun needNavigationIcon(): Boolean {
        return true
    }

    private fun hideNavigation() {
        hideToolbar()
        hideMenu()
    }

    private fun showNavigation() {
        showToolbar()
        showMenu()
    }

    private fun hideMenu() {
        if (comic_menu.visibility == View.VISIBLE) {
            comic_menu.visibility = View.INVISIBLE
        }
    }

    private fun showMenu() {
        if (comic_menu.visibility != View.VISIBLE) {
            comic_menu.visibility = View.VISIBLE
        }
    }

    inner class ComicCallback : ObjCallBack<ComicData> {

        override fun onTasksLoaded(task: ComicData) {
            if (isFinishing)
                return
            addRecentComic(task)
            setTitle(task.title)
            initComicMenu(task)
            Glide.with(this@ComicActivity).load(task.topic.user.avatar_url).crossFade().into(headImage)
            mRecyclerView.adapter = ComicAdapter(this@ComicActivity, task)
        }

        override fun onDataNotAvailable(msg: String?) {
            if (isFinishing)
                return
            Toast.makeText(this@ComicActivity, msg, Toast.LENGTH_SHORT).show()
        }

        override fun start() {
            if (!progressDialog.isShowing)
                progressDialog.show()
        }

        override fun onComplete() {
            if (progressDialog.isShowing)
                progressDialog.dismiss()
        }
    }
}
