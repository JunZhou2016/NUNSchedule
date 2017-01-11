package com.mawanjun.mindakebiao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import com.mawanjun.mindakebiao.R;

/**
 * 项目名称：MinDaKeBiao
 * 类描述：
 * 创建人：马万军
 * 创建时间：2017/1/6 2:26
 * 修改人：马万军
 * 修改时间：2017/1/6 2:26
 * 修改备注：
 */
public class HelpActivity extends  BaseActivity {
    private TextView mDownload;
    private  TextView mUse;



    private void assignViews() {
        mDownload = (TextView) findViewById(R.id.download);
        mUse = (TextView) findViewById(R.id.use);


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help_activity);
        assignViews();


        mUse.setText("1请在北方民族大学内网环境下登录。\n\n" +
                     "2不在学校，如何进入内网？\n\n" +
                      "下载附件中的“ArubaVIA.apk”，安装并运行即可为您创建内网环境。" +
                    "ArubaVIA登录界面按如下规则填写：\n\n" +
                         "Server URL: 111.113.23.11\n" +
                         "Username:   完整学号\n" +
                         "Password:   智慧网密码（初始密码为身份证后六位）\n\n" +
                         "PS:部分手机需要打开手机热点\n\n" +
                         "联系邮箱： devma@foxmail.com"
                       );

        String s1 = "<font color='blue'><b> 附件：</b></font><br>";
        s1 += "<a href = 'http://xww.nun.edu.cn/xcbnews/Article/UploadFiles/201503/ArubaVIA.apk'>ArubaVIA.apk</b></a><br>" +
                "<a href = 'http://its.nun.edu.cn/resource/20160808/20160808114733138.docx'>更多ArubaVIA使用说明</a>";
        mDownload.setText(Html.fromHtml(s1));
        mDownload.setMovementMethod(LinkMovementMethod.getInstance());

        setTitleText("登录说明");
        imageBack();


    }



    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
