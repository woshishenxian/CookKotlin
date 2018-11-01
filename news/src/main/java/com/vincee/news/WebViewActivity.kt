package com.vincee.news

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.vincee.core.base.CoreActivity
import com.vincee.provider.RouterPath

@Route(path = RouterPath.News.PATH_WEB)
class WebViewActivity : CoreActivity() {

    companion object {
        fun startActivity(context: Context, title: String?, url: String?) {
            val intent = Intent(context, WebViewActivity::class.java)
            intent.putExtra(RouterPath.News.PARAM_TITLE, title)
            intent.putExtra(RouterPath.News.PARAM_TITLE, url)
            context.startActivity(intent)
        }
    }

    private var mWebView: WebView? = null

    @Autowired(name = RouterPath.News.PARAM_TITLE)
    @JvmField
    var title: String? = null

    @Autowired(name = RouterPath.News.PARAM_URL)
    @JvmField
    var url: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)

        mWebView = findViewById(R.id.mWebView)
        initWebView(mWebView)

        setTitle(title)
        mWebView?.loadUrl(url)
    }

    private fun initWebView(webView: WebView?) {
        webView?.let {
            webView.settings.javaScriptEnabled = true
            webView.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                    view?.loadUrl(url)
                    return true
                }
            }
        }
    }


    override fun needNavigationIcon(): Boolean {
        return true
    }

    override fun isWebViewActivity(): Boolean {
        return true
    }
}
