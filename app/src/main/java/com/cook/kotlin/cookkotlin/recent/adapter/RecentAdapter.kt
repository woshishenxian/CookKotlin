package com.cook.kotlin.cookkotlin.recent.adapter

import android.content.Context
import android.content.DialogInterface
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.cook.kotlin.cookkotlin.R
import com.cook.kotlin.cookkotlin.comic.ComicActivity
import com.cook.kotlin.cookkotlin.comic.ComicListActivity
import com.cook.kotlin.db.model.RecentComic
import com.cook.kotlin.utils.DBAsyncTask
import com.cook.kotlin.utils.DialogUtils
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.activity_recent_comic_item.view.*

/**
 * Created by DE10035 on 2017/12/21.
 */
class RecentAdapter(val context: Context, val recentComics: ArrayList<RecentComic>) : RecyclerView.Adapter<RecentAdapter.Holder>() {


    override fun onBindViewHolder(holder: Holder, position: Int) {
        val comic = recentComics.get(position)
        holder.bind(comic)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.activity_recent_comic_item, parent, false);
        return Holder(itemView)
    }

    override fun getItemCount(): Int {
        return recentComics.size
    }

    inner class Holder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {


        fun bind(recentComic: RecentComic) {
            Glide.with(context).load(recentComic.picUrl).into(containerView.mImageView)
            containerView.mTtitleView.text = recentComic.title
            containerView.mEpisodeTitleView.text = if (TextUtils.isEmpty(recentComic.episodeTitle)) {
                "您尚未观看"
            } else {
                recentComic.episodeTitle
            }
            containerView.setOnClickListener {
                if (recentComic.episodeId == 0) {
                    ComicListActivity.startActivity(context, recentComic.titleId)
                } else {
                    ComicActivity.startActivity(context, recentComic.episodeId)
                }

            }
            containerView.setOnLongClickListener {
                DialogUtils.showRemoveDialog(context, DialogInterface.OnClickListener { dialog, _ ->
                    recentComics.removeAt(adapterPosition)
                    notifyItemRemoved(adapterPosition)
                    DBAsyncTask().execute(DBAsyncTask.REMOVE, recentComic.id)
                    dialog.dismiss()
                })
                true
            }
        }

    }
}