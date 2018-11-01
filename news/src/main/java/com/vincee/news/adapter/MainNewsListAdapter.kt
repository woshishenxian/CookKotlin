package com.vincee.news.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.vincee.news.R
import com.vincee.news.model.News
import com.vincee.provider.RouterPath
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.activity_main_item_list.view.*

/**
 * Created by DE10035 on 2017/12/15.
 */
class MainNewsListAdapter(val context: Context, val newsList: ArrayList<News>) : RecyclerView.Adapter<MainNewsListAdapter.Holder>() {

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val news = newsList.get(position)
        holder.bind(news)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.activity_main_item_list, parent, false);
        return Holder(itemView)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    inner class Holder(override val containerView: View) : ViewHolder(containerView), LayoutContainer {
        var news: News? = null

        init {
            containerView.setOnClickListener {
                ARouter.getInstance().build(RouterPath.News.PATH_WEB).withString(RouterPath.News.PARAM_TITLE, news?.title)
                        .withString(RouterPath.News.PARAM_URL, news?.url).navigation()
            }
        }

        fun bind(news: News) {
            Glide.with(context).load(news.picUrl).into(containerView.mImageView)
            containerView.mTitleView.text = news.title
            containerView.mSourceView.text = news.ctime
            this.news = news
        }

    }

}