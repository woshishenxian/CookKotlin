package com.cook.kotlin.service

import android.hardware.Camera
import android.service.wallpaper.WallpaperService
import android.view.SurfaceHolder


/**
 * Created by DE10035 on 2018/3/13.
 *
 *
 */
class CameraLiveWallpager : WallpaperService() {
    override fun onCreateEngine(): Engine {
        return CameraEngine()
    }


    inner class CameraEngine() : Engine(), Camera.PreviewCallback {

        var camera: Camera? = null

        override fun onCreate(surfaceHolder: SurfaceHolder?) {
            super.onCreate(surfaceHolder)
            startPreview()
        }

        override fun onDestroy() {
            super.onDestroy()
            stopPreview()
        }

        override fun onVisibilityChanged(visible: Boolean) {
            if (visible) {
                startPreview()
            } else {
                stopPreview()
            }
        }

        fun startPreview() {
            camera = Camera.open()
            camera?.setDisplayOrientation(90)
            camera?.setPreviewDisplay(surfaceHolder)
            camera?.startPreview()
        }

        fun stopPreview() {
            camera?.stopPreview()
            camera?.setPreviewCallback(null)
            camera?.release()
            camera = null
        }


        override fun onPreviewFrame(data: ByteArray?, camera: Camera?) {
            camera?.addCallbackBuffer(data)
        }
    }
}