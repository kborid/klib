package com.kborid.common.base;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * BaseSimpleFragment
 *
 * @description: 简单fragment基类封装, 带ButterKnife
 * @date: 2019/9/25
 * @author: duanwei
 * @email: duanwei@thunisoft.com
 * @version: 1.0.0
 */
public abstract class BaseSimpleFragment extends Fragment {

    private static final String IS_SUPPORT_HIDE_STATE = "IS_SUPPORT_HIDE_STATE";

    protected View mRootView;
    private Unbinder mUnBinder;
    private boolean isInitialized = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(getLayoutResId(), null);
        return mRootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnBinder = ButterKnife.bind(this, view);
        if (savedInstanceState == null) {
            if (!isHidden()) {
                isInitialized = true;
                initEventAndData(null);
            }
        } else {
            boolean isSupportHidden = savedInstanceState.getBoolean(IS_SUPPORT_HIDE_STATE);
            if (!isSupportHidden) {
                isInitialized = true;
                initEventAndData(savedInstanceState);
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(IS_SUPPORT_HIDE_STATE, isHidden());
    }

    /**
     * 通过FragmentTransaction的hide、show方法，切换时回调该方法
     *
     * @param hidden
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!isInitialized && !hidden) {
            isInitialized = true;
            initEventAndData(null);
        }
    }

    /**
     * Fragment与ViewPager结合，切换时回调该方法
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isInitialized && isVisibleToUser) {
            isInitialized = true;
            initEventAndData(null);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnBinder.unbind();
    }

    protected abstract int getLayoutResId();

    protected abstract void initEventAndData(@Nullable Bundle savedInstanceState);
}
