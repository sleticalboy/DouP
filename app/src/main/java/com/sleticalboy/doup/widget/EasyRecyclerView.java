package com.sleticalboy.doup.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Android Studio.
 * Date: 12/31/17.
 *
 * @author sleticalboy
 */
public class EasyRecyclerView extends RecyclerView {

    public EasyRecyclerView(Context context) {
        super(context);
    }

    public EasyRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public EasyRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private float distanceX;
    private float distanceY;
    private float lastX;
    private float lastY;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = e.getX();
                lastY = e.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float curX = e.getX();
                float curY = e.getY();

                distanceX += Math.abs(curX - lastX);
                distanceY += Math.abs(curY - lastY);
                lastX = curX;
                lastY = curY;
                if (distanceX > distanceY) {
                    return false;
                }
                break;
            default:
                break;
        }
        return super.onInterceptTouchEvent(e);
    }
}
