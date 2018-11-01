package com.vincee.wallpager

import android.Manifest
import android.app.WallpaperManager
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.widget.Toast
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.vincee.core.base.CoreActivity
import com.vincee.provider.RouterPath
import kotlinx.android.synthetic.main.activity_wall_pager.*


/**
 * Created by DE10035 on 2018/3/13.
 */
@Route(path = RouterPath.WallPager.PATH_WALL_PAPER)
class LiveWallpagerActivity : CoreActivity() {

    companion object {
        private val PERMISSIONS_REQUEST_CAMERA = 454
        private val PERMISSION_CAMERA = Manifest.permission.CAMERA
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wall_pager)
        ARouter.getInstance().inject(this)

        wall_clear.setOnClickListener {
            WallpaperManager.getInstance(this@LiveWallpagerActivity).clear()
        }

        wall_live.setOnClickListener {
            checkSelfPermission()
        }
    }


    /**
     * 检查权限
     */
    fun checkSelfPermission() {
        if (ContextCompat.checkSelfPermission(this, PERMISSION_CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    arrayOf(PERMISSION_CAMERA),
                    PERMISSIONS_REQUEST_CAMERA)
        } else {
            startWallpaper()
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSIONS_REQUEST_CAMERA -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startWallpaper()
                } else {
                    Toast.makeText(this, getString(R.string._lease_open_permissions), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    /**
     * 选择壁纸
     */
    fun startWallpaper() {
        val pickWallpaper = Intent(Intent.ACTION_SET_WALLPAPER)
        val chooser = Intent.createChooser(pickWallpaper, getString(R.string.choose_wallpaper))
        startActivity(chooser)
    }

}