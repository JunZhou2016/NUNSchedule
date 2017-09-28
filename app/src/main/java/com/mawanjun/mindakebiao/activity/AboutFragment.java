package com.mawanjun.mindakebiao.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mawanjun.mindakebiao.R;
import com.mawanjun.mindakebiao.utils.SharedPreferenceUtil;
import com.mawanjun.mindakebiao.utils.ToastUtil;

import java.io.FileInputStream;

import okhttp3.OkHttpClient;

/**
 * 项目名称：MinDaKeBiao
 * 类描述：
 * 创建人：马万军
 * 创建时间：2017/1/1 12:40
 * 修改人：马万军
 * 修改时间：2017/1/1 12:40
 * 修改备注：
 */
public class AboutFragment  extends Fragment{

    private ImageView finish;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.about_fragment,container,false);
        finish = (ImageView) view.findViewById(R.id.finish);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               final   AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setTitle("确认退出当前账号？");
                alertDialog.setNegativeButton("取消",null);
                alertDialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferenceUtil sharedPreferenceUtil = new SharedPreferenceUtil(getContext(), "accountInfo");
                        sharedPreferenceUtil.setKeyData("userNameKey","");
                        Intent intent = new Intent(getActivity(),LoginActivity.class);
                        startActivity(intent);
                        sharedPreferenceUtil.setKeyData("isLogin","");
                        getActivity().finish();
                    }
                });
                        alertDialog.show();
            }
        });
        return  view;
    }

}
