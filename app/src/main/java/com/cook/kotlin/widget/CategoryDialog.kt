package com.cook.kotlin.widget

import android.app.Dialog
import android.content.Context
import android.view.View
import com.cook.kotlin.cookkotlin.R
import com.flyco.animation.FlipEnter.FlipVerticalSwingEnter
import com.flyco.dialog.widget.base.BottomBaseDialog

/**
 * Created by DE10035 on 2017/12/20.
 */
class CategoryDialog(context: Context,val listener:OnItemClickListener):BottomBaseDialog<CategoryDialog>(context){


    override fun onCreateView(): View {
        showAnim(FlipVerticalSwingEnter())
        dismissAnim(null)
        return View.inflate(context, R.layout.dialog_category,null)
    }

    override fun setUiBeforShow() {
        findViewById(R.id.item_wx).setOnClickListener { listener.onItemClick(0,this@CategoryDialog) }
        findViewById(R.id.item_comic).setOnClickListener { listener.onItemClick(1,this@CategoryDialog) }
    }

    interface OnItemClickListener{
        fun onItemClick(position:Int, dialog: Dialog)
    }
}