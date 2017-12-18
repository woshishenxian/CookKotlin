package com.cook.kotlin.cookkotlin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.support.v7.widget.StaggeredGridLayoutManager.VERTICAL
import android.widget.Toast
import com.cook.kotlin.cookkotlin.adapter.MainComicGridAdapter
import com.cook.kotlin.cookkotlin.adapter.MainNewsListAdapter
import com.cook.kotlin.model.ComicData
import com.cook.kotlin.model.News
import com.cook.kotlin.model.base.ArrCallBack
import com.cook.kotlin.model.base.ObjCallBack
import com.cook.kotlin.source.DataSource
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fab_menu.*

class MainActivity : BaseActivity(){

    companion object{
        fun startActivity(context: Context){
            context.startActivity(Intent(context,MainActivity::class.java))
        }
    }

    lateinit var mLayoutManager: LinearLayoutManager
    lateinit var mMainNewsListAdapter: MainNewsListAdapter


    val newsList :ArrayList<News> = ArrayList<News>()
    private var pageNo = 1

    private var curTheme: Int = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mLayoutManager = LinearLayoutManager(this)
        mMainNewsListAdapter = MainNewsListAdapter(this,newsList)

        mRecyclerView.addOnScrollListener(object :RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                mMenuView.close(true)
                if (curTheme == 1)
                    return
                if (mLayoutManager.findLastCompletelyVisibleItemPosition() == mMainNewsListAdapter.itemCount-4){
                    pageNo++
                    requestNews()
                }
            }
        })

        mMenuView.setOnMenuToggleListener { opened -> mMenuView.alpha = if (opened){1f}else {0.5f}  }
        findViewById(R.id.fab_wx).setOnClickListener {
            pageNo =1
            requestNews()
            mMenuView.close(true)}
        findViewById(R.id.fab_comic).setOnClickListener {
            pageNo = 1
            requestComic()
            mMenuView.close(true)}

        requestComic()
    }

    private fun requestNews(){
        setTitle("微信精选")
        curTheme = 0
        if (pageNo == 1){
            newsList.clear()
            mRecyclerView.layoutManager = mLayoutManager
            mRecyclerView.adapter = mMainNewsListAdapter
            mLayoutManager.scrollToPositionWithOffset(0,0)
        }
        source.getNewsByPageNo(pageNo,arrayCallback = NewsCallback())
    }

    private fun requestComic(){
        setTitle("漫画")
        curTheme = 1
        source.getComics(objCallback = TopicCallback())
    }



    inner class NewsCallback:ArrCallBack<News>{

        override fun onTasksLoaded(tasks: List<News>) {
            if (isFinishing)
                return
            newsList.addAll(tasks)
            mMainNewsListAdapter.notifyDataSetChanged()
        }

        override fun onDataNotAvailable(msg: String?) {
            if (isFinishing)
                return
            Toast.makeText(this@MainActivity,msg,Toast.LENGTH_SHORT).show()
        }

        override fun start() {
        }

        override fun onComplete() {
        }
    }


    inner class TopicCallback:ObjCallBack<ComicData>{

        override fun onTasksLoaded(task: ComicData) {
            if (isFinishing)
                return
            mRecyclerView.layoutManager = StaggeredGridLayoutManager(2,VERTICAL)
            mRecyclerView.adapter =   MainComicGridAdapter(this@MainActivity,task.topics)
        }

        override fun onDataNotAvailable(msg: String?) {
            if (isFinishing)
                return
            Toast.makeText(this@MainActivity,msg,Toast.LENGTH_SHORT).show()
        }

        override fun start() {
        }

        override fun onComplete() {
        }
    }
}
