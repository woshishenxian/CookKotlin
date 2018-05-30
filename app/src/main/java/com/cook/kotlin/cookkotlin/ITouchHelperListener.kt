package com.cook.kotlin.cookkotlin

interface ITouchHelperListener {

    fun onItemMove(formPosition: Int, toPosition: Int)
    fun onItemSwipe(position: Int)
}