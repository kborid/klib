package com.kborid.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Build;
import androidx.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kborid.ui.R;

/**
 * ProgressDialog
 *
 * @description: progress dialog
 * @date: 2019/9/25
 * @author: duanwei
 * @email: duanwei@thunisoft.com
 * @version: 2.0.0
 */
public class ProgressDialog extends Dialog {
    private ProgressBar loadingPB;
    private TextView tipsTV;


    public ProgressDialog(final Context context) {
        this(context, R.style.dialogStyle_Progress);
    }

    public ProgressDialog(Context context, int theme) {
        super(context, theme);
        setContentView(R.layout.dialog_progress);
        tipsTV = (TextView) findViewById(R.id.tv_tips);
        loadingPB = (ProgressBar) findViewById(R.id.pb_loading);
    }

    public void setMessageShow(boolean isShow) {
        if (null != tipsTV) {
            if (isShow) {
                tipsTV.setVisibility(View.VISIBLE);
            } else {
                tipsTV.setVisibility(View.GONE);
            }
        }
    }

    public void setMessage(String msg) {
        if (null != tipsTV) {
            setMessageShow(!TextUtils.isEmpty(msg));
            tipsTV.setText(msg);
        }
    }

    public void setMessage(int msgId) {
        setMessage(getContext().getString(msgId));
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    public void show() {
        super.show();
        System.gc();
    }

    /**
     * 设置mark and tips颜色
     *
     * @param color
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setMainColor(int color) {
        setMarkColor(color);
        setMessageColor(color);
    }

    /**
     * 设置Tips颜色
     *
     * @param color
     */
    public void setMessageColor(int color) {
        if (null != tipsTV) {
            tipsTV.setTextColor(color);
        }
    }

    /**
     * 设置旋转颜色
     *
     * @param color
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setMarkColor(int color) {
        if (null != loadingPB) {
            loadingPB.setIndeterminateTintList(ColorStateList.valueOf(color));
        }
    }

    /**
     * 设置多个旋转颜色
     *
     * @param colorList
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setMarkColorList(ColorStateList colorList) {
        if (null != loadingPB) {
            loadingPB.setIndeterminateTintList(colorList);
        }
    }

    /**
     * 设置mark图片，must be xml
     *
     * @param drawable
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setMarkDrawable(int drawable) {
        if (null != loadingPB) {
            loadingPB.setIndeterminateDrawable(getContext().getDrawable(drawable));
        }
    }

}
