package com.cook.kotlin.widget;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.cook.kotlin.cookkotlin.R;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

public class ProgressBarHelper {


    public static void show(Activity ac) {
        View view = inflate(ac);
        if (view.getVisibility() == View.INVISIBLE
                || view.getVisibility() == View.GONE) {
            view.setVisibility(View.VISIBLE);
        }
    }

    public static void hide(Activity ac) {
        final View view = getInflatedView(ac);
        if (view != null && view.getVisibility() == View.VISIBLE) {
            view.postDelayed(new Runnable() {
                @Override
                public void run() {
                    view.setVisibility(View.GONE);
                }
            },500);
        }
    }

    private static View inflate(Activity ac) {
        if (!isInflated(ac)) {
            View.inflate(ac, R.layout.progress_bar, (ViewGroup) ac.findViewById(android.R.id.content));
        }
        return getInflatedView(ac);
    }

    private static boolean isInflated(Activity ac) {
        return ac.findViewById(R.id.progressBar) != null;
    }

    private static View getInflatedView(Activity ac) {
        return ac.findViewById(R.id.progressBar);
    }
}
