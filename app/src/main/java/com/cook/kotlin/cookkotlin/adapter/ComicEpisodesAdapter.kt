package com.cook.kotlin.cookkotlin.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.cook.kotlin.cookkotlin.R
import com.cook.kotlin.cookkotlin.comic.ComicActivity
import com.cook.kotlin.model.Comic
import com.cook.kotlin.model.ComicData
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.activity_comic_list_item_banner.view.*
import kotlinx.android.synthetic.main.activity_comic_list_item_discription.view.*
import kotlinx.android.synthetic.main.activity_comic_list_item_episode.view.*

/**
 * Created by DE10035 on 2017/12/18.
 */
class ComicEpisodesAdapter(val context: Context,val comicData:ComicData) :RecyclerView.Adapter<RecyclerView.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 0){
           return BannerHolder(LayoutInflater.from(context).inflate(R.layout.activity_comic_list_item_banner,parent,false))
        }else if (viewType == 1){
            return DiscriptionHolder(LayoutInflater.from(context).inflate(R.layout.activity_comic_list_item_discription,parent,false))
        }else{
            return EpisodeHolder(LayoutInflater.from(context).inflate(R.layout.activity_comic_list_item_episode,parent,false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (holder is EpisodeHolder && position>=2){
            holder.bind(comicData.comics[position-2])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return comicData.comics.size +2
    }

    inner class BannerHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),LayoutContainer {

        init {
            Glide.with(context).load(comicData.cover_image_url).into(containerView.mBannerView)
            containerView.mNicknameView.text = comicData.user.nickname
            containerView.mTitleView.text = comicData.title
        }
    }

    inner class DiscriptionHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),LayoutContainer {
        init {
            containerView.mDiscriptionView.text = comicData.description
        }
    }

    inner class EpisodeHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView) ,LayoutContainer{

        fun bind(comic: Comic) {
            Glide.with(context).load(comic.cover_image_url).into(containerView.mImageView)
            containerView.mSourceView.text = DateFormat.format("yyyy-MM-dd",comic.updated_at)
            containerView.mEpisodeTitleView.text = comic.title
            containerView.setOnClickListener { ComicActivity.startActivity(context,comic.id) }
        }

    }
}