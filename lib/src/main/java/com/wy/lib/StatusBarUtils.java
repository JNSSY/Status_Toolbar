package com.wy.lib;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

@TargetApi(Build.VERSION_CODES.M)
public class StatusBarUtils {
    private int mAlpha = 0;
    private Window window;
    private YWScrollView scrollView;
    private View toolbar;
    private View title;
    private View view;
    private boolean translucent_navigation;

    public StatusBarUtils(Window window, YWScrollView scrollView, View toolbar, View title, View view, boolean translucent_navigation) {
        this.window = window;
        this.scrollView = scrollView;
        this.toolbar = toolbar;
        this.title = title;
        this.view = view;
        this.translucent_navigation = translucent_navigation;
        transparencyBar();
    }


    public void setStatusBarColor(final int initColor, final int finalR, final int finalG, final int finalB) {
        scrollView.setOnScrollChangedListener(new OnScrollChangedListener() {
            @Override
            public void onScrollChanged(YWScrollView scrollView, int x, int y, int oldx, int oldy) {
                int minHeight = 50;
                int maxHeight = view.getMeasuredHeight();
                if (scrollView.getScrollY() <= minHeight) {
                    mAlpha = 0;
                } else if (scrollView.getScrollY() > maxHeight) {
                    mAlpha = 255;
                } else {
                    mAlpha = (scrollView.getScrollY() - minHeight) * 255 / (maxHeight - minHeight);
                }

                if (mAlpha == 0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        window.setStatusBarColor(Color.argb(255 - mAlpha, finalR, finalG, finalB));
                    }
                    toolbar.setBackgroundColor(Color.argb(255 - mAlpha, finalR, finalG, finalB));
                } else if (mAlpha >= 255) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        window.setStatusBarColor(initColor);
                    }
                    ((TextView)title).setTextColor(Color.GRAY);
                    toolbar.setBackgroundColor(initColor);
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        window.setStatusBarColor(Color.argb(255 - mAlpha, finalR, finalG, finalB));
                    }
                    ((TextView)title).setTextColor(Color.WHITE);
                    toolbar.setBackgroundColor(Color.argb(255 - mAlpha, finalR, finalG, finalB));
                }

                setAndroidNativeLightStatusBar(true);
            }
        });
    }


    public int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier(
                "status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    /**
     * 是否透明导航栏
     */
    private void transparencyBar() {
        if (Build.VERSION.SDK_INT >= 21) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            if (translucent_navigation) {
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            }
            window.setStatusBarColor(Color.TRANSPARENT);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        } else if (Build.VERSION.SDK_INT >= 19) {
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * 状态栏文字颜色是否黑色
     *
     * @param dark
     */

    private void setAndroidNativeLightStatusBar(boolean dark) {
        View decor = window.getDecorView();
        if (dark) {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }
}
