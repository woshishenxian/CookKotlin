package com.cook.kotlin.model

/**
 * Created by DE10035 on 2017/12/20.
 */
enum class ComicType(val type:Int,val typeName:String) {
    WXNEW(1,"微信精选"),
    NEW(2,"新作"),
    END(3,"完结"),
    BESTSELL(4,"畅销"),
    MALE(5,"男生"),
    FEMALE(6,"男生")

}