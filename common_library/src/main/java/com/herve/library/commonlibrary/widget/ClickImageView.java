package com.herve.library.commonlibrary.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatImageView;

/**
 * @author by DELL
 * @date on 2017/12/16
 * @describe
 */

public class ClickImageView extends AppCompatImageView {

    public ClickImageView(Context context) {
        super(context);
        setClickable(true);
    }

    public ClickImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setClickable(true);
    }

    public ClickImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setClickable(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                this.setColorFilter(0x99000000);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                this.setColorFilter(null);
                break;
        }
        return super.onTouchEvent(event);
    }
}