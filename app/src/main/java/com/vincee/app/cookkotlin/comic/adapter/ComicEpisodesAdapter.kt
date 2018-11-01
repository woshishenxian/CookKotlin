package com.vincee.app.cookkotlin.comic.adapter

import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.vincee.app.R
import com.vincee.app.cookkotlin.comic.ComicActivity
import com.vincee.app.cookkotlin.comic.ComicListActivity
import com.vincee.app.model.Comic
import com.vincee.app.model.ComicData
import com.vincee.provider.glide.GlideApp
import com.vincee.provider.glide.GlideRoundTransform
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.activity_comic_list_item_banner.view.*
import kotlinx.android.synthetic.main.activity_comic_list_item_discription.view.*
import kotlinx.android.synthetic.main.activity_comic_list_item_episode.view.*

/**
 * Created by DE10035 on 2017/12/18.
 */
class ComicEpisodesAdapter(val context: Context, val comicData: ComicData) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val recentEpisodeIds: ArrayList<Int> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 0) {
            return BannerHolder(LayoutInflater.from(context).inflate(R.layout.activity_comic_list_item_banner, parent, false))
        } else if (viewType == 1) {
            return DiscriptionHolder(LayoutInflater.from(context).inflate(R.layout.activity_comic_list_item_discription, parent, false))
        } else {
            return EpisodeHolder(LayoutInflater.from(context).inflate(R.layout.activity_comic_list_item_episode, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is EpisodeHolder && position >= 2) {
            holder.bind(comicData.comics[position - 2])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return comicData.comics.size + 2
    }

    inner class BannerHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        init {
            Glide.with(context).load(comicData.cover_image_url).into(containerView.mBannerView)
            containerView.mNicknameView.text = comicData.user.nickname
            containerView.mTitleView.text = comicData.title
        }
    }

    inner class DiscriptionHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        init {
            containerView.mDiscriptionView.text = comicData.description
        }
    }

    inner class EpisodeHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(comic: Comic) {
            GlideApp.with(context).load(comic.cover_image_url).optionalTransform(GlideRoundTransform()).into(containerView.mImageView)
            containerView.mSourceView.text = DateFormat.format("yyyy-MM-dd", comic.updated_at)
            containerView.mEpisodeTitleView.text = comic.title
            containerView.item_layout.isSelected = recentEpisodeIds.contains(comic.id)
            containerView.setOnClickListener {
                ComicActivity.startActivityForResult(context as Activity, comic.id, ComicListActivity.requestCode)
                containerView.item_layout.isSelected = true
                recentEpisodeIds.add(comic.id)
            }
        }

    }

    fun notifyRecentDataChanged(recentEpisodeIds: List<Int>?) {
        recentEpisodeIds?.let {
            this.recentEpisodeIds.clear()
            this.recentEpisodeIds.addAll(recentEpisodeIds)
            notifyDataSetChanged()
        }
    }
}