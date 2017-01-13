package com.mawanjun.mindakebiao.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mawanjun.mindakebiao.R;
import com.mawanjun.mindakebiao.database.StuCourseDao;
import com.mawanjun.mindakebiao.model.Course;
import com.mawanjun.mindakebiao.net.CourseService;
import com.mawanjun.mindakebiao.net.HttpConnection;
import com.mawanjun.mindakebiao.net.LoginService;
import com.mawanjun.mindakebiao.utils.SharedPreferenceUtil;
import com.mawanjun.mindakebiao.utils.ToastUtil;
import com.wayww.edittextfirework.FireworkView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：MinDaKeBiao
 * 类描述：
 * 创建人：马万军
 * 创建时间：2017/1/1 22:48
 * 修改人：马万军
 * 修改时间：2017/1/1 22:48
 * 修改备注：
 */
public class LoginActivity extends  BaseActivity {

    private EditText mEtUserName;
    private EditText mEtPwd;
    private EditText mEtCodes;
    private ImageView mIvCodesIcon;
    private Button mBtnLogin;
    private Button mRefresh;
    private TextView mHelpText;
    private ImageView mLogo;
    private FireworkView mFireWorkView1, mFireWorkView2, mFireWorkView3;
    private List<Course> mStuCourseList = new ArrayList<>();
    private CourseService mCourseService;
    private StuCourseDao mStuCourseDao;


    private void assignViews() {
        mEtUserName = (EditText) findViewById(R.id.login_et_username);
        mEtPwd = (EditText) findViewById(R.id.login_et_pwd);
        mEtCodes = (EditText) findViewById(R.id.login_et_codes);
        mIvCodesIcon = (ImageView) findViewById(R.id.login_iv_codes_img);
        mBtnLogin = (Button) findViewById(R.id.login_btn_login);
        mRefresh = (Button) findViewById(R.id.refresh_codes);
        mHelpText = (TextView) findViewById(R.id.help_text);
        mLogo = (ImageView) findViewById(R.id.imageViewLogo);
        mFireWorkView1 = (FireworkView) findViewById(R.id.fire_work1);
        mFireWorkView2 = (FireworkView) findViewById(R.id.fire_work2);
        mFireWorkView3 = (FireworkView) findViewById(R.id.fire_work3);
        mFireWorkView1.bindEditText(mEtUserName);
        mFireWorkView2.bindEditText(mEtPwd);
        mFireWorkView3.bindEditText(mEtCodes);
        mCourseService = new CourseService();
        mStuCourseDao = StuCourseDao.getInstance(this);
    }


    private LoginService mLoginService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        assignViews();
        initWidget(savedInstanceState);
        initClick();

        mHelpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHelpActivity();
            }
        });
        mLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHelpActivity();
            }
        });
    }


    protected void initWidget(Bundle savedInstanceState) {
        mLoginService = new LoginService(this);
        //获取存储的账号和密码

        String userName = SharedPreferenceUtil.getKeyData("userNameKey");
        String pwd = SharedPreferenceUtil.getKeyData("pwdKey");
        if (!userName.equals("") && !pwd.equals("")) {
            mEtUserName.setText(userName);
            mEtPwd.setText(pwd);
        }
        //初始化验证码
        setUpCodesImage();
    }

    private void setUpCodesImage() {
        try {
            mLoginService.getCodesImg(new HttpConnection.HttpCallBack<Bitmap>() {
                @Override
                public void callback(Bitmap data) {
                    mIvCodesIcon.setImageBitmap(data);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void initClick() {
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置正在登录
                final ProgressDialog dialog = new ProgressDialog(LoginActivity.this);
                dialog.show();
                //获取登录数据，并验证
                final String userName = mEtUserName.getText().toString().trim();
                final String pwd = mEtPwd.getText().toString().trim();
                String codes = mEtCodes.getText().toString().trim();

                try {
                    mLoginService.login(userName, pwd, codes, new HttpConnection.HttpCallBack<Boolean>() {
                        @Override
                        public void callback(Boolean data) {
                           dialog.dismiss();
                            //登陆成功
                            if (data) {
                                //存储数据
                                SharedPreferenceUtil sharedPreferenceUtil = new SharedPreferenceUtil(getApplicationContext(), "accountInfo");
                                sharedPreferenceUtil.setKeyData("userNameKey", userName);
                                sharedPreferenceUtil.setKeyData("pwdKey", pwd);
                                sharedPreferenceUtil.setKeyData("isLogin", "TRUE");
                                //如果登录成功，从网页上获取课程表
                                loadCourse();
                            } else {
                                //提示账号或者密码错误
                                ToastUtil.showToast(getApplicationContext(), "账号或密码错误");
                                //重新获取验证码
                                setUpCodesImage();
                            }
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        //重新设置验证码
        mIvCodesIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpCodesImage();
            }
        });
        //刷新验证码
        mRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpCodesImage();
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.orientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            //TODO:
        }
        super.onConfigurationChanged(newConfig);
    }

    private void showHelpActivity() {
        Intent intent = new Intent(LoginActivity.this,HelpActivity.class);
        startActivity(intent);

    }
    public  void loadCourse() {
        try {
            //等待提示
            final ProgressDialog dialog = new ProgressDialog(LoginActivity.this);
            dialog.setTitle("加载课程中");
            dialog.show();
            //加载数据

            SharedPreferenceUtil sharedPreferenceUtil = new SharedPreferenceUtil(getApplicationContext(), "accountInfo");
            mCourseService.getCourse(sharedPreferenceUtil.getKeyData("userNameKey"),
                    new HttpConnection.HttpCallBack<List<Course>>() {
                        @Override
                        public void callback(List<Course> data) {
                            //清空原有数据
                            mStuCourseList.clear();
                            mStuCourseDao.removeAll();
                            //加载数据
                            mStuCourseList.addAll(data);
                            //将数据存储到数据库中
                            mStuCourseDao.saveStuClsList(mStuCourseList);
                            //跳转到MainActivity中
                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                            dialog.dismiss();
                        }
                    });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}