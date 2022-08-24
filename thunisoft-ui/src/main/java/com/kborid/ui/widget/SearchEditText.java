package com.kborid.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatEditText;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.kborid.ui.R;
import com.kborid.ui.util.KeyboardUtils;

/**
 * SearchEditText
 *
 * @description: 自定义EditText，drawableLeft、hint居中显示
 * @date: 2019/8/8
 * @author: duanwei
 * @email: duanwei@thunisoft.com
 * @version: 1.0.0
 */
public class SearchEditText extends AppCompatEditText {

    private Context context;
    private Drawable drawableLeft; // icon图标
    private boolean isShowCenter;//是否居中显示icon，默认为不居中
    private boolean isOpen;//是否开启使用,默认为false

    private boolean isDraw = true;//是否绘制,配合居中显示使用
    private CharSequence hintText;

    public SearchEditText(Context context) {
        super(context);
        this.context = context;
        initView(null);
    }

    public SearchEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView(attrs);
    }

    public SearchEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView(attrs);
    }

    private void initView(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SearchEditText);
            isShowCenter = array.getBoolean(R.styleable.SearchEditText_isCenter, false);
            isOpen = array.getBoolean(R.styleable.SearchEditText_isOpen, true);
            array.recycle();
        }
        drawableLeft = getCompoundDrawables()[0];
        hintText = getHint();
        if (TextUtils.isEmpty(hintText)) {
            hintText = "";
        }
    }

    public void setKeyboardListener(View decorView) {
        KeyboardUtils.observeSoftKeyboard(decorView, new KeyboardUtils.OnSoftKeyboardChangeListener() {
            @Override
            public void onSoftKeyBoardChange(int softKeyboardHeight, boolean visible) {
                if (visible) {
                    setCursorVisible(true); // 显示光标
                    isDraw = false;
                }
            }
        });
    }

    public void resumeEditTextState() {
        if (!TextUtils.isEmpty(getText())) {
            setCursorVisible(true);
            isDraw = false;
            KeyboardUtils.showSoftKeyboard(this);
        }
    }

    public void resetEditTextState() {
        if (TextUtils.isEmpty(getText())) {
            setCursorVisible(false);
            isDraw = true;
            KeyboardUtils.hideSoftKeyboard(this);
            setFocusable(false);
            setFocusableInTouchMode(true);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        //如果不启用还使用EditText原来的，否则重新绘制
        if (!isOpen) {
            super.onDraw(canvas);
            return;
        }
        if (isShowCenter && isDraw) {// 将icon绘制在中间
            setCompoundDrawablesWithIntrinsicBounds(drawableLeft, null, null, null);//绘制图片
            float textWidth = getPaint().measureText(hintText.toString());//得到文字宽度
            int drawablePadding = getCompoundDrawablePadding();//得到drawablePadding宽度
            int drawableWidth = drawableLeft.getIntrinsicWidth();//得到图片宽度
            float bodyWidth = textWidth + drawableWidth + drawablePadding;//计算距离
            canvas.translate((getWidth() - bodyWidth - getPaddingLeft() - getPaddingRight()) / 2, 0);//最终绘制位置
            super.onDraw(canvas);
        } else {
            setCompoundDrawablesWithIntrinsicBounds(drawableLeft, null, null, null);
            super.onDraw(canvas);
        }
    }
}