package com.cook.kotlin.cookkotlin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient

class WebViewActivity : BaseActivity() {

    companion object {
        fun startActivity(context: Context, title: String?, url: String?) {
            val intent = Intent(context, WebViewActivity::class.java)
            intent.putExtra("Title", title)
            intent.putExtra("Url", url)
            context.startActivity(intent)
        }
    }

    private var mWebView: WebView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)

        mWebView = findViewById(R.id.mWebView) as WebView?
        initWebView(mWebView)
        intent.getStringExtra("Title")?.let {
            setTitle(it)
        }
        intent.getStringExtra("Url")?.let {
            mWebView?.loadUrl(it)
        }
    }

    private fun initWebView(webView:WebView?){
        webView?.let {
            webView.settings.javaScriptEnabled = true
        }
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

    override fun needNavigationIcon(): Boolean {
        return true
    }

    override fun isWebViewActivity(): Boolean {
        return true
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

    private class WebViewerClient : WebViewClient() {

        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            return super.shouldOverrideUrlLoading(view, request)
        }

    }

}