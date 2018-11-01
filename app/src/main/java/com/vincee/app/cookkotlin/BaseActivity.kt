package com.vincee.app.cookkotlin

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v4.app.TaskStackBuilder
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.vincee.app.source.DataSource
import kotlinx.android.synthetic.main.activity_webview.*
import kotlinx.android.synthetic.main.toolbar_base.*

/**
 * Created by DE10035 on 2017/12/14.
 */
abstract class BaseActivity : AppCompatActivity() {

    val source = DataSource()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        initToolbar()
        initCloseBtn()
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(needNavigationIcon())
        supportActionBar?.setTitle("")

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                return navigateHomeItem()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    protected fun navigateHomeItem(): Boolean {
        if (mWebView?.canGoBack() ?: false) {
            mWebView?.goBack()
        } else {
            val upIntent = NavUtils.getParentActivityIntent(this)
            if (upIntent != null) {
                onCreateUpIntent(upIntent)
                if (NavUtils.shouldUpRecreateTask(this, upIntent) || isTaskRoot) {
                    TaskStackBuilder.create(this).addNextIntentWithParentStack(upIntent).startActivities()
                    finish()
                } else {
                    NavUtils.navigateUpTo(this, upIntent)
                }
            } else {
                finish()
            }
        }
        return true
    }

    override fun navigateUpTo(upIntent: Intent): Boolean {
        upIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(upIntent)
        finish()
        return true
    }

    open fun onCreateUpIntent(upIntent: Intent) {}

    protected fun hideToolbar() {
        if (supportActionBar?.isShowing ?: false) {
            supportActionBar?.hide()
        }
    }

    protected fun showToolbar() {
        if (!(supportActionBar?.isShowing ?: true)) {
            supportActionBar?.show()
        }
    }

    override fun onBackPressed() {
        navigateHomeItem()
    }

    private fun initCloseBtn() {
        btn_close?.visibility = if (isWebViewActivity()) {
            btn_close.setOnClickListener { finish() }
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    override fun setTitle(titleId: Int) {
        mainTitle.setText(titleId)
    }

    override fun setTitle(title: CharSequence?) {
        title?.let { mainTitle.text = title }

    }

    open fun needNavigationIcon(): Boolean {
        return false
    }

    open fun isWebViewActivity(): Boolean {
        return false
    }
}

fun AppCompatActivity.toast(msg:String){
    Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
}