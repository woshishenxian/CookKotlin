package com.cook.kotlin.cookkotlin.about

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.cook.kotlin.cookkotlin.BaseActivity
import com.cook.kotlin.cookkotlin.BuildConfig
import com.cook.kotlin.cookkotlin.R
import kotlinx.android.synthetic.main.activity_about.*

class AboutActivity : BaseActivity() {

    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, AboutActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        overridePendingTransition(R.anim.activity_down_up_anim, R.anim.activity_exit_anim)
        version_text.text = "v"+BuildConfig.VERSION_NAME

        btn_blog.setOnClickListener { startViewAction(R.string.blog_url) }

        btn_project_home.setOnClickListener { startViewAction(R.string.project_url) }
        setTitle("")
    }


    private fun startViewAction(uriRes: Int) {
        try {
            val uri = Uri.parse(getString(uriRes))
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
        }

    }

    override fun needNavigationIcon(): Boolean {
        return true
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.activity_exit_anim, R.anim.activity_up_down_anim)
    }
}
