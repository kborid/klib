package com.kborid.common.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

/**
 * @description: base fragment dialog
 * @date: 2019/7/3
 * @author: duanwei
 * @email: duanwei@thunisoft.com
 */
public abstract class BaseFragmentDialog extends DialogFragment {

    protected View mView;
    protected Context mContext;

    @SuppressLint("ResourceType")
    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        if (null != window) {
            window.setBackgroundDrawableResource(Color.TRANSPARENT);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(getLayoutResId(), container, false);
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initEventAndData();
    }

    protected abstract int getLayoutResId();

    protected abstract void initEventAndData();

    public void show(FragmentManager fm) {
        show(fm, getClass().getSimpleName());
    }

    public void onDismiss() {
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        onDismiss();
    }
}
