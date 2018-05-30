package com.cook.kotlin.cookkotlin.daily

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cook.kotlin.cookkotlin.R

class DialyFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_daily,container,false)
    }


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {


    }
}