package com.cook.kotlin.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cook.kotlin.cookkotlin.R;


/**
 * Created by Vince.liu on 2018/1/4.
 *
 */

public class FastScrollBar extends LinearLayout {

    private ImageView imageView;

    private RecyclerView recyclerView;
    private boolean manuallyChangingPosition;

    private boolean isVertical;


    public FastScrollBar(@NonNull Context context) {
        this(context, null);
    }

    public FastScrollBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FastScrollBar(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs == null)
            return;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FastScrollBar);
        Drawable drawable = a.getDrawable(R.styleable.FastScrollBar_src);
        a.recycle();

        isVertical = getOrientation() == VERTICAL;

        imageView = new ImageView(context);
        imageView.setImageDrawable(drawable);
        addView(imageView);
        initHandleMovement();
    }

    /**
     * 初始化 滚动条的手势
     */
    private void initHandleMovement() {
        imageView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
                    manuallyChangingPosition = true;
                    float delta = isVertical ? (event.getY() - v.getHeight() / 2) : (event.getX() - v.getWidth() / 2);
                    setScrollerPosition(delta);
                    setRecyclerViewPosition(isVertical ? v.getTranslationY() : v.getTranslationX());
                    return true;

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    manuallyChangingPosition = false;
                    return true;

                }
                return false;
            }
        });
    }

    /**
     * 更新滚动条的显示状态
     */
    public void invalidateVisibility() {
        if (isRecyclerViewScrollable()) {
            setVisibility(VISIBLE);
        } else {
            setVisibility(INVISIBLE);
        }
    }

    /**
     * @param position 滚动到RecyclerView item位置
     */
    public void scrollToPosition(int position) {
        if (recyclerView == null || recyclerView.getLayoutManager() == null)
            return;
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            layoutManager.scrollToPositionWithOffset(position, 0);
            recyclerView.post(new Runnable() {
                @Override
                public void run() {
                    if (getRecyclerScrollRange() != 0) {
                        setScrollerPosition(getRecyclerOffset() * getScrollRange() / getRecyclerScrollRange());
                    }
                }
            });
        }
    }

    /**
     * 设置RecyclerView 的位置
     *
     * @param delta 偏移幅度
     */
    private void setRecyclerViewPosition(float delta) {
        if (recyclerView == null || getScrollRange() == 0) return;
        int d = (int) (delta * getRecyclerScrollRange() / getScrollRange());
        if (isVertical) {
            recyclerView.scrollBy(0, d - recyclerView.computeVerticalScrollOffset());
        } else {
            recyclerView.scrollBy(d - recyclerView.computeVerticalScrollOffset(), 0);

        }
    }

    /**
     * 设置滚动条的位置
     *
     * @param delta 滚动幅度
     */
    private void setScrollerPosition(float delta) {
        if (isVertical) {
            float offset = Math.min(getScrollRange(), Math.max(0, delta + imageView.getTranslationY()));
            imageView.setTranslationY(offset);
        } else {
            float offset = Math.min(getScrollRange(), Math.max(0, delta + imageView.getTranslationX()));
            imageView.setTranslationX(offset);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        adjustScrollerPosition(w, h, oldw, oldh);
    }

    /**
     * 当屏幕大小调整时，调整滚动条
     *
     * @param w    Current width of this view.
     * @param h    Current height of this view.
     * @param oldw Old width of this view.
     * @param oldh Old height of this view.
     */
    private void adjustScrollerPosition(int w, int h, int oldw, int oldh) {
        if (oldw == 0 || oldh == 0 || w == 0 || h == 0)
            return;
        if (isVertical) {
            imageView.setTranslationY(recyclerView.computeVerticalScrollOffset() * getScrollRange() / getRecyclerScrollRange());
        } else {
            imageView.setTranslationX(recyclerView.computeHorizontalScrollOffset() * getScrollRange() / getRecyclerScrollRange());
        }
    }

    /**
     * 判断是否需要更新滚动条的位置
     *
     * @return boolean
     */
    private boolean shouldUpdateHandlePosition() {
        return !manuallyChangingPosition && recyclerView.getChildCount() > 0;
    }

    private int getRecyclerOffset() {
        return isVertical ? recyclerView.computeVerticalScrollOffset() : recyclerView.computeHorizontalScrollOffset();
    }

    /**
     * 判断RecyclerView 是否能够滚动
     *
     * @return boolean
     */
    private boolean isRecyclerViewScrollable() {
        if (recyclerView == null)
            return false;
        if (isVertical) {
            return recyclerView.computeVerticalScrollRange() > recyclerView.computeVerticalScrollExtent();
        }
        return recyclerView.computeHorizontalScrollRange() > recyclerView.computeHorizontalScrollExtent();
    }


    /**
     * FastVerticalScrollBar 的滚动范围
     *
     * @return int
     */
    final public int getScrollRange() {
        if (imageView == null)
            return isVertical ? getHeight() : getWidth();
        if (isVertical) {
            return getHeight() - imageView.getHeight() - getPaddingTop() - getPaddingBottom();
        }
        return getWidth() - imageView.getWidth() - getPaddingLeft() - getPaddingRight();
    }


    /**
     * RecyclerView 的滚动范围
     *
     * @return int
     */
    final public int getRecyclerScrollRange() {
        if (isVertical) {
            return recyclerView.computeVerticalScrollRange() - recyclerView.computeVerticalScrollExtent();
        }
        return recyclerView.computeHorizontalScrollRange() - recyclerView.computeHorizontalScrollExtent();

    }


    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        this.recyclerView.addOnScrollListener(new RecyclerViewScrollListener());
    }


    private class RecyclerViewScrollListener extends RecyclerView.OnScrollListener {

        private boolean invalidateVisibility;

        private RecyclerViewScrollListener() {
        }

        @Override
        public void onScrolled(RecyclerView rv, int dx, int dy) {
            if (!invalidateVisibility) {
                invalidateVisibility = true;
                invalidateVisibility();
            }
            if (shouldUpdateHandlePosition()) {
                updateHandlePosition(isVertical ? dy : dx);
            }

        }

        void updateHandlePosition(int dy) {
            if (getRecyclerScrollRange() != 0) {
                setScrollerPosition(dy * getScrollRange() / getRecyclerScrollRange());
            }
        }
    }
}
