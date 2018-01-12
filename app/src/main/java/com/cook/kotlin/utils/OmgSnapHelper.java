package com.cook.kotlin.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by DE10035 on 2018/1/12.
 */

public class OmgSnapHelper extends PagerSnapHelper {

    @Override
    protected LinearSmoothScroller createSnapScroller(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager.canScrollVertically()) {
            return null;
        }
        return super.createSnapScroller(layoutManager);
    }

    @Nullable
    @Override
    public int[] calculateDistanceToFinalSnap(@NonNull RecyclerView.LayoutManager layoutManager, @NonNull View targetView) {
        if (layoutManager.canScrollVertically()) {
            int[] out = {0, 0};
            return out;
        }
        return super.calculateDistanceToFinalSnap(layoutManager, targetView);
    }
}
