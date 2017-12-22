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
import com.cook.kotlin.cookkotlin.BaseActivity
import com.cook.kotlin.cookkotlin.R
import com.cook.kotlin.cookkotlin.comic.adapter.ComicEpisodesAdapter
import com.cook.kotlin.db.model.RecentComic
import com.cook.kotlin.model.ComicData
import com.cook.kotlin.model.base.ObjCallBack
import com.cook.kotlin.utils.DBAsyncTask
import kotlinx.android.synthetic.main.activity_comic_list.*
import kotlinx.android.synthetic.main.toolbar_comic.*
import java.util.*

class ComicListActivity : BaseActivity() {

    companion object {
        fun startActivity(context: Context, no: Int) {
            val intent = Intent(context, ComicListActivity::class.java)
            intent.putExtra("topic_id", no)
            context.startActivity(intent)
        }
    }


    lateinit var mLayoutManager: LinearLayoutManager
    lateinit var mComicEpisodesAdapter: ComicEpisodesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_comic_list)

        mainTitle.alpha = 0f
        headImage.alpha = 0f
        mLayoutManager = LinearLayoutManager(this)
        mRecyclerView.layoutManager = mLayoutManager

        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            var change = false
            val duration = 750L
            val evaluator = IntEvaluator()
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                if (mLayoutManager.findFirstVisibleItemPosition() != 0
                        && !change) {
                    change = true
                    val animator = ObjectAnimator.ofInt(1, 100).setDuration(duration)
                    animator.addUpdateListener { toolbar.setBackgroundColor(Color.argb(evaluator.evaluate(it.animatedFraction, 0, 255), 48, 48, 48)) }
                    animator.start()
                    ObjectAnimator.ofFloat(mainTitle, "alpha", 0f, 1f).setDuration(duration).start()
                    ObjectAnimator.ofFloat(headImage, "alpha", 0f, 1f).setDuration(duration).start()
                } else if (mLayoutManager.findFirstVisibleItemPosition() == 0
                        && change) {
                    change = false
                    val animator = ObjectAnimator.ofInt(1, 100).setDuration(duration)
                    animator.addUpdateListener { toolbar.setBackgroundColor(Color.argb(evaluator.evaluate(it.animatedFraction, 255, 0), 48, 48, 48)) }
                    animator.start()
                    ObjectAnimator.ofFloat(mainTitle, "alpha", 1f, 0f).setDuration(duration).start()
                    ObjectAnimator.ofFloat(headImage, "alpha", 1f, 0f).setDuration(duration).start()
                }
            }
        })

        source.getComicEpisodes(no = intent.getIntExtra("topic_id", 0), objCallback = ComicEpisodesCallback())
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
            Glide.with(this@ComicListActivity).load(task.user.avatar_url).into(headImage)
            setTitle(task.title)
            DBAsyncTask(callback = RecentCallback()).execute(DBAsyncTask.SEARCH, intent.getIntExtra("topic_id", 0))
        }

        override fun onDataNotAvailable(msg: String?) {
            if (isFinishing)
                return
            Toast.makeText(this@ComicListActivity, msg, Toast.LENGTH_SHORT).show()
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

    inner class RecentCallback : DBAsyncTask.Callback {
        override fun onPostExecute(result: List<RecentComic>) {
            mComicEpisodesAdapter.notifyRecentDataChanged(if (result.isNotEmpty()) {
                result[0].episodeIds
            } else {
                Collections.emptyList()
            })
        }
    }
}
