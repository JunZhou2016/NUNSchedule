package com.mawanjun.mindakebiao.activity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mawanjun.mindakebiao.R;
import com.mawanjun.mindakebiao.database.StuCourseDao;
import com.mawanjun.mindakebiao.model.Course;
import com.mawanjun.mindakebiao.net.CourseService;
import com.mawanjun.mindakebiao.net.HttpConnection;
import com.mawanjun.mindakebiao.utils.SharedPreferenceUtil;
import com.mawanjun.mindakebiao.utils.ToastUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
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
public class CourseFragment extends BaseFragment {

    private static final int REQUEST_LOGIN = 0;
 //   private static final String [] TITLE_DATA = {"9月","周一","周二","周三","周四","周五","周六","周日"};
    private static final int GRID_ROW_COUNT = 12;
    private static final int GRID_COL_COUNT = 8;
    private List<Course> mStuCourseList = new ArrayList<>();
    private CourseService mCourseService;
    private StuCourseDao mStuCourseDao;
    private List<Course> courses;
    private GridLayout mGlClsTitle;
    private GridLayout mGlClsContent;
    private  ProgressDialog dialog;
    LinearLayout weekNames;
  //  private TextView text;
    private int mTableDistance;
//    private static CourseFragment mInstance;// 单例模式
//
//    private CourseFragment() {
//    }
//
//    public static CourseFragment getInstance() {
//        if (mInstance == null) {
//            synchronized (CourseFragment.class) {
//                if (mInstance == null) {
//                    mInstance = new CourseFragment();
//                }
//            }
//        }
//        return mInstance;
//    }

   // private Button button;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.course_fragment,container,false);
      //  text = (TextView) view.findViewById(R.id.text);
     //  mGlClsTitle = (GridLayout) view.findViewById(R.id.main_grid_title);
        mGlClsContent = (GridLayout) view.findViewById(R.id.main_grid_content);
        weekNames = (LinearLayout) view.findViewById(R.id.weekNames);
       // button = (Button) view.findViewById(R.id.load_course);
        initWidget();
        processLogic();
       // button.setOnClickListener(this);
       // processLogic(savedInstanceState);
        return  view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
//
//    @Override
//    protected void initView() {
//
//    }


    protected void initWidget() {
        mTableDistance = getScreenPixelWidth()/15;
        mCourseService = new CourseService();
        mStuCourseDao = StuCourseDao.getInstance(getContext());
    //   setUpClsTitle();
        initWeekNameView();
        setUpClsContent();


    }


    protected void initClick() {

    }


    protected void processLogic() {
        final ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.show();
        //首先从数据库获取值
        courses = mStuCourseDao.getStuClsList();
        mStuCourseList.addAll(courses);
       // ToastUtil.showToast(getContext(),courses.toString());
        showCls();
        dialog.dismiss();
    }
    /**
     * 顶部周一到周日的布局
     **/
    private void initWeekNameView() {
        for (int i = 0; i < 7 + 1; i++) {
            TextView tvWeekName = new TextView(getContext());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.gravity = Gravity.CENTER | Gravity.CENTER_HORIZONTAL;
            if (i != 0) {
                lp.weight = 1;
                tvWeekName.setText("周" + intToZH(i));
                if (i == getWeekDay()) {
                    tvWeekName.setTextColor(Color.parseColor("#FF0000"));
                } else {
                    tvWeekName.setTextColor(Color.parseColor("#4A4A4A"));
                }
            } else {
                lp.weight = 0.8f;
                tvWeekName.setText(getMonth() + "月");
            }
            tvWeekName.setGravity(Gravity.CENTER_HORIZONTAL);
            tvWeekName.setLayoutParams(lp);
            weekNames.addView(tvWeekName);
        }
    }

    /**
     * 当前星期
     */
    public int getWeekDay() {
        int w = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1;
        if (w <= 0) {
            w = 7;
        }
        return w;
    }
    /**
     * 当前月份
     */
    public int getMonth() {
        int w = Calendar.getInstance().get(Calendar.MONTH) + 1;
        return w;
    }

    /**
     * 数字转换中文
     */
    public static String intToZH(int i) {
        String[] zh = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
        String[] unit = {"", "十", "百", "千", "万", "十", "百", "千", "亿", "十"};

        String str = "";
        StringBuffer sb = new StringBuffer(String.valueOf(i));
        sb = sb.reverse();
        int r = 0;
        int l = 0;
        for (int j = 0; j < sb.length(); j++) {
            r = Integer.valueOf(sb.substring(j, j + 1));
            if (j != 0)
                l = Integer.valueOf(sb.substring(j - 1, j));
            if (j == 0) {
                if (r != 0 || sb.length() == 1)
                    str = zh[r];
                continue;
            }
            if (j == 1 || j == 2 || j == 3 || j == 5 || j == 6 || j == 7 || j == 9) {
                if (r != 0)
                    str = zh[r] + unit[j] + str;
                else if (l != 0)
                    str = zh[r] + str;
                continue;
            }
            if (j == 4 || j == 8) {
                str = unit[j] + str;
                if ((l != 0 && r == 0) || r != 0)
                    str = zh[r] + str;
                continue;
            }
        }
        if (str.equals("七"))
            str = "日";
        return str;
    }

//    //设置表格显示星期的地方
//    private void setUpClsTitle(){
//        for (int i=0; i<TITLE_DATA.length; ++i){
//            String content = TITLE_DATA[i];
//            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
//            //第一列的时候
//            if (i == 0){
//                params.width = mTableDistance;
//            }
//            else {
//                //添加分割线
//                LayoutInflater inflater=LayoutInflater.from(getContext());
//                View divider = inflater.inflate(R.layout.grid_title_form,mGlClsTitle,false);
//                mGlClsTitle.addView(divider);
//
//                params.width = mTableDistance * 2;
//            }
//            params.height = GridLayout.LayoutParams.MATCH_PARENT;
//            TextView textView = new TextView(getContext());
//            textView.setTextColor(getResources().getColor(R.color.blue));
//            textView.setText(content);
//            textView.setGravity(Gravity.CENTER);
//            mGlClsTitle.addView(textView,params);
//        }
//    }



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
            TextView textView = new TextView(getContext());
            textView.setTextColor(getResources().getColor(R.color.blue));
            textView.setText(i+"");
            textView.setGravity(Gravity.CENTER);
            textView.setBackground(getResources().getDrawable(R.drawable.back));
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

            View view = new View(getContext());
            mGlClsContent.addView(view,params);
        }
    }

    private void showCls() {
        //text.setText(courses.toString());
        for (int i = 0; i < mStuCourseList.size(); ++i) {
            Course course = mStuCourseList.get(i);
            int row = course.getClsNum();
            int col = course.getDay();
            int size = course.getClsCount();
            //设定View在表格的哪行那列
            GridLayout.LayoutParams params = new GridLayout.LayoutParams(
                    GridLayout.spec(row, size),
                    GridLayout.spec(col)
            );
            //设置View的宽高
            params.width = mTableDistance * 2;
            params.height = (int) getResources().getDimension(R.dimen.table_row_height) * size;
            params.setGravity(Gravity.FILL);
            //通过代码改变<Shape>的背景颜色
            GradientDrawable drawable = (GradientDrawable) getResources().getDrawable(R.drawable.cls_bg);
            drawable.setColor(getResources().getColor(course.getColor()));
            //设置View
            TextView textView = new TextView(getContext());
            textView.setTextColor(getResources().getColor(R.color.white));
            textView.setText(course.getClsName());
            textView.setGravity(Gravity.CENTER);
            textView.setBackground(drawable);
            //添加到表格中
            mGlClsContent.addView(textView, params);
        }

    }
}
