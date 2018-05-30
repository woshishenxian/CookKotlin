package com.cook.kotlin.cookkotlin.daily

import java.util.*

enum class WeekDay(val date: Int) {
    SUNDAY(Calendar.SUNDAY)
    ,
    MONDAY(Calendar.MONDAY)
    ,
    TUESDAY(Calendar.TUESDAY)
    ,
    WEDNESDAY(Calendar.WEDNESDAY)
    ,
    THURSDAY(Calendar.THURSDAY)
    ,
    FRIDAY(Calendar.FRIDAY)
    ,
    SATURDAY(Calendar.SATURDAY);
}

class WeekDayUtils {
    companion object {
        fun paserByWeek(day: WeekDay): Int {
//            val today = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
//            val yesterday = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
//            WeekDay.values().iterator().forEach {
//                return if (it.date == today) {
//                    0
//                } else {
//
//                }
//            }
            return 0
        }
    }
}