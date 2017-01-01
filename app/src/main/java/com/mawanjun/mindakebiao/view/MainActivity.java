package com.mawanjun.mindakebiao.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;

import com.mawanjun.mindakebiao.R;
import com.mawanjun.mindakebiao.activity.BaseActivity;
import com.ycl.tabview.library.TabView;
import com.ycl.tabview.library.TabViewChild;

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
public class MainActivity extends BaseActivity {
    TabView tabView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tabView = (TabView) findViewById(R.id.tabView);

        CourseFragment courseFragment = new CourseFragment();
        AboutFragment aboutFragment = new AboutFragment();

        //start add data
        List<TabViewChild> tabViewChildList = new ArrayList<>();
        TabViewChild tabViewChild01 = new TabViewChild(R.drawable.world, R.drawable.world, "课表", courseFragment);
        TabViewChild tabViewChild02 = new TabViewChild(R.drawable.world, R.drawable.world, "更多", aboutFragment);

        tabViewChildList.add(tabViewChild01);
        tabViewChildList.add(tabViewChild02);

        //end add data
        //start custom style
        tabView.setTextViewSelectedColor(Color.BLUE);
        tabView.setTextViewUnSelectedColor(Color.BLACK);
        tabView.setTabViewBackgroundColor(Color.WHITE);
        tabView.setTabViewHeight(dip2px(52));
        tabView.setImageViewTextViewMargin(2);
        tabView.setTextViewSize(14);
        tabView.setImageViewWidth(dip2px(30));
        tabView.setImageViewHeight(dip2px(30));
        tabView.setTabViewGravity(Gravity.TOP);
        tabView.setTabViewDefaultPosition(0);
        //end of custom
        tabView.setTabViewChild(tabViewChildList, getSupportFragmentManager());

    }

    public int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}




