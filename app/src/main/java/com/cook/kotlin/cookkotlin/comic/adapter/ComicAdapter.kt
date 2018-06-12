package com.cook.kotlin.cookkotlin.comic.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeTransition
import com.cook.kotlin.cookkotlin.R
import com.cook.kotlin.model.ComicData
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.activity_comic_item.view.*

/**
 * Created by DE10035 on 2017/12/18.
 */
class ComicAdapter(val context: Context, val comicData: ComicData) : RecyclerView.Adapter<ComicAdapter.Holder>() {


    override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): com.cook.kotlin.cookkotlin.comic.adapter.ComicAdapter.Holder {
        return Holder(LayoutInflater.from(context).inflate(R.layout.activity_comic_item, parent, false))
    }

    override fun onBindViewHolder(holder: com.cook.kotlin.cookkotlin.comic.adapter.ComicAdapter.Holder, position: Int) {
        holder.bind(position)
    }


    override fun getItemCount(): Int {
        return if (comicData.images.size <= comicData.image_infos.size) {
            comicData.images.size
        } else {
            comicData.image_infos.size
        }
    }


    inner class Holder(override val containerView: android.view.View) : RecyclerView.ViewHolder(containerView), LayoutContainer {


        fun bind(position: Int) {
            val imageInfo = comicData.image_infos[position]
            containerView.mImageView.ratio = imageInfo.height.toFloat() / imageInfo.width.toFloat()
            Glide.with(context).load(comicData.images[position]).transition(DrawableTransitionOptions().crossFade())
//                    .dontAnimate().placeholder(R.drawable.image_placeholder)
                    .into(containerView.mImageView)
            containerView.setOnClickListener { callback?.onItemClick(position,it) }
        }

    }

    var callback:OnItemClickCallBack ?= null

}


public interface OnItemClickCallBack {
    fun onItemClick(position: Int, v: View);
}