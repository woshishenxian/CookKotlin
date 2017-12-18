package com.cook.kotlin.cookkotlin.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.cook.kotlin.cookkotlin.R
import com.cook.kotlin.model.ComicData
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.activity_comic_list_item_episode.view.*

/**
 * Created by DE10035 on 2017/12/18.
 */
class ComicAdapter(val context: Context,val comicData:ComicData) :RecyclerView.Adapter<ComicAdapter.Holder>(){


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ComicAdapter.Holder {
       return Holder(LayoutInflater.from(context).inflate(R.layout.activity_comic_item,parent,false))
    }

    override fun onBindViewHolder(holder: ComicAdapter.Holder?, position: Int) {
        holder?.bind(position)
    }


    override fun getItemCount(): Int {
        return if (comicData.images.size <=comicData.image_infos.size){comicData.images.size}else{comicData.image_infos.size}
    }


    inner class Holder(override val containerView: View) : RecyclerView.ViewHolder(containerView) ,LayoutContainer{

        fun bind(position: Int) {
            val imageInfo = comicData.image_infos[position]
            containerView.mImageView.ratio = imageInfo.height.toFloat()/imageInfo.width.toFloat()
            Glide.with(context).load(comicData.images[position]).into(containerView.mImageView)
        }

    }
}