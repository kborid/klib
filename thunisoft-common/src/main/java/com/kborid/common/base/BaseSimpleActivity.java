package com.kborid.common.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * BaseSimpleActivity
 *
 * @description: 简单activity基类, 带ButterKnife
 * @date: 2019/10/25
 * @author: duanwei
 * @email: duanwei@thunisoft.com
 * @version: 1.0.0
 */
public abstract class BaseSimpleActivity extends AppCompatActivity {

    protected ViewModelProvider viewModelProvider;
    private Unbinder mUnBinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        mUnBinder = ButterKnife.bind(this);
        viewModelProvider = new ViewModelProvider(this);
        initDataAndEvent(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnBinder.unbind();
    }

    protected abstract int getLayoutResId();

    protected abstract void initDataAndEvent(Bundle savedInstanceState);

    protected <P extends ViewModel> P getViewModel(Class<P> clazz) {
        return viewModelProvider.get(clazz);
    }
}
