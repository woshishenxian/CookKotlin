package com.cook.kotlin.utils

import android.support.v4.view.GestureDetectorCompat
import android.support.v7.widget.RecyclerView
import android.view.GestureDetector
import android.view.MotionEvent

/**
 * Created by DE10035 on 2018/1/3.
 */
abstract class SimpleOnItemTouchjListener(private val mRecyclerView: RecyclerView) : RecyclerView.OnItemTouchListener {

    private val mGestureDetectorCompat: GestureDetectorCompat

    init {
        this.mGestureDetectorCompat = GestureDetectorCompat(mRecyclerView.context,object: GestureDetector.SimpleOnGestureListener(){
            override fun onSingleTapUp(e: MotionEvent?): Boolean {
                e?.let {
                    val child = mRecyclerView.findChildViewUnder(e.x,e.y)
                    val vh = mRecyclerView.getChildViewHolder(child)
                    onItemClick(vh)
                    return true
                }
                return false
            }
        })
    }

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        mGestureDetectorCompat.onTouchEvent(e)
        return false
    }

    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
        mGestureDetectorCompat.onTouchEvent(e)
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {

    }

    abstract fun onItemClick(vh: RecyclerView.ViewHolder)
}
