package com.kborid.ui.bottombar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * @description: 自定义底部navigation栏
 * @date: 2019/7/1
 * @author: duanwei
 * @email: duanwei@thunisoft.com
 */
public class NavigationBottomBar extends LinearLayout {

    private static final String TAG = NavigationBottomBar.class.getSimpleName();

    private int currentIndex = 0;

    public NavigationBottomBar(Context context) {
        this(context, null);
    }

    public NavigationBottomBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NavigationBottomBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOrientation(LinearLayout.HORIZONTAL);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View view = getChildAt(i);
            final int finalI = i;
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currentIndex != finalI) {
                        currentIndex = finalI;
                        if (null != onTabCheckedListener) {
                            onTabCheckedListener.onTabChecked(finalI);
                        }
                        setSelectedStatus(finalI);
                    }
                }
            });
        }
    }

    private void setSelectedStatus(int index) {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            getChildAt(i).setSelected(index == i);
        }
    }

    public void check(int index) {
        if (currentIndex != index) {
            currentIndex = index;
            setSelectedStatus(index);
        }
    }

    private OnTabCheckedListener onTabCheckedListener = null;

    public void setOnTabCheckedListener(OnTabCheckedListener listener) {
        onTabCheckedListener = listener;
    }

    public interface OnTabCheckedListener {
        void onTabChecked(int position);
    }
}
