package com.cook.kotlin.cookkotlin.comic

import android.animation.IntEvaluator
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeTransition
import com.cook.kotlin.cookkotlin.BaseActivity
import com.cook.kotlin.cookkotlin.R
import com.cook.kotlin.cookkotlin.comic.adapter.ComicEpisodesAdapter
import com.cook.kotlin.db.model.RecentComic
import com.cook.kotlin.model.ComicData
import com.cook.kotlin.model.base.ObjCallBack
import com.cook.kotlin.utils.DBAsyncTask
import com.cook.kotlin.utils.glide.GlideApp
import com.cook.kotlin.widget.ProgressBarHelper
import kotlinx.android.synthetic.main.activity_comic_list.*
import kotlinx.android.synthetic.main.toolbar_comic.*
import java.util.*

class ComicListActivity : BaseActivity() {

    companion object {
        val requestCode = 110
        fun startActivity(context: Context, no: Int) {
            val intent = Intent(context, ComicListActivity::class.java)
            intent.putExtra("topic_id", no)
            context.startActivity(intent)
        }
    }


    var mLayoutManager: LinearLayoutManager? = null
    var mComicEpisodesAdapter: ComicEpisodesAdapter? = null
    private var topic_id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_comic_list)

        topic_id = intent.getIntExtra("topic_id", 0)
        mainTitle.alpha = 0f
        headImage.alpha = 0f
        mLayoutManager = LinearLayoutManager(this)
        mRecyclerView.layoutManager = mLayoutManager

        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            var change = false
            val duration = 750L
            val evaluator = IntEvaluator()
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                if (mLayoutManager?.findFirstVisibleItemPosition() != 0
                        && !change) {
                    change = true
                    val animator = ObjectAnimator.ofInt(1, 100).setDuration(duration)
                    animator.addUpdateListener { toolbar.setBackgroundColor(Color.argb(evaluator.evaluate(it.animatedFraction, 0, 255), 255, 152, 0)) }
                    animator.start()
                    ObjectAnimator.ofFloat(mainTitle, "alpha", 0f, 1f).setDuration(duration).start()
                    ObjectAnimator.ofFloat(headImage, "alpha", 0f, 1f).setDuration(duration).start()
                } else if (mLayoutManager?.findFirstVisibleItemPosition() == 0
                        && change) {
                    change = false
                    val animator = ObjectAnimator.ofInt(1, 100).setDuration(duration)
                    animator.addUpdateListener { toolbar.setBackgroundColor(Color.argb(evaluator.evaluate(it.animatedFraction, 255, 0), 255, 152, 0)) }
                    animator.start()
                    ObjectAnimator.ofFloat(mainTitle, "alpha", 1f, 0f).setDuration(duration).start()
                    ObjectAnimator.ofFloat(headImage, "alpha", 1f, 0f).setDuration(duration).start()
                }
            }
        })

        source.getComicEpisodes(no = intent.getIntExtra("topic_id", 0), objCallback = ComicEpisodesCallback())
    }

    private fun indexofList(episodeId: Int): Int {
        mComicEpisodesAdapter?.let {
            it.comicData.comics.forEachIndexed { index, comic ->
                if (comic.id == episodeId) {
                    return index + 2
                }
            }
        }
        return 0
    }


    override fun needNavigationIcon(): Boolean {
        return true
    }


    inner class ComicEpisodesCallback : ObjCallBack<ComicData> {

        override fun onTasksLoaded(task: ComicData) {
            if (isFinishing)
                return
            mComicEpisodesAdapter = ComicEpisodesAdapter(this@ComicListActivity, task)
            mRecyclerView.adapter = mComicEpisodesAdapter
            GlideApp.with(this@ComicListActivity).load(task.user.avatar_url).optionalCircleCrop().into(headImage)
            setTitle(task.title)
            DBAsyncTask(callback = RecentCallback()).execute(DBAsyncTask.SEARCH, topic_id)
        }

        override fun onDataNotAvailable(msg: String?) {
            if (isFinishing)
                return
            Toast.makeText(this@ComicListActivity, msg, Toast.LENGTH_SHORT).show()
        }

        override fun start() {
            ProgressBarHelper.show(this@ComicListActivity)
        }

        override fun onComplete() {
            ProgressBarHelper.hide(this@ComicListActivity)
        }
    }

    inner class RecentCallback : DBAsyncTask.Callback {
        override fun onPostExecute(result: List<RecentComic>) {
            mComicEpisodesAdapter?.notifyRecentDataChanged(if (result.isNotEmpty()) {
                result[0].episodeIds
            } else {
                Collections.emptyList()
            })
            mRecyclerView?.smoothScrollToPosition(indexofList(if (result.isNotEmpty()) {
                result[0].episodeId
            } else {
                0
            }))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == ComicListActivity.requestCode) {
            DBAsyncTask(callback = RecentCallback()).execute(DBAsyncTask.SEARCH, topic_id)
        }
    }
}
