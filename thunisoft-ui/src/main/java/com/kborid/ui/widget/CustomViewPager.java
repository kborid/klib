package com.kborid.ui.widget;

import android.content.Context;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @description: 增加是否支持滑动设置的自定义ViewPager
 * @date: 2019/8/8
 * @author: duanwei
 * @email: duanwei@thunisoft.com
 * @version: 1.0.0
 */
public class CustomViewPager extends ViewPager {


    private boolean isSlidingEnabled = true;

    public CustomViewPager(Context context) {
        super(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return this.isSlidingEnabled && super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return this.isSlidingEnabled && super.onInterceptTouchEvent(event);
    }

    public void setSlidingEnabled(boolean enable) {
        this.isSlidingEnabled = enable;
    }
}
