package com.vincee.wallpager

import android.media.MediaPlayer
import android.service.wallpaper.WallpaperService
import android.view.SurfaceHolder
import java.io.IOException


/**
 * Created by DE10035 on 2018/3/13.
 *
 *
 */
class VideoLiveWallpager : WallpaperService() {
    override fun onCreateEngine(): Engine {
        return VideoEngine()
    }


    inner class VideoEngine() : Engine() {
        private var mMediaPlayer: MediaPlayer? = null

        override fun onSurfaceCreated(holder: SurfaceHolder?) {
            super.onSurfaceCreated(holder)
            mMediaPlayer = MediaPlayer()
            mMediaPlayer?.setSurface(holder?.surface)
            try {
                val assetMg = applicationContext.assets
                val fileDescriptor = assetMg.openFd("test1.mp4")
                mMediaPlayer?.setDataSource(fileDescriptor.fileDescriptor,
                        fileDescriptor.startOffset, fileDescriptor.length)
                mMediaPlayer?.setLooping(true)
                mMediaPlayer?.setVolume(0f, 0f)
                mMediaPlayer?.prepare()
                mMediaPlayer?.start()

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        override fun onVisibilityChanged(visible: Boolean) {
            if (visible) {
                mMediaPlayer?.start()
            } else {
                mMediaPlayer?.pause()
            }
        }


        override fun onSurfaceDestroyed(holder: SurfaceHolder?) {
            super.onSurfaceDestroyed(holder)
            mMediaPlayer?.release()
            mMediaPlayer = null
        }
    }


}