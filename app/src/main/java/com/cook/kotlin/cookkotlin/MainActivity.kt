package com.cook.kotlin.cookkotlin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.support.v7.widget.StaggeredGridLayoutManager.VERTICAL
import android.widget.Toast
import com.cook.kotlin.cookkotlin.adapter.GridAdapter
import com.cook.kotlin.cookkotlin.adapter.ListAdapter
import com.cook.kotlin.model.News
import com.cook.kotlin.model.Topic
import com.cook.kotlin.model.TopicData
import com.cook.kotlin.model.base.ArrCallBack
import com.cook.kotlin.model.base.ObjCallBack
import com.cook.kotlin.source.NewsDataSource
import com.github.clans.fab.FloatingActionMenu

class MainActivity : BaseActivity(){

    companion object{
        fun startActivity(context: Context){
            context.startActivity(Intent(context,MainActivity::class.java))
        }
    }

    lateinit var mRecyclerView: RecyclerView
    lateinit var mLayoutManager: LinearLayoutManager
    lateinit var mListAdapter: ListAdapter
    lateinit var mMenuView: FloatingActionMenu

    val source = NewsDataSource()

    val newsList :ArrayList<News> = ArrayList<News>()
    private var pageNo = 1

    private var curTheme: Int = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mRecyclerView = findViewById(R.id.mRecyclerView) as RecyclerView
        mMenuView = findViewById(R.id.menu) as FloatingActionMenu
        mLayoutManager = LinearLayoutManager(this)
        mListAdapter = ListAdapter(this,newsList)

        mRecyclerView.addOnScrollListener(object :RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                mMenuView.close(true)
                if (curTheme == 1)
                    return
                if (mLayoutManager.findLastCompletelyVisibleItemPosition() == mListAdapter.itemCount-4){
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

        requestNews()
    }

    private fun requestNews(){
        setTitle("微信精选")
        curTheme = 0
        if (pageNo == 1){
            newsList.clear()
            mRecyclerView.layoutManager = mLayoutManager
            mRecyclerView.adapter = mListAdapter
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
            newsList.addAll(tasks)
            mListAdapter.notifyDataSetChanged()
        }

        override fun onDataNotAvailable(msg: String?) {
            Toast.makeText(this@MainActivity,msg,Toast.LENGTH_SHORT).show()
        }

        override fun start() {
        }

        override fun onComplete() {
        }
    }


    inner class TopicCallback:ObjCallBack<TopicData>{

        override fun onTasksLoaded(task: TopicData) {
            mRecyclerView.layoutManager = StaggeredGridLayoutManager(2,VERTICAL)
            mRecyclerView.adapter =   GridAdapter(this@MainActivity,task.topics)
        }

        override fun onDataNotAvailable(msg: String?) {
            Toast.makeText(this@MainActivity,msg,Toast.LENGTH_SHORT).show()
        }

        override fun start() {
        }

        override fun onComplete() {
        }
    }
}
