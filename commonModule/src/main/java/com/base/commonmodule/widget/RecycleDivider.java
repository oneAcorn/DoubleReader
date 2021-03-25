package com.base.commonmodule.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import androidx.annotation.ColorRes;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

/**
 * Author:      Lee Yeung
 * Create Date: 2018/2/8
 * Description: Recycle分割线
 */
public class RecycleDivider extends RecyclerView.ItemDecoration {
    private Paint paint;
    private Context context;
    private int orientation;
    private int dividerHeight = 1;
    private boolean isVisibleDivider = true;
    private boolean isDrawLastDivider;
    private boolean isDrawHeaderDivider;

    public RecycleDivider(Context context) {
        this(context, LinearLayoutManager.VERTICAL);
    }

    public RecycleDivider(Context context, int orientation) {
        if (context == null) {
            throw new IllegalArgumentException("context is null！");
        }

        if (orientation != LinearLayoutManager.VERTICAL && orientation != LinearLayoutManager.HORIZONTAL) {
            throw new IllegalArgumentException("Please enter the correct parameters！");
        }

        this.context = context;
        this.orientation = orientation;

        initPaint();
    }

    private void initPaint() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.TRANSPARENT);
    }

    public void setDividerHeight(int dividerHeight) {
        this.dividerHeight = dividerHeight;
    }

    public int getDividerHeight() {
        return dividerHeight;
    }

    public void setDividerColor(@ColorRes int dividerColor) {
        paint.setColor(ContextCompat.getColor(context, dividerColor));
    }

    /**
     * 设置分割线是否可见
     *
     * @param isVisibleDivider
     */
    public void setVisibleDivider(boolean isVisibleDivider) {
        this.isVisibleDivider = isVisibleDivider;
    }

    /**
     * 是否画最后一条分割线
     *
     * @param isDrawLastDivider
     */
    public void setDrawLastDivider(boolean isDrawLastDivider) {
        this.isDrawLastDivider = isDrawLastDivider;
    }

    public void setDrawHeaderDivider(boolean isDrawHeaderDivider) {
        this.isDrawHeaderDivider = isDrawHeaderDivider;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (orientation == LinearLayoutManager.VERTICAL) {
            outRect.set(0, 0, 0, dividerHeight);
            if (isDrawHeaderDivider && parent.getChildAdapterPosition(view) == 0) {
                outRect.top = dividerHeight;
            }
        } else {
            outRect.set(0, 0, dividerHeight, 0);
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (isVisibleDivider) {
            if (orientation == LinearLayoutManager.VERTICAL) {
                drawVertical(c, parent);
            } else {
                drawHorizontal(c, parent);
            }
        }
    }

    private void drawHorizontal(Canvas canvas, RecyclerView parent) {
        int childCount = parent.getChildCount();
        View child;
        if (!isDrawLastDivider) {
            childCount = childCount - 1;
        }

        for (int i = 0; i < childCount; i++) {
            child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            int left = child.getRight() + layoutParams.rightMargin;
            int right = left + dividerHeight;
            canvas.drawRect(left, child.getTop(), right, child.getBottom(), paint);
        }
    }

    private void drawVertical(Canvas canvas, RecyclerView parent) {
        int childCount = parent.getChildCount();

        View child;
        if (!isDrawLastDivider) {
            childCount = childCount - 1;
        }

        for (int i = 0; i < childCount; i++) {
            child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            int top = child.getBottom() + layoutParams.bottomMargin;
            int bottom = top + dividerHeight;
            canvas.drawRect(child.getLeft(), top, child.getRight(), bottom, paint);
        }
    }
}
