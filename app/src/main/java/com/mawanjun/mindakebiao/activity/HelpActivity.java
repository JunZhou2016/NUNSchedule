package com.mawanjun.mindakebiao.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
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
    private TextView mHelpWord;

    private void assignViews() {
        mDownload = (TextView) findViewById(R.id.download);
        mHelpWord = (TextView) findViewById(R.id.help_word);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help_activity);
        assignViews();

        String s1 = "<font color='blue'><b> 点击下载：</b></font><br>";
        s1 += "<a href = 'http://xww.nun.edu.cn/xcbnews/Article/UploadFiles/201503/ArubaVIA.apk'>ArubaVIA</a>";
        mDownload.setText(Html.fromHtml(s1));
        mDownload.setMovementMethod(LinkMovementMethod.getInstance());

        String s2 = "<font color='blue'><b>访问VPN使用说明：</b></font><br>";
        s2 += "<a href = 'http://its.nun.edu.cn/863.html'>VPN使用说明</a>";
        mHelpWord.setText(Html.fromHtml(s2));
       mHelpWord.setMovementMethod(LinkMovementMethod.getInstance());

    }

}
