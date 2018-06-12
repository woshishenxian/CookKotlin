package com.cook.kotlin.cookkotlin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.StaggeredGridLayoutManager
import android.support.v7.widget.StaggeredGridLayoutManager.VERTICAL
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.cook.kotlin.cookkotlin.about.AboutActivity
import com.cook.kotlin.cookkotlin.adapter.MainComicGridAdapter
import com.cook.kotlin.cookkotlin.recent.RecentComicActivity
import com.cook.kotlin.cookkotlin.wall.LiveWallpagerActivity
import com.cook.kotlin.model.Comic
import com.cook.kotlin.model.ComicData
import com.cook.kotlin.model.ComicType
import com.cook.kotlin.model.base.ObjCallBack
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.android.synthetic.main.toolbar_base.*


class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }

    val topicList: ArrayList<Comic> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)

        mRecyclerView.layoutManager = StaggeredGridLayoutManager(2, VERTICAL)
        val mainComicGridAdapter = MainComicGridAdapter(this@MainActivity, topicList)
        mRecyclerView.adapter = mainComicGridAdapter
        val itemTouchHelper = ItemTouchHelper(XCallback(mainComicGridAdapter))
        itemTouchHelper.attachToRecyclerView(mRecyclerView)
        requestComic(ComicType.END)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main_more, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_settings) {
            AboutActivity.startActivity(this@MainActivity)
            return true
        }
        return super.onOptionsItemSelected(item)
    }


    private fun requestComic(comicType: ComicType) {
        setTitle(comicType.typeName)
        source.getComics(comicType.type, objCallback = TopicCallback())
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_recent -> {
                RecentComicActivity.startActivity(this@MainActivity)
            }
            R.id.nav_livewall -> {
                LiveWallpagerActivity.startActivity(this@MainActivity)
            }
            R.id.nav_male -> {
                requestComic(ComicType.MALE)
            }
            R.id.nav_female -> {
                requestComic(ComicType.FEMALE)
            }
            R.id.nav_new -> {
                requestComic(ComicType.NEW)
            }
            R.id.nav_end -> {
                requestComic(ComicType.END)
            }
            R.id.nav_best_sell -> {
                requestComic(ComicType.BESTSELL)
            }
            R.id.nav_about -> {
                AboutActivity.startActivity(this@MainActivity)
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }


    inner class TopicCallback : ObjCallBack<ComicData> {

        override fun onTasksLoaded(task: ComicData) {
            if (isFinishing)
                return
            topicList.clear()
            topicList.addAll(task.topics)
            mRecyclerView.adapter.notifyDataSetChanged()
            mRecyclerView.scrollToPosition(0)
        }

        override fun onDataNotAvailable(msg: String?) {
            if (isFinishing)
                return
            Toast.makeText(this@MainActivity, msg, Toast.LENGTH_SHORT).show()
        }

        override fun start() {

        }

        override fun onComplete() {

        }
    }
}
