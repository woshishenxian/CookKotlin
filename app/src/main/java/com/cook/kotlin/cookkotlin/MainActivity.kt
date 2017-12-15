package com.cook.kotlin.cookkotlin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.cook.kotlin.cookkotlin.adapter.ListAdapter
import com.cook.kotlin.model.News
import com.cook.kotlin.model.base.ArrCallBack
import com.cook.kotlin.source.NewsDataSource
import com.github.clans.fab.FloatingActionMenu

class MainActivity : BaseActivity() ,ArrCallBack<News>{

    companion object{
        fun startActivity(context: Context){
            context.startActivity(Intent(context,MainActivity::class.java))
        }
    }

    lateinit var mRecyclerView: RecyclerView
    lateinit var mLayoutManager: LinearLayoutManager
    lateinit var mAdapter: ListAdapter
    lateinit var mMenuView: FloatingActionMenu


    val newsList :ArrayList<News> = ArrayList<News>()
    val source = NewsDataSource()

    private var pageNo = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mRecyclerView = findViewById(R.id.mRecyclerView) as RecyclerView
        mMenuView = findViewById(R.id.menu) as FloatingActionMenu

        mLayoutManager = LinearLayoutManager(this)
        mRecyclerView.layoutManager = mLayoutManager
        mAdapter = ListAdapter(this,newsList)
        mRecyclerView.adapter = mAdapter
        mRecyclerView.addOnScrollListener(object :RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                mMenuView.close(true)
                if (mLayoutManager.findLastCompletelyVisibleItemPosition() == mAdapter.itemCount-4){
                    pageNo++
                    requestNews()
                }
            }
        })

        mMenuView.setOnMenuToggleListener { opened -> mMenuView.alpha = if (opened){1f}else {0.5f}  }
        findViewById(R.id.fab_huabian).setOnClickListener {
            pageNo =1
            requestHuanbian()
            mMenuView.close(true)}
        findViewById(R.id.fab_nba).setOnClickListener {
            pageNo = 1
            requestNba()
            mMenuView.close(true)}
        findViewById(R.id.fab_wx).setOnClickListener {
            pageNo = 1
            requestNews()
            mMenuView.close(true)}
        findViewById(R.id.fab_travel).setOnClickListener {
            pageNo = 1
            requestTravel()
            mMenuView.close(true)}

        requestNews()
    }

    private fun requestNews(){
        source.getNewsByPageNo(pageNo,this)
    }

    private fun requestNba(){
        source.getNbaByPageNo(pageNo,this)
    }

    private fun requestTravel(){
        source.getTravelByPageNo(pageNo,this)
    }

    private fun requestHuanbian(){
        source.getHuabianByPageNo(pageNo,this)
    }

    override fun onTasksLoaded(tasks: List<News>) {
        if (pageNo == 1){
            newsList.clear()
            mLayoutManager.scrollToPositionWithOffset(0,0)
        }
        newsList.addAll(tasks)
        mAdapter.notifyDataSetChanged()
    }

    override fun onDataNotAvailable(msg: String?) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
    }

    override fun start() {
    }

    override fun onComplete() {
    }
}
