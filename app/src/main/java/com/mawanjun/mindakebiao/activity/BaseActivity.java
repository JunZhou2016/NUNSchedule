package com.mawanjun.mindakebiao.activity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mawanjun.mindakebiao.R;

/**
 * 项目名称：MinDaKeBiao
 * 类描述：BaseActivity基类Activity，所有Activity的共性类
 * 创建人：马万军
 * 创建时间：2016/12/27 22:18
 * 修改人：马万军
 * 修改时间：2016/12/27 22:18
 * 修改备注：
 */
public class BaseActivity extends FragmentActivity {

    private int backKeyPressedTime = 0;
    private ImageView mImageView;
    private TextView mTitleText;




    protected void setScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
    }


    public boolean isNetWorkConn() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        return info != null && info.isConnected();
    }

    public void setTitleText(String titleText){
        mTitleText = (TextView) findViewById(R.id.title_text);
        mTitleText.setText(titleText);
    }

    public  void imageBack() {
        mImageView = (ImageView) findViewById(R.id.back);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    //    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK && backKeyPressedTime == 0) {
//            Toast.makeText(this, "再按一次返回键退出程序", Toast.LENGTH_SHORT).show();
//            backKeyPressedTime = 1;
//            new Thread() {
//                @Override
//                public void run() {
//                    try {
//                        Thread.sleep(2000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    } finally {
//                        backKeyPressedTime = 0;
//                    }
//                }
//            }.start();
//            return false;
//        } else if (keyCode == KeyEvent.KEYCODE_BACK && backKeyPressedTime == 1) {
//            this.finish();
//            System.exit(0);
//        }
//        return true;
//    }

}
