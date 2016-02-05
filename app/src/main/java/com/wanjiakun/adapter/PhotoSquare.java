package com.wanjiakun.adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Wanjiakun on 3/15/2015.
 */
public class PhotoSquare extends ImageView {
    public PhotoSquare(Context context) {
        super(context);
    }

    public PhotoSquare(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PhotoSquare(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
