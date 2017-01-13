package com.mawanjun.mindakebiao.activity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.mawanjun.mindakebiao.R;
import com.mawanjun.mindakebiao.database.StuCourseDao;
import com.mawanjun.mindakebiao.model.Course;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 项目名称：MinDaKeBiao
 * 类描述：
 * 创建人：马万军
 * 创建时间：2017/1/1 12:33
 * 修改人：马万军
 * 修改时间：2017/1/1 12:33
 * 修改备注：
 */
public class CourseFragment extends BaseFragment implements AdapterView.OnItemSelectedListener {

    private LinearLayout weekNames;
    private LinearLayout sections;


    private GridLayout mGlClsContent;
    private int maxSection = 12;
    private int itemHeight;
    private ArrayList<Course> mStuCourseList;
    private StuCourseDao mStuCourseDao;
    private List<Course> courses;
    private int mTableDistance;
    private static final int GRID_COL_COUNT = 7;

    private Spinner spinner;
    private ArrayAdapter<String> adapter;
    private String[] weekList;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.course_fragment, container, false);
        weekNames = (LinearLayout) view.findViewById(R.id.weekNames);
        sections = (LinearLayout) view.findViewById(R.id.sections);
        spinner = (Spinner) view.findViewById(R.id.spinner);
        mGlClsContent = (GridLayout) view.findViewById(R.id.main_grid_content);
        itemHeight = getResources().getDimensionPixelSize(R.dimen.sectionHeight);
        mTableDistance = getScreenPixelWidth() * 70 / 80 / 7;
        weekList = new String[]{"第1周", "第2周", "第3周", "第4周", "第5周", "第6周", "第7周", "第8周", "第9周", "第10周", "第11周"
                , "第12周", "第13周", "第14周", "第15周", "第16周", "第17周", "第18周", "第19周", "第20周"};
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, weekList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        initWeekCourseView();
        return view;
    }


    /**
     * 初始化课程表
     */

    private void initWeekCourseView() {
        mStuCourseList = new ArrayList<>();
        mStuCourseDao = StuCourseDao.getInstance(getContext());
        courses = mStuCourseDao.getStuClsList();
        mStuCourseList.addAll(courses);
        initWeekNameView();
        setUpClsContent();
        initSectionView();

    }


    //初始化课表显示的格子
    private void setUpClsContent() {

        //初始化表格的距离
        for (int i = 0; i < GRID_COL_COUNT; i++) {
            int row = 0;
            int col = i;
            GridLayout.LayoutParams params = new GridLayout.LayoutParams(
                    GridLayout.spec(row), GridLayout.spec(col)
            );
            params.width = mTableDistance;
            params.height = 0;

            View view = new View(getContext());
            mGlClsContent.addView(view, params);
        }
    }

    private void showCls(int week) {
        //清空原有视图，再添加新的视图
        mGlClsContent.removeAllViews();
        setUpClsContent();
        for (int i = 0; i < mStuCourseList.size(); i++) {
            Course course = mStuCourseList.get(i);

            String temp = course.getClsName();
            int x = temp.indexOf("*");
            int y = temp.indexOf("#");
            int z = temp.indexOf("|");
            String start = temp.substring(x + 1, y);
            String end = temp.substring(y + 1, z);
            String bWeek = temp.substring(z + 1, z + 2);

            if (isThisWeek(week, start, end, bWeek)) {
                int row = course.getClsNum();
                int col = course.getDay() - 1;
                int size = course.getClsCount();
                //设定View在表格的哪行那列
                GridLayout.LayoutParams params = new GridLayout.LayoutParams(
                        GridLayout.spec(row, size),
                        GridLayout.spec(col)
                );
                //设置View的宽高
                params.width = mTableDistance;
                params.height = (int) getResources().getDimension(R.dimen.table_row_height) * size;
                params.setGravity(Gravity.FILL);
                //通过代码改变<Shape>的背景颜色
                GradientDrawable drawable = (GradientDrawable) getResources().getDrawable(R.drawable.cls_bg);
                drawable.setColor(getResources().getColor(course.getColor()));
                //设置View
                TextView textView = new TextView(getContext());
                textView.setTextColor(getResources().getColor(R.color.white));
                int k = course.getClsName().indexOf("*");
                textView.setText(course.getClsName().substring(0, k));
                textView.setGravity(Gravity.CENTER);
                textView.setBackground(drawable);
                //添加到表格中
                mGlClsContent.addView(textView, params);
            }
        }

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
     * 左边节次布局，设定每天最多12节课
     */
    private void initSectionView() {
        for (int i = 1; i <= maxSection; i++) {
            TextView tvSection = new TextView(getContext());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, getResources().getDimensionPixelSize(R.dimen.sectionHeight));
            lp.gravity = Gravity.CENTER;
            tvSection.setGravity(Gravity.CENTER);
            tvSection.setText(String.valueOf(i));
            tvSection.setLayoutParams(lp);
            sections.addView(tvSection);
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
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
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

    /**
     * 判断本节课是否在指定周上
     *
     * @param week 指定周
     * @return ture 是
     */
    public Boolean isThisWeek(int week, String start, String end, String bWeek) {
        Boolean result = false;
        // 在最大最小周之间
        int max = Integer.parseInt(end);
        int min = Integer.parseInt(start);
        if (week <= max && week >= min) {
            if ("全".equals(bWeek))
                result = true;
            if ("单".equals(bWeek) && week % 2 == 1) {
                result = true;
            }
            if ("双".equals(bWeek) && week % 2 == 0) {
                result = true;
            }
        }
        return result;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String week = adapter.getItem(position).trim();
        int x = week.indexOf("周");
        int y = Integer.parseInt(week.substring(1, x));
        showCls(y);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
