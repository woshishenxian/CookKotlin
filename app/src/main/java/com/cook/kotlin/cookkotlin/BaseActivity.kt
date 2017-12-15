package com.cook.kotlin.cookkotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.TextView

/**
 * Created by DE10035 on 2017/12/14.
 */
open class BaseActivity : AppCompatActivity() {
    private var toolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        initToolbar()
        initCloseBtn()
    }

    private fun initToolbar() {
        toolbar = findViewById(R.id.toolbar) as Toolbar
        if (toolbar == null) {
            return
        }
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(needNavigationIcon())
        supportActionBar!!.setTitle("")

    }

    private fun initCloseBtn() {
        val closeBtn = findViewById(R.id.btn_close)
        closeBtn.visibility = if (isWebViewActivity()) {
            closeBtn.setOnClickListener { finish() }
            View.VISIBLE
        }else{
            View.GONE
        }
    }

    fun setTitle(title: String) {
        val titleView = findViewById(R.id.title) as TextView?
        titleView?.text = title
    }

    open fun needNavigationIcon(): Boolean {
        return false
    }

    open fun isWebViewActivity(): Boolean {
        return false
    }
}