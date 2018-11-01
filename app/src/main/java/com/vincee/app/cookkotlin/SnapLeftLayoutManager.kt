package com.vincee.app.cookkotlin

import android.support.v7.widget.RecyclerView

class SnapLeftLayoutManager : RecyclerView.LayoutManager() {

    companion object {
        val DEFAULT_SHOW_ITEM = 2
    }

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT,
                RecyclerView.LayoutParams.WRAP_CONTENT)
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State?) {
        detachAndScrapAttachedViews(recycler)
    }


}