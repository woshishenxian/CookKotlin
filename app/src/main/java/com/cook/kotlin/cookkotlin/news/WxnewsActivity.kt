package com.cook.kotlin.cookkotlin.news

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.cook.kotlin.cookkotlin.BaseActivity
import com.cook.kotlin.cookkotlin.R
import com.cook.kotlin.cookkotlin.news.adapter.MainNewsListAdapter
import com.cook.kotlin.model.News
import com.cook.kotlin.model.base.ArrCallBack
import com.cook.kotlin.widget.ProgressBarHelper
import kotlinx.android.synthetic.main.activity_main.*

class WxnewsActivity : BaseActivity() {

    companion object {
        fun startActivity(context: Context) {
            val intent = Intent(context, WxnewsActivity::class.java)
            context.startActivity(intent)
        }
    }

    lateinit var mLayoutManager: LinearLayoutManager
    lateinit var mMainNewsListAdapter: MainNewsListAdapter

    private val newsList: ArrayList<News> = ArrayList<News>()
    private var pageNo = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setTitle(R.string.wxnews)

        mLayoutManager = LinearLayoutManager(this)
        mMainNewsListAdapter = MainNewsListAdapter(this, newsList)
        mRecyclerView.layoutManager = mLayoutManager
        mRecyclerView.adapter = mMainNewsListAdapter

        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                if (mLayoutManager.findLastCompletelyVisibleItemPosition() == mMainNewsListAdapter.itemCount - 4) {
                    pageNo++
                    requestNews()
                }
            }
        })

        requestNews()

    }

    private fun requestNews() {
        if (pageNo == 1) {
            newsList.clear()
        }
        source.getNewsByPageNo(pageNo, arrayCallback = NewsCallback())
    }

    override fun needNavigationIcon(): Boolean {
        return true
    }

    inner class NewsCallback : ArrCallBack<News> {

        override fun onTasksLoaded(tasks: List<News>) {
            if (isFinishing)
                return
            newsList.addAll(tasks)
            mMainNewsListAdapter.notifyDataSetChanged()
        }

        override fun onDataNotAvailable(msg: String?) {
            if (isFinishing)
                return
            Toast.makeText(this@WxnewsActivity, msg, Toast.LENGTH_SHORT).show()
        }

        override fun start() {
            ProgressBarHelper.show(this@WxnewsActivity)
        }

        override fun onComplete() {
            ProgressBarHelper.hide(this@WxnewsActivity)
        }
    }
}
