package com.kborid.ui.title;

import android.content.Context;
import android.content.res.TypedArray;
import com.google.android.material.appbar.AppBarLayout;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.kborid.ui.R;
import com.kborid.ui.util.ScreenUtils;

/**
 * MainTitleLayout
 *
 * @description: 基于AppBarLayout与ToolBar，自定义title layout
 * @date: 2019/10/10
 * @author: duanwei
 * @email: duanwei@thunisoft.com
 * @version: 1.0.0
 */
public class MainTitleLayout extends AppBarLayout {

    private static final int ELLIPSIZE_END = 0;
    private static final int ELLIPSIZE_MARQUEE = 1;

    private Toolbar toolbar;
    private TextView titleTV;

    /**
     * 自定义属性值
     */
    private float mMainTitleSize; //主标题字体大小, px
    private int mMainTitleColor; //主标题颜色
    private int mRightMenuResId; //右侧菜单，layout id
    private int mEllipsize = ELLIPSIZE_END;

    public MainTitleLayout(Context context) {
        this(context, null);
    }

    public MainTitleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.layout_main_title, this);
        toolbar = findViewById(R.id.toolbar);
        titleTV = findViewById(R.id.title);
        parseAttr(context, attrs);
        init();
    }

    private void init() {
        titleTV.setTextSize(TypedValue.COMPLEX_UNIT_PX, mMainTitleSize);
        titleTV.setTextColor(mMainTitleColor);
        if (-1 != mRightMenuResId) {
            toolbar.inflateMenu(mRightMenuResId);
        }
        openMarqueeEffect(ELLIPSIZE_MARQUEE == mEllipsize);
    }

    /**
     * 解析attrs、设置
     *
     * @param context
     * @param attrs
     */
    private void parseAttr(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MainTitleLayout);
        mMainTitleSize = ta.getDimension(R.styleable.MainTitleLayout_mainTitleSize, ScreenUtils.sp2px(20));
        mMainTitleColor = ta.getColor(R.styleable.MainTitleLayout_mainTitleColor, getResources().getColor(R.color.black));
        mRightMenuResId = ta.getResourceId(R.styleable.MainTitleLayout_rightLayoutId, -1);
        mEllipsize = ta.getInt(R.styleable.MainTitleLayout_ellipsize, mEllipsize);
        ta.recycle();
    }

    /**
     * 开启跑马灯效果
     */
    public void openMarqueeEffect(boolean openMarquee) {
        if (openMarquee) {
            titleTV.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            titleTV.setSingleLine(true);
            titleTV.setSelected(true);
            titleTV.setFocusable(true);
            titleTV.setFocusableInTouchMode(true);
        } else {
            titleTV.setEllipsize(TextUtils.TruncateAt.END);
            titleTV.setSingleLine(true);
        }
    }

    /**
     * 设置标题
     *
     * @param resId
     */
    public void setTitle(int resId) {
        setTitle(getContext().getString(resId));
    }

    public void setTitle(String str) {
        if (null != titleTV) {
            titleTV.setText(str);
        }
    }

    /**
     * 设置左侧导航按钮
     *
     * @param resId
     * @param listener
     */
    public void setNavigator(int resId, OnClickListener listener) {
        if (null != toolbar) {
            toolbar.setNavigationIcon(resId);
            toolbar.setNavigationOnClickListener(listener);
        }
    }

    /**
     * 设置右侧menu菜单项
     *
     * @param listener
     */
    public void setOnMenuItemClickListener(Toolbar.OnMenuItemClickListener listener) {
        if (null != toolbar) {
            toolbar.setOnMenuItemClickListener(listener);
        }
    }
}
