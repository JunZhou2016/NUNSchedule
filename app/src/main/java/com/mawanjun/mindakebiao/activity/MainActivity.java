package com.mawanjun.mindakebiao.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mawanjun.mindakebiao.R;

import java.util.ArrayList;
import java.util.List;

import me.majiajie.pagerbottomtabstrip.Controller;
import me.majiajie.pagerbottomtabstrip.PagerBottomTabLayout;
import me.majiajie.pagerbottomtabstrip.TabItemBuilder;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectListener;

/**
 * 项目名称：MinDaKeBiao
 * 类描述：
 * 创建人：马万军
 * 创建时间：2016/12/27 22:17
 * 修改人：马万军
 * 修改时间：2016/12/27 22:17
 * 修改备注：
 */
public class MainActivity extends BaseActivity {


    //    int[] testColors = {0xFF7BA3A8,0xFFF4F3DE,0xFFBEAD92,0xFFF35A4A,0xFF5B4947};
//    int[] testColors = {0xFF00796B,0xFF8D6E63,0xFF2196F3,0xFF607D8B,0xFFF57C00};
    int[] testColors = {0xFF00796B, 0xFF5B4947, 0xFF607D8B, 0xFFF57C00, 0xFFF57C00};
    Controller controller;

    List<Fragment> mFragments;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomTab();
        initFragment();
    }

    private void initFragment() {
        mFragments = new ArrayList<>();
            AboutFragment aboutFragme = new AboutFragment();
            CourseFragment courseFragment = new CourseFragment();
            mFragments.add(courseFragment);
            mFragments.add(aboutFragme);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.push_up_in, R.anim.push_up_out);
            transaction.add(R.id.frameLayout, courseFragment);
            transaction.add(R.id.frameLayout, mFragments.get(1));
           //transaction.hide(courseFragment); hide的话会多显示两次Fragment，有种抖动的感觉
            transaction.hide(aboutFragme);
            transaction.commit();

    }

    private void BottomTab() {
        PagerBottomTabLayout pagerBottomTabLayout = (PagerBottomTabLayout) findViewById(R.id.tab);

        //用TabItemBuilder构建一个导航按钮
        TabItemBuilder tabItemBuilder = new TabItemBuilder(this).create()
                //.setDefaultIcon(android.R.drawable.ic_menu_send)
                .setDefaultIcon(R.drawable.coursefill)
                .setText("课表")
                .setSelectedColor(testColors[2])
                .setTag("A")
                .build();

        TabItemBuilder tabItemBuilder2 = new TabItemBuilder(this).create()
                //.setDefaultIcon(android.R.drawable.ic_menu_send)
                .setDefaultIcon(R.drawable.aboutfill)
                .setText("更多")
                .setSelectedColor(testColors[1])
                .build();
        //构建导航栏,得到Controller进行后续控制
        controller = pagerBottomTabLayout.builder()
                .addTabItem(tabItemBuilder)
                .addTabItem(tabItemBuilder2)
                // .addTabItem(android.R.drawable.ic_menu_compass, "更多",testColors[1])
                //.addTabItem(R.drawable.aboutfill, "更多", testColors[1])
                //.addTabItem(android.R.drawable.ic_menu_search, "搜索",testColors[2])
                //               .addTabItem(android.R.drawable.ic_menu_help, "帮助",testColors[2])
//                .setMode(TabLayoutMode.HIDE_TEXT)
//                .setMode(TabLayoutMode.CHANGE_BACKGROUND_COLOR)
//                .setMode(TabLayoutMode.HIDE_TEXT| TabLayoutMode.CHANGE_BACKGROUND_COLOR)
                .build();

//        controller.setMessageNumber("A",2);
//        controller.setDisplayOval(0,true);
        controller.setSelect(0);
        controller.addTabItemClickListener(listener);

    }


    OnTabItemSelectListener listener = new OnTabItemSelectListener() {
        @Override
        public void onSelected(int index, Object tag) {
            Log.i("asd", "onSelected:" + index + "   TAG: " + tag.toString());

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            //  transaction.setCustomAnimations(R.anim.push_up_in,R.anim.push_up_out);
            // transaction.replace(R.id.frameLayout, mFragments.get(index));
            transaction.hide(mFragments.get(mFragments.size() - index - 1));
            transaction.show(mFragments.get(index));
            transaction.commit();
        }

        @Override
        public void onRepeatClick(int index, Object tag) {
            Log.i("asd", "onRepeatClick:" + index + "   TAG: " + tag.toString());
        }
    };

    @Override
    protected void onSaveInstanceState(Bundle outState) {
      //  super.onSaveInstanceState(outState);
    }


    @Override
    public void onBackPressed() {
        //实现Home键效果
        //super.onBackPressed();这句话一定要注掉,不然又去调用默认的back处理方式了
        Intent i= new Intent(Intent.ACTION_MAIN);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addCategory(Intent.CATEGORY_HOME);
        startActivity(i);
    }
}





