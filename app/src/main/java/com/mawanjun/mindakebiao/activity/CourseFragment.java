package com.mawanjun.mindakebiao.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mawanjun.mindakebiao.R;
import com.mawanjun.mindakebiao.activity.LoginActivity;
import com.mawanjun.mindakebiao.database.StuCourseDao;
import com.mawanjun.mindakebiao.model.Course;
import com.mawanjun.mindakebiao.net.CourseService;
import com.mawanjun.mindakebiao.net.HttpConnection;
import com.mawanjun.mindakebiao.utils.SharedPreferenceUtil;
import com.mawanjun.mindakebiao.utils.ToastUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * 项目名称：MinDaKeBiao
 * 类描述：
 * 创建人：马万军
 * 创建时间：2017/1/1 12:33
 * 修改人：马万军
 * 修改时间：2017/1/1 12:33
 * 修改备注：
 */
public class CourseFragment extends Fragment {
    private List<Course> mStuCourseList = new ArrayList<>();
    private CourseService mCourseService;
    private StuCourseDao mStuCourseDao;

         @Nullable
         @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.course_fragment,container,false);
             mCourseService = new CourseService();
             mStuCourseDao = StuCourseDao.getInstance(getContext());
             dispalyCourse();
        return  view;
    }


    public void dispalyCourse() {
        try {
            ToastUtil.showToast(getContext(),"测试");
            //等待提示
            //final ProgressDialog dialog = new ProgressDialog(getContext());
           // dialog.setTitle("加载课程中");
           // dialog.show();
            //加载数据
            mCourseService.getCourse(SharedPreferenceUtil.getKeyData("userNameKey"),
                    new HttpConnection.HttpCallBack<List<Course>>() {
                        @Override
                        public void callback(List<Course> data) {
                            ToastUtil.showToast(getContext(),data.toString());
                            //清空原有数据
                            //mStuCourseList.clear();
                          //  mStuCourseDao.removeAll();
                            //加载数据
                           // mStuCourseList.addAll(data);
                            //ToastUtil.showToast(getContext(),data.toString());
                             Toast.makeText(getContext(),data.toString(),Toast.LENGTH_LONG).show();
                            Log.d("ceshi",data.toString());
                            //  showCls();
                         //   dialog.dismiss();
                        }
                    });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
