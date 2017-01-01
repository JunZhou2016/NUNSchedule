package com.mawanjun.mindakebiao.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.mawanjun.mindakebiao.R;
import com.mawanjun.mindakebiao.utils.SharedPreferenceUtil;
import com.mawanjun.mindakebiao.view.MainActivity;

import gr.net.maroulis.library.EasySplashScreen;

/**
 * 项目名称：MinDaKeBiao
 * 类描述：闪屏界面
 * 创建人：马万军
 * 创建时间：2016/12/31 21:29
 * 修改人：马万军
 * 修改时间：2016/12/31 21:29
 * 修改备注：
 */
public class SplashActivity extends Activity {
ImageView imageView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //借助第三方库快速构建闪屏界面
        View easySplashScreenView = new EasySplashScreen(this)
                .withFullScreen()
                .withSplashTimeOut(1000)
                .withTargetActivity(chooseActivity())
                //.withBackgroundResource(android.R.color.holo_red_light)
                .withBackgroundColor(Color.WHITE)
                .withFooterText("Copyright 2016 devma")
                .withBeforeLogoText("团结进取")
                .withLogo(R.mipmap.ic_launcher)
                .withAfterLogoText("砥砺成才")
                .create();


        setContentView(easySplashScreenView);

    }

    public Class<? extends Object>  chooseActivity(){
        SharedPreferenceUtil sharedPreferenceUtil = new SharedPreferenceUtil(
                        getApplicationContext(),"accountInfo");
                String isLogin = sharedPreferenceUtil.getKeyData("isLogin");
                if (isLogin.equals("TRUE")) {
                    return  MainActivity.class;
                } else {
                   return LoginActivity.class;
                }

    }
}
