package com.mawanjun.mindakebiao.view;

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
    private static final int REQUEST_LOGIN = 0;
    private static final String [] TITLE_DATA = {"9月","周一","周二","周三","周四","周五","周六","周日"};
    private static final int GRID_ROW_COUNT = 12;
    private static final int GRID_COL_COUNT = 8;
    private List<Course> mStuCourseList = new ArrayList<>();
    private CourseService mCourseService;
    private StuCourseDao mStuCourseDao;
    private GridLayout mGlClsTitle;
    private GridLayout mGlClsContent;
    private int mTableDistance;
         @Nullable
         @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.course_fragment,container,false);
        mGlClsTitle = (GridLayout) view.findViewById(R.id.main_grid_title);
        mGlClsContent = (GridLayout) view.findViewById(R.id.main_grid_content);
        initWidget();
        processLogic(savedInstanceState);
        return  view;
    }

    public int getScreenPixelWidth(){
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        return metrics.widthPixels;
    }
    protected void initWidget() {
        //  ToastUtils.init(this);

        mTableDistance = getScreenPixelWidth()/15;
        mCourseService = new CourseService();
        mStuCourseDao = StuCourseDao.getInstance(getActivity().getApplicationContext());
        //mStuCourseDao = new StuCourseDao(getContext());
        setUpClsTitle();
        setUpClsContent();
        setHasOptionsMenu(true);
    }
    //设置表格显示星期的地方
    private void setUpClsTitle(){
        for (int i=0; i<TITLE_DATA.length; ++i){
            String content = TITLE_DATA[i];
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            //第一列的时候
            if (i == 0){
                params.width = mTableDistance;
            }
            else {
                //添加分割线

                LayoutInflater inflater = LayoutInflater.from(getActivity().getApplicationContext());
                View divider = inflater.inflate(R.layout.grid_title_form,mGlClsTitle,false);
               mGlClsTitle.addView(divider);

                params.width = mTableDistance * 2;
            }
            params.height = GridLayout.LayoutParams.MATCH_PARENT;
            TextView textView = new TextView(getActivity().getApplicationContext());
            textView.setTextColor(getResources().getColor(R.color.colorPrimary));
            textView.setText(content);
            textView.setGravity(Gravity.CENTER);
           mGlClsTitle.addView(textView,params);
        }
    }
    //初始化课表显示的格子
    private void setUpClsContent(){
        //设置每行第几节课的提示
        for(int i=0; i<GRID_ROW_COUNT+1; ++i){
            int row = i;
            int col = 0;
            GridLayout.LayoutParams params = new GridLayout.LayoutParams(
                    GridLayout.spec(row),GridLayout.spec(col)
            );
            params.width = mTableDistance;
            if (i == 0){
                params.height = 0;
            }
            else {
                params.height = (int) getResources().getDimension(R.dimen.table_row_height);
            }
            TextView textView = new TextView(getActivity().getApplicationContext());
            textView.setTextColor(getResources().getColor(R.color.colorPrimary));
            textView.setText(i+"");
            textView.setGravity(Gravity.CENTER);
            textView.setBackground(getResources().getDrawable(R.drawable.table_frame));
            mGlClsContent.addView(textView,params);
        }
        //初始化表格的距离
        for (int i=1; i<GRID_COL_COUNT; ++i){
            int row = 0;
            int col = i;
            GridLayout.LayoutParams params = new GridLayout.LayoutParams(
                    GridLayout.spec(row),GridLayout.spec(col)
            );
            params.width = mTableDistance*2;
            params.height = 0;

            View view = new View(getActivity().getApplicationContext());
           mGlClsContent.addView(view,params);
        }
    }

    private void showCls(){
        for (int i = 0; i< mStuCourseList.size(); ++i){
            Course course = mStuCourseList.get(i);
            int row = course.getClsNum();
            int col = course.getDay();
            int size = course.getClsCount();
            //设定View在表格的哪行那列
            GridLayout.LayoutParams params = new GridLayout.LayoutParams(
                    GridLayout.spec(row,size),
                    GridLayout.spec(col)
            );
            //设置View的宽高
            params.width = mTableDistance*2;
            params.height = (int) getResources().getDimension(R.dimen.table_row_height) * size;
            params.setGravity(Gravity.FILL);
            //通过代码改变<Shape>的背景颜色
            GradientDrawable drawable = (GradientDrawable) getResources().getDrawable(R.drawable.cls_bg);
            drawable.setColor(getResources().getColor(course.getColor()));
            //设置View
            TextView textView = new TextView(getActivity().getApplicationContext());
            textView.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            textView.setText(course.getClsName());
            textView.setGravity(Gravity.CENTER);
            textView.setBackground(drawable);
            //添加到表格中
            mGlClsContent.addView(textView,params);
        }
    }



    protected void processLogic(Bundle savedInstanceState) {
        //首先从数据库获取值
        List<Course> courses = mStuCourseDao.getStuClsList();
        mStuCourseList.addAll(courses);
        showCls();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.activity_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_get_course:
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivityForResult(intent,REQUEST_LOGIN);
                break;
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == -1){
            switch (requestCode){
                case REQUEST_LOGIN:
                    try {
                        //等待提示
                        final ProgressDialog dialog = new ProgressDialog(getContext());
                        dialog.setTitle("加载课程中");
                        dialog.show();
                        //加载数据
                        mCourseService.getCourse(SharedPreferenceUtil.getKeyData("userNameKey"),
                                new HttpConnection.HttpCallBack<List<Course>>() {
                                    @Override
                                    public void callback(List<Course> data) {
                                        //清空原有数据
                                        mStuCourseList.clear();
                                        mStuCourseDao.removeAll();
                                        //加载数据
                                        mStuCourseList.addAll(data);
                                        showCls();
                                        dialog.dismiss();
                                    }
                                });

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        mStuCourseDao.saveStuClsList(mStuCourseList);
    }

}
