package com.wy.lib;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

@TargetApi(Build.VERSION_CODES.M)
public class StatusBarUtils {
    private int mAlpha = 0;
    private Window window;
    private YWScrollView scrollView;
    private View toolbar;
    private View view_status;
    private ImageView iv_back;
    private View title;
    private View view;
    private Activity activity;
    private Bitmap bitmap_gray,bitmap_white;
    private boolean translucent_navigation;

    public StatusBarUtils(Activity activity, YWScrollView scrollView,View view_status, View toolbar, ImageView iv_back, View title, View view, boolean translucent_navigation) {
        this.activity = activity;
        this.window = activity.getWindow();
        this.scrollView = scrollView;
        this.view_status = view_status;
        this.toolbar = toolbar;
        this.iv_back = iv_back;
        this.title = title;
        this.view = view;
        this.translucent_navigation = translucent_navigation;
        transparencyBar();
        setStatus();
        bitmap_gray = BitmapFactory.decodeResource(activity.getResources(), R.mipmap.icon_left_arrow_gray);
        bitmap_white = BitmapFactory.decodeResource(activity.getResources(), R.mipmap.icon_left_arrow);
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
                    ((TextView) title).setTextColor(Color.GRAY);
                    iv_back.setImageBitmap(bitmap_gray);
                    toolbar.setBackgroundColor(initColor);
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        window.setStatusBarColor(Color.argb(255 - mAlpha, finalR, finalG, finalB));
                    }
                    ((TextView) title).setTextColor(Color.WHITE);
                    iv_back.setImageBitmap(bitmap_white);
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

    private void setStatus(){
        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) view_status.getLayoutParams();
        linearParams.height = getStatusBarHeight(activity.getBaseContext());
        view_status.setLayoutParams(linearParams);
    }
}
