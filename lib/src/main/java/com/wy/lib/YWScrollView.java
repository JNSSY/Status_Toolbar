package com.wy.lib;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class YWScrollView extends ScrollView {

    private OnScrollChangedListener listener;

    public YWScrollView(Context context) {
        super(context);
    }

    public YWScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setOnScrollChangedListener(OnScrollChangedListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if (listener != null) {
            listener.onScrollChanged(this, x, y, oldx, oldy);
        }
    }
}
