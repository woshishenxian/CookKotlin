package com.cook.kotlin.cookkotlin

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import com.cook.kotlin.source.DataSource
import kotlinx.android.synthetic.main.activity_webview.*
import kotlinx.android.synthetic.main.toolbar_base.*

/**
 * Created by DE10035 on 2017/12/14.
 */
open class BaseActivity : AppCompatActivity() {

    val source = DataSource()
    lateinit var progressDialog:ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("正在努力加载")
    }

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        initToolbar()
        initCloseBtn()
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(needNavigationIcon())
        supportActionBar!!.setTitle("")

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                navigateHomeItem()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    protected fun navigateHomeItem() {
        if (mWebView?.canGoBack() ?: false) {
            mWebView?.goBack()
            return
        }
        finish()
    }

    override fun onBackPressed() {
        navigateHomeItem()
    }

    private fun initCloseBtn() {
        btn_close?.visibility = if (isWebViewActivity()) {
            btn_close.setOnClickListener { finish() }
            View.VISIBLE
        }else{
            View.GONE
        }
    }

    fun setTitle(titleString: String) {
        mainTitle.text = titleString
    }

    open fun needNavigationIcon(): Boolean {
        return false
    }

    open fun isWebViewActivity(): Boolean {
        return false
    }
}