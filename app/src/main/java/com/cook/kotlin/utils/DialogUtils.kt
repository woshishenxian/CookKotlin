package com.cook.kotlin.utils

import android.content.Context
import android.content.DialogInterface
import android.support.v7.app.AlertDialog

/**
 * Created by DE10035 on 2017/12/22.
 */

object DialogUtils {

    fun showRemoveDialog(context: Context, listener:DialogInterface.OnClickListener) {
        AlertDialog.Builder(context).setTitle("最近观看漫画")
                .setMessage("确定要删除此条记录吗？")
                .setNegativeButton("取消", DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() })
                .setPositiveButton("确定", listener).show()
    }

    fun showClearDialog(context: Context, listener:DialogInterface.OnClickListener) {
        AlertDialog.Builder(context).setTitle("最近观看漫画")
                .setMessage("确定要清空观看记录吗？")
                .setNegativeButton("取消", DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() })
                .setPositiveButton("确定", listener).show()
    }

}