package com.vincee.app.cookkotlin

interface ITouchHelperListener {

    fun onItemMove(formPosition: Int, toPosition: Int)
    fun onItemSwipe(position: Int)
}