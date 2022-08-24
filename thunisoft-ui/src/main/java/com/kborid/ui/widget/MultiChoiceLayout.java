package com.kborid.ui.widget;


import android.content.Context;
import android.util.AttributeSet;

import com.kborid.ui.R;


public class MultiChoiceLayout extends CheckedRelativeLayout {

    public MultiChoiceLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setImageResouce(R.drawable.iv_single_unchoiced, R.drawable.iv_single_choiced);
    }
}
