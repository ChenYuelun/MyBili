package com.example.chenyuelun.mybili.view.view;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by chenyuelun on 2017/7/19.
 */

public class GridView extends android.widget.GridView {
    public GridView(Context context) {
        this(context,null);
    }

    public GridView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public GridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



}
