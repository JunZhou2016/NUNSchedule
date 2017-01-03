package com.mawanjun.mindakebiao.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mawanjun.mindakebiao.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：MinDaKeBiao
 * 类描述：
 * 创建人：马万军
 * 创建时间：2016/12/27 22:17
 * 修改人：马万军
 * 修改时间：2016/12/27 22:17
 * 修改备注：
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout mCourse;
    private ImageButton mCourseimage;
    private LinearLayout mAbout;
    private ImageButton mAboutimage;
    private TextView aboutTextView;
    private TextView courseTextView;
    private ViewPager viewPager;
    private List<Fragment> mFragmentlist;
    private FragmentPagerAdapter fragmentPagerAdapter;
    Fragment mCourseFragment;
    Fragment mAboutFragment;

    public  void assignViews() {
        mCourse = (LinearLayout) findViewById(R.id.course);
        mCourseimage = (ImageButton) findViewById(R.id.courseimage);
        mAbout = (LinearLayout) findViewById(R.id.about);
        mAboutimage = (ImageButton) findViewById(R.id.aboutimage);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        aboutTextView = (TextView) findViewById(R.id.about_text);
        courseTextView = (TextView) findViewById(R.id.course_text);

        mFragmentlist = new ArrayList<Fragment>();
        mCourseFragment = new CourseFragment();
        mAboutFragment = new AboutFragment();
        mFragmentlist.add(mCourseFragment);
        mFragmentlist.add(mAboutFragment);
        fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragmentlist.get(position);
            }

            @Override
            public int getCount() {
                return mFragmentlist.size();
            }
        };

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setimage ();
                int selectitem = viewPager.getCurrentItem();
                switch (selectitem){
                    case  0:
                        viewPager.setCurrentItem(0);
                        mCourseimage.setImageResource(R.drawable.coursefill);
                        courseTextView.setTextColor(Color.parseColor("#56abe4"));

                        break;
                    case 1:
                        viewPager.setCurrentItem(1);
                        mAboutimage.setImageResource(R.drawable.aboutfill);
                        aboutTextView.setTextColor(Color.parseColor("#56abe4"));
                        courseTextView.setTextColor(Color.parseColor("#272636"));
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    public void initevent(){
        mCourse.setOnClickListener(this);
        mAbout.setOnClickListener(this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        assignViews();
        initevent();
        viewPager.setAdapter(fragmentPagerAdapter);

    }



    @Override
    public void onClick(View v) {
        setimage();
        switch (v.getId()){
            case R.id.course:
                viewPager.setCurrentItem(0);
                mCourseimage.setImageResource(R.drawable.coursefill);
                courseTextView.setTextColor(Color.parseColor("#56abe4"));
                break;
            case R.id.about:
                viewPager.setCurrentItem(1);
                mAboutimage.setImageResource(R.drawable.aboutfill);
                aboutTextView.setTextColor(Color.parseColor("#56abe4"));
                break;
        }

    }

    public void setimage (){
        mCourseimage.setImageResource(R.drawable.coursenull);

        aboutTextView.setTextColor(Color.parseColor("#272636"));
        mAboutimage.setImageResource(R.drawable.aboutnull);
    }

}




