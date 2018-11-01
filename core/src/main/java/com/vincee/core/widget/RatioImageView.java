/*
 * @(#)RatioImageView.class $version 2014. 5. 13.
 *
 * Copyright 2014 Naver Corp. All rights Reserved.
 * NAVER PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.vincee.core.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.vincee.core.R;


/**
 * 가로세로비를 유지하는 {@link ImageView}의 확장 클래스.
 *
 * ratio =  width/height;
 * ex)ratio ==  0.81 // 세로가 길다.
 * ex)ratio ==  1.22 // 가로가 길다.
 * @author answer
 */
public class RatioImageView extends AppCompatImageView {
    private float ratio;

    public RatioImageView(Context context) {
        this(context, null);
    }

    public RatioImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RatioImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RatioView);
        ratio = a.getFloat(R.styleable.RatioView_ratio, 1.0f); // default 1 로 해야하지않을까
        a.recycle();
    }

    public float getRatio() {
        return ratio;
    }

    public void setRatio(float ratio) {
       this.ratio = ratio;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode( widthMeasureSpec );
        int widthSize = widthMode == MeasureSpec.UNSPECIFIED ? Integer.MAX_VALUE : MeasureSpec.getSize( widthMeasureSpec );
        int heightMode = MeasureSpec.getMode( heightMeasureSpec );
        int heightSize = heightMode == MeasureSpec.UNSPECIFIED ? Integer.MAX_VALUE : MeasureSpec.getSize( heightMeasureSpec );

        int measuredWidth;
        int measuredHeight;

        if (heightMode == MeasureSpec.EXACTLY && widthMode == MeasureSpec.EXACTLY ) {
            measuredWidth = widthSize;
            measuredHeight = heightSize;
        } else if (heightMode == MeasureSpec.EXACTLY) {
            measuredWidth = (int) (heightSize * ratio);
            measuredHeight = heightSize;
        } else if (widthMode == MeasureSpec.EXACTLY) {
            measuredHeight = (int) (widthSize * ratio);
            measuredWidth = widthSize;
        } else {
            measuredWidth = widthSize;
            measuredHeight = (int) (measuredWidth / ratio);
        }
        setMeasuredDimension(measuredWidth, measuredHeight);
    }
}
