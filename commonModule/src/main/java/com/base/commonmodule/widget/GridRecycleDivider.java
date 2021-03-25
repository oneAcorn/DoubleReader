package com.base.commonmodule.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import androidx.annotation.ColorRes;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

/**
 * @author ChenChong on 2018/8/2 15:22
 */
public class GridRecycleDivider extends RecyclerView.ItemDecoration {
    private Paint paint;
    private boolean isVisibleDivider;
    private int horizontalSpace = 1;
    private int verticalSpace = 1;
    private int mSpanCount;
    private boolean isDrawLastItemRight = true;

    public void setHorizontalSpace(int horizontalSpace) {
        this.horizontalSpace = horizontalSpace;
    }

    public void setVerticalSpace(int verticalSpace) {
        this.verticalSpace = verticalSpace;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (mSpanCount == 0) {
            RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
            if (layoutManager instanceof GridLayoutManager) {
                mSpanCount = ((GridLayoutManager) layoutManager).getSpanCount();
            }
        }

        if (mSpanCount > 0) {
            final int childPosition = parent.getChildLayoutPosition(view);
            if (childPosition % mSpanCount != 0) {
                outRect.left = horizontalSpace;

                // TODO: 2018/8/20 处理最后一个itemview没有正好是在最右列的时候，最后一个itemview的右侧也需要画线处理
                if (isDrawLastItemRight) {
                    final int itemCount = parent.getAdapter().getItemCount();
                    if (childPosition == itemCount - 1) {
                        if ((childPosition % mSpanCount) < (mSpanCount - 1)) {
                            outRect.right = horizontalSpace;
                        }
                    }
                }
            }

            outRect.bottom = verticalSpace;
        }
    }

    public void setVisibleDivider(boolean visibleDivider) {
        isVisibleDivider = visibleDivider;
        if (visibleDivider) {
            initPaint();
        }
    }

    public void setDividerColor(Context context, @ColorRes int dividerColor) {
        if (paint == null) {
            initPaint();
        }

        paint.setColor(ContextCompat.getColor(context, dividerColor));
    }

    private void initPaint() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.TRANSPARENT);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (isVisibleDivider) {
            if (verticalSpace > 0) {
                drawVertical(c, parent);
            }

            if (horizontalSpace > 0) {
                drawHorizontal(c, parent);
            }
        }
    }

    private void drawVertical(Canvas canvas, RecyclerView parent) {
        int childCount = parent.getChildCount();
        View child;
        if (isVisibleDivider) {
            for (int i = 0; i < childCount; i++) {
                child = parent.getChildAt(i);
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
                int top = child.getBottom() + layoutParams.bottomMargin;
                int bottom = top + verticalSpace;

                /**
                 * ---------
                 * |
                 * |
                 * |
                 * |
                 * --------     ：填充不满
                 * ---------    ：填充满
                 *
                 * 参数right：child.getRight() + horizontalSpace
                 * 追加horizontalSpace偏移量，防止右下角出现无法填充满的情况
                 *
                 */
                canvas.drawRect(child.getLeft(), top, child.getRight() + horizontalSpace, bottom, paint);
            }
        }
    }

    private void drawHorizontal(Canvas canvas, RecyclerView parent) {
        int childCount = parent.getChildCount();
        View child;
        if (isVisibleDivider) {
            for (int i = 0; i < childCount; i++) {
                if (i % mSpanCount != 0) {
                    // TODO: 2018/8/21 不是第一列画左侧线
                    child = parent.getChildAt(i);
                    RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
                    int left = child.getLeft() + layoutParams.leftMargin;
                    int right = left + horizontalSpace;
                    canvas.drawRect(left, child.getTop(), right, child.getBottom(), paint);
                }

                if (isDrawLastItemRight) {
                    final int itemCount = parent.getAdapter().getItemCount();
                    if (i == itemCount - 1) {
                        if ((i % mSpanCount) < (mSpanCount - 1)) {
                            // TODO: 2018/8/21 最后一个item，并且没有不是最右列需要画右侧的线，用来闭合
                            child = parent.getChildAt(i);
                            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
                            final int left_offset = child.getRight() + layoutParams.rightMargin;
                            final int right_offset = left_offset + horizontalSpace;
                            canvas.drawRect(left_offset, child.getTop(), right_offset, child.getBottom(), paint);
                        }
                    }
                }
            }
        }
    }
}
