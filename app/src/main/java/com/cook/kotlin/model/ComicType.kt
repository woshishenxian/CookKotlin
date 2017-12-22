package com.cook.kotlin.model

import com.cook.kotlin.cookkotlin.R

/**
 * Created by DE10035 on 2017/12/20.
 */
enum class ComicType(val type: Int, val typeName: Int) {
    NEW(2, R.string.new_comic),
    END(3, R.string.end),
    BESTSELL(4, R.string.best_sell),
    MALE(5, R.string.male),
    FEMALE(6, R.string.female)
}