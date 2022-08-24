package com.kborid.ui.bottombar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kborid.ui.R;
import com.kborid.ui.util.ScreenUtils;

/**
 * @description: 自定义底部navigation栏item
 * @date: 2019/7/1
 * @time: 17:49
 * @author: duanwei
 * @email: duanwei@thunisoft.com
 */
public class NavigationBottomItem extends RelativeLayout {

    private static final String TAG = NavigationBottomItem.class.getSimpleName();

    private TextView tv_msg_count;

    private Drawable mDrawableTop;
    private String mTitle;
    private int mIconSize;
    private int mDrawablePadding;


    public NavigationBottomItem(Context context) {
        this(context, null);
    }

    public NavigationBottomItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NavigationBottomItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.NavigationBottomItem);
        mDrawableTop = ta.getDrawable(R.styleable.NavigationBottomItem_drawableTop);
        mTitle = ta.getString(R.styleable.NavigationBottomItem_bottomItemTitle);
        mIconSize = (int) ta.getDimension(R.styleable.NavigationBottomItem_iconSize, ScreenUtils.dp2px(20));
        mDrawablePadding = (int) ta.getDimension(R.styleable.NavigationBottomItem_drawablePadding, 0);
        ta.recycle();
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_item_bottom_navi, this);
        ImageView iv_nav_icon = (ImageView) findViewById(R.id.iv_nav_icon);
        TextView tv_nav_name = (TextView) findViewById(R.id.tv_nav_name);
        if (!TextUtils.isEmpty(mTitle)) {
            tv_nav_name.setText(mTitle);
        }
        LinearLayout.LayoutParams iconLlp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        iconLlp.width = mIconSize;
        iconLlp.height = mIconSize;
        iconLlp.bottomMargin = mDrawablePadding;
        iv_nav_icon.setLayoutParams(iconLlp);
        iv_nav_icon.setImageDrawable(mDrawableTop);
        tv_msg_count = (TextView) findViewById(R.id.tv_msg_count);
    }

    public void setMsgCount(int count) {
        if (count <= 0) {
            tv_msg_count.setVisibility(GONE);
        } else {
            tv_msg_count.setVisibility(VISIBLE);
            if (count <= 99) {
                tv_msg_count.setText(String.valueOf(count));
            } else {
                tv_msg_count.setText("99+");
            }
        }
    }
}
