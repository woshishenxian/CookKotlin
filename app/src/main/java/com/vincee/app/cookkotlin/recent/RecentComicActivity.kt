package com.vincee.app.cookkotlin.recent

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.vincee.app.R
import com.vincee.app.cookkotlin.BaseActivity
import com.vincee.app.cookkotlin.recent.adapter.RecentAdapter
import com.vincee.app.db.model.RecentComic
import com.vincee.app.utils.DBAsyncTask
import com.vincee.app.utils.DialogUtils
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
    val recentComics: ArrayList<com.vincee.app.db.model.RecentComic> = ArrayList()
    lateinit var mAdapter : RecentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recent_comic)

        setTitle(R.string.recentComic)

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_recent_clear,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_settings){
            DialogUtils.showClearDialog(this@RecentComicActivity,DialogInterface.OnClickListener { dialog, _ ->
                DBAsyncTask().execute(DBAsyncTask.CLEAR)
                recentComics.clear()
                mAdapter.notifyDataSetChanged()
                dialog.dismiss()
            })
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun needNavigationIcon(): Boolean {
        return true
    }
}
