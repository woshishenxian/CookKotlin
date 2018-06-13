package com.cook.kotlin.cookkotlin.comic

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SnapHelper
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.cook.kotlin.cookkotlin.BaseActivity
import com.cook.kotlin.cookkotlin.R
import com.cook.kotlin.cookkotlin.comic.adapter.ComicAdapter
import com.cook.kotlin.cookkotlin.comic.adapter.OnItemClickCallBack
import com.cook.kotlin.cookkotlin.toast
import com.cook.kotlin.db.model.RecentComic
import com.cook.kotlin.model.ComicData
import com.cook.kotlin.model.base.ObjCallBack
import com.cook.kotlin.utils.DBAsyncTask
import com.cook.kotlin.utils.OmgSnapHelper
import com.cook.kotlin.utils.glide.GlideApp
import com.cook.kotlin.widget.ProgressBarHelper
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
            activity.startActivityForResult(intent, reqCode)
        }
    }

    private var comicData: ComicData? = null
    private val layoutManager = LinearLayoutManager(this)
    private var snapHelper: SnapHelper = OmgSnapHelper()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comic)

        toolbar.setBackgroundColor(Color.parseColor("#44000000"))

        layoutManager.orientation = LinearLayoutManager.VERTICAL
        mRecyclerView.layoutManager = layoutManager
        snapHelper.attachToRecyclerView(mRecyclerView)
        val animator = DefaultItemAnimator()
        animator.addDuration = 0
        animator.supportsChangeAnimations = false
        animator.changeDuration = 0
        mRecyclerView.itemAnimator = animator

        source.getComicById(intent.getIntExtra("comic_id", 0), objCallback = ComicCallback())

        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {

                if (layoutManager.findFirstCompletelyVisibleItemPosition() == 0) {
                    showNavigation()
                } else if (layoutManager.findLastCompletelyVisibleItemPosition() == recyclerView!!.adapter.itemCount - 1) {
                    showNavigation()
                } else {
                    hideNavigation()
                }
            }
        })
        comic_menu_next.setOnClickListener {
            source.getComicById(comicData?.next_comic_id ?: 0, objCallback = ComicCallback())
        }
        comic_menu_previous.setOnClickListener {
            source.getComicById(comicData?.previous_comic_id ?: 0, objCallback = ComicCallback())
        }
        headImage.setOnClickListener {
            if (comicData?.comic_type != 1) {
                toast("该漫画不支持横向滑动")
                return@setOnClickListener
            }
            if (layoutManager.orientation == LinearLayoutManager.VERTICAL) {
                layoutManager.orientation = LinearLayoutManager.HORIZONTAL
            } else {
                layoutManager.orientation = LinearLayoutManager.VERTICAL
            }
        }
    }

    private fun initComicMenu(task: ComicData) {
        comic_menu_next.isEnabled = task.next_comic_id != 0
        comic_menu_previous.isEnabled = task.previous_comic_id != 0
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
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
    }

    private fun showNavigation() {
        showToolbar()
        showMenu()
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
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

    override fun onCreateUpIntent(upIntent: Intent) {
        super.onCreateUpIntent(upIntent)
        upIntent.putExtra("topic_id", comicData?.topic?.id)
    }

    inner class ComicCallback : ObjCallBack<ComicData> {

        override fun onTasksLoaded(task: ComicData) {
            if (isFinishing)
                return
            comicData = task
            addRecentComic(task)
            setTitle(task.title)
            initComicMenu(task)
            GlideApp.with(this@ComicActivity).load(task.topic.user.avatar_url).circleCrop().into(headImage)
            val comicAdapter = ComicAdapter(this@ComicActivity, task)
            comicAdapter.callback = object : OnItemClickCallBack {
                var showing = true
                override fun onItemClick(position: Int, v: View) {
                    if (showing) {
                        hideNavigation()
                    } else {
                        showNavigation()
                    }
                    showing = !showing
                }
            }
            mRecyclerView.adapter = comicAdapter
        }

        override fun onDataNotAvailable(msg: String?) {
            if (isFinishing)
                return
            Toast.makeText(this@ComicActivity, msg, Toast.LENGTH_SHORT).show()
        }

        override fun start() {
            ProgressBarHelper.show(this@ComicActivity)
        }

        override fun onComplete() {
            ProgressBarHelper.hide(this@ComicActivity)
        }
    }
}
