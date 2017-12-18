package com.cook.kotlin.cookkotlin.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.cook.kotlin.cookkotlin.R
import com.cook.kotlin.cookkotlin.WebViewActivity
import com.cook.kotlin.model.News

/**
 * Created by DE10035 on 2017/12/15.
 */
class MainNewsListAdapter : RecyclerView.Adapter<MainNewsListAdapter.Holder> {
    private val newsList: ArrayList<News>
    private val context: Context

    constructor(context: Context, newsList: ArrayList<News>) : super() {
        this.context = context
        this.newsList = newsList
    }

    override fun onBindViewHolder(holder: MainNewsListAdapter.Holder?, position: Int) {
        val news = newsList.get(position)
        holder?.bind(news)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MainNewsListAdapter.Holder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.activity_main_item_list, parent, false);
        return Holder(itemView)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    inner class Holder(itemView: View) : ViewHolder(itemView) {
        val mImageView: ImageView?
        val mTitleView: TextView?
        val mSourceView: TextView?
        var news: News? = null

        init {
            mImageView = itemView.findViewById(R.id.mImageView) as ImageView
            mTitleView = itemView.findViewById(R.id.mTitleView) as TextView
            mSourceView = itemView.findViewById(R.id.mSourceView) as TextView
            itemView.setOnClickListener {
                WebViewActivity.startActivity(context,news?.title,news?.url)
            }
        }

        fun bind(news: News) {
            Glide.with(context).load(news.picUrl).into(mImageView)
            mTitleView?.text = news.title
            mSourceView?.text = news.ctime
            this.news = news
        }

    }

}