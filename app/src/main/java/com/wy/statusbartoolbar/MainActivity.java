package com.wy.statusbartoolbar;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wy.lib.StatusBarUtils;
import com.wy.lib.YWScrollView;

@RequiresApi(api = Build.VERSION_CODES.M)
public class MainActivity extends AppCompatActivity {
    private YWScrollView scrollview;
    private LinearLayout ll_toolbar;
    private ImageView iv;
    private StatusBarUtils barUtils;
    private View view_status;
    private TextView tv_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scrollview = findViewById(R.id.scrollview);
        view_status = findViewById(R.id.view_status);
        tv_title = findViewById(R.id.tv_title);
        ll_toolbar = findViewById(R.id.ll_toolbar);
        iv = findViewById(R.id.iv);

        barUtils = new StatusBarUtils(getWindow(), scrollview, ll_toolbar, tv_title,iv, true);
        barUtils.setStatusBarColor(getResources().getColor(R.color.white), 0, 133, 119);

        view_status = findViewById(R.id.view_status);
        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) view_status.getLayoutParams();
        linearParams.height = barUtils.getStatusBarHeight(this.getBaseContext());
        view_status.setLayoutParams(linearParams);


    }



}
