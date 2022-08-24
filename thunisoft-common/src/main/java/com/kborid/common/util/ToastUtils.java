package com.kborid.common.util;

import android.widget.Toast;

import com.kborid.common.ThunisoftCommon;


public class ToastUtils {

    private static Toast mToast;

    public static void showToast(String msg, int duration) {
        if (null != mToast) {
            mToast.cancel();
            mToast = null;
        }

        mToast = Toast.makeText(ThunisoftCommon.getContext(), msg, duration);
        mToast.show();
    }

    public static void showToast(int stringId, int duration) {
        showToast(ThunisoftCommon.getContext().getString(stringId), duration);
    }

    public static void showToast(String msg) {
        showToast(msg, Toast.LENGTH_SHORT);
    }

    public static void showToast(int stringId) {
        showToast(stringId, Toast.LENGTH_SHORT);
    }
}
