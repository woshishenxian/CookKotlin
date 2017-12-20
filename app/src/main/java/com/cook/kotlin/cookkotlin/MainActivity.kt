package com.cook.kotlin.cookkotlin

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.support.v7.widget.StaggeredGridLayoutManager.VERTICAL
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.cook.kotlin.cookkotlin.adapter.MainComicGridAdapter
import com.cook.kotlin.cookkotlin.adapter.MainNewsListAdapter
import com.cook.kotlin.model.Comic
import com.cook.kotlin.model.ComicData
import com.cook.kotlin.model.ComicType
import com.cook.kotlin.model.News
import com.cook.kotlin.model.base.ArrCallBack
import com.cook.kotlin.model.base.ObjCallBack
import com.cook.kotlin.widget.CategoryDialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fab_menu.*

class MainActivity : BaseActivity() {

    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }

    lateinit var mLayoutManager: LinearLayoutManager
    lateinit var mMainNewsListAdapter: MainNewsListAdapter


    val newsList: ArrayList<News> = ArrayList<News>()
    private var pageNo = 1

    private var curType: ComicType =  ComicType.BESTSELL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mLayoutManager = LinearLayoutManager(this)
        mMainNewsListAdapter = MainNewsListAdapter(this, newsList)

        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                mMenuView.close(true)
                if (curType != ComicType.WXNEW)
                    return
                if (mLayoutManager.findLastCompletelyVisibleItemPosition() == mMainNewsListAdapter.itemCount - 4) {
                    pageNo++
                    requestNews()
                }
            }
        })

        mMenuView.setOnMenuToggleListener { opened ->
            mMenuView.alpha = if (opened) {
                1f
            } else {
                0.5f
            }
        }
        fab_male.setOnClickListener {
            requestComic(ComicType.MALE)
            mMenuView.close(true)
        }
        fab_female.setOnClickListener {
            requestComic(ComicType.FEMALE)
            mMenuView.close(true)
        }
        fab_new.setOnClickListener {
            requestComic(ComicType.NEW)
            mMenuView.close(true)
        }
        fab_end.setOnClickListener {
            requestComic(ComicType.END)
            mMenuView.close(true)
        }
        fab_best_sell.setOnClickListener {
            requestComic(ComicType.BESTSELL)
            mMenuView.close(true)
        }

        requestComic(curType)
    }

    private fun requestNews() {
        curType = ComicType.WXNEW
        setTitle(ComicType.WXNEW.typeName)
        if (pageNo == 1) {
            newsList.clear()
            mRecyclerView.layoutManager = mLayoutManager
            mRecyclerView.adapter = mMainNewsListAdapter
            mLayoutManager.scrollToPositionWithOffset(0, 0)
        }
        source.getNewsByPageNo(pageNo, arrayCallback = NewsCallback())
    }

    private fun requestComic(comicType:ComicType) {
        curType = comicType
        setTitle(comicType.typeName)
        source.getComics(comicType.type,objCallback = TopicCallback())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main_more,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_settings){
            CategoryDialog(this,object :CategoryDialog.OnItemClickListener{
                override fun onItemClick(position: Int,dialog:Dialog) {
                    if (position == 0){
                        mMenuView.visibility = View.GONE
                        pageNo=1
                        requestNews()
                    }else{
                        mMenuView.visibility = View.VISIBLE
                        requestComic(ComicType.BESTSELL)
                    }
                    dialog.dismiss()
                }
            }).show()
            return true
        }
        return super.onOptionsItemSelected(item)
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
            Toast.makeText(this@MainActivity, msg, Toast.LENGTH_SHORT).show()
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


    inner class TopicCallback : ObjCallBack<ComicData> {

        override fun onTasksLoaded(task: ComicData) {
            if (isFinishing)
                return
            mRecyclerView.layoutManager = StaggeredGridLayoutManager(2, VERTICAL)
            mRecyclerView.adapter = MainComicGridAdapter(this@MainActivity, task.topics)
        }

        override fun onDataNotAvailable(msg: String?) {
            if (isFinishing)
                return
            Toast.makeText(this@MainActivity, msg, Toast.LENGTH_SHORT).show()
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
