package com.mawanjun.mindakebiao.activity;

import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;

public abstract class BaseFragment extends Fragment {



    public int getScreenPixelWidth(){
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        return metrics.widthPixels;
    }

}
