package com.cook.kotlin.cookkotlin.comic

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.cook.kotlin.cookkotlin.BaseActivity
import com.cook.kotlin.cookkotlin.R
import com.cook.kotlin.cookkotlin.comic.adapter.ComicAdapter
import com.cook.kotlin.model.ComicData
import com.cook.kotlin.model.base.ObjCallBack
import kotlinx.android.synthetic.main.activity_comic.*
import kotlinx.android.synthetic.main.toolbar_comic.*

class ComicActivity : BaseActivity() {

    companion object {
        fun startActivity(context: Context, no: Int) {
            val intent = Intent(context, ComicActivity::class.java)
            intent.putExtra("comic_id", no)
            context.startActivity(intent)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comic)

        toolbar.setBackgroundColor(Color.parseColor("#44000000"))

        mRecyclerView.layoutManager = LinearLayoutManager(this)
        source.getComicById(intent.getIntExtra("comic_id", 0), objCallback = ComicCallback())
    }


    override fun needNavigationIcon(): Boolean {
        return true
    }

    inner class ComicCallback : ObjCallBack<ComicData> {

        override fun onTasksLoaded(task: ComicData) {
            if (isFinishing)
                return
            mRecyclerView.adapter = ComicAdapter(this@ComicActivity, task)
            Glide.with(this@ComicActivity).load(task.topic.user.avatar_url).crossFade().diskCacheStrategy(DiskCacheStrategy.ALL).into(headImage)
            setTitle(task.title)
        }

        override fun onDataNotAvailable(msg: String?) {
            if (isFinishing)
                return
            Toast.makeText(this@ComicActivity, msg, Toast.LENGTH_SHORT).show()
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
