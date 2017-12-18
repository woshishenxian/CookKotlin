package com.cook.kotlin.cookkotlin.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import com.cook.kotlin.cookkotlin.R
import com.cook.kotlin.cookkotlin.comic.ComicListActivity
import com.cook.kotlin.model.Comic
import com.cook.kotlin.widget.RatioImageView
import java.util.*

/**
 * Created by DE10035 on 2017/12/15.
 */
class MainComicGridAdapter : RecyclerView.Adapter<MainComicGridAdapter.Holder> {
    private val topicList: ArrayList<Comic>
    private val context: Context
    private val random = Random()

    constructor(context: Context, topicList: ArrayList<Comic>) : super() {
        this.context = context
        this.topicList = topicList
    }

    override fun onBindViewHolder(holder: MainComicGridAdapter.Holder?, position: Int) {
        val topic = topicList.get(position)
        holder?.bind(topic)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MainComicGridAdapter.Holder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.activity_main_item_grid, parent, false);
        return Holder(itemView)
    }

    override fun getItemCount(): Int {
        return topicList.size
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mImageView: RatioImageView?
        val mTitleView: TextView?
        val mSourceView: TextView?
        var topic: Comic? = null

        init {
            mImageView = itemView.findViewById(R.id.mImageView) as RatioImageView
            mTitleView = itemView.findViewById(R.id.mTitleView) as TextView
            mSourceView = itemView.findViewById(R.id.mSourceView) as TextView
            itemView.setOnClickListener {
                    ComicListActivity.startActivity(context,topic?.id ?:0)
            }
        }

        fun bind(topic: Comic) {
            this.topic = topic
            mTitleView?.text = topic.title
            mSourceView?.text = topic.user.nickname
            if (topic.random <= 0){
                topic.random = random.nextInt(2)+1
            }
            Glide.with(context).load(
                    if (topic.random == 1){
                        mImageView?.ratio = 0.63f
                        topic.cover_image_url
                    } else{
                        mImageView?.ratio = 1.32f
                        topic.vertical_image_url})
                    .into(mImageView)
        }

    }

}