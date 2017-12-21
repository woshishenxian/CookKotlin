package com.cook.kotlin.cookkotlin.recent

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.cook.kotlin.cookkotlin.BaseActivity
import com.cook.kotlin.cookkotlin.R
import com.cook.kotlin.cookkotlin.adapter.RecentAdapter
import com.cook.kotlin.cookkotlin.comic.ComicActivity
import com.cook.kotlin.db.model.RecentComic
import com.cook.kotlin.utils.DBAsyncTask
import kotlinx.android.synthetic.main.activity_recent_comic.*

class RecentComicActivity : BaseActivity() {

    companion object {
        fun startActivity(context: Context) {
            val intent = Intent(context, RecentComicActivity::class.java)
            context.startActivity(intent)
        }
    }

    lateinit var mLayoutManager: LinearLayoutManager
    private var pageNo = 1
    val recentComics: ArrayList<RecentComic> = ArrayList()
    lateinit var mAdapter :RecentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recent_comic)

        setTitle("最近观看漫画")

        mLayoutManager = LinearLayoutManager(this)
        mRecyclerView.layoutManager = mLayoutManager
        mAdapter = RecentAdapter(this,recentComics)
        mRecyclerView.adapter = mAdapter

        DBAsyncTask(object : DBAsyncTask.Callback{
            override fun onPostExecute(result: List<RecentComic>) {
                    if (result.isNotEmpty()){
                        recentComics.addAll(result)
                        mAdapter.notifyDataSetChanged()
                    }
            }
        }).execute(DBAsyncTask.QUERY,pageNo)
    }

    override fun needNavigationIcon(): Boolean {
        return true
    }
}
