package com.mawanjun.mindakebiao.activity;

import android.os.Bundle;
import android.view.View;

import com.mawanjun.mindakebiao.R;

/**
 * 项目名称：MinDaKeBiao
 * 类描述：
 * 创建人：马万军
 * 创建时间：2016/12/27 22:18
 * 修改人：马万军
 * 修改时间：2016/12/27 22:18
 * 修改备注：
 */
public class LoginActivity extends  BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);//隐藏状态栏
        getSupportActionBar().hide();

    }
}
