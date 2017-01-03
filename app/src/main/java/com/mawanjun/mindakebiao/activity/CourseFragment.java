package com.mawanjun.mindakebiao.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
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

         @Nullable
         @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.course_fragment,container,false);
        return  view;
    }



}
