package com.mawanjun.mindakebiao.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * 项目名称：MinDaKeBiao
 * 类描述：
 * 创建人：马万军
 * 创建时间：2017/1/1 23:27
 * 修改人：马万军
 * 修改时间：2017/1/1 23:27
 * 修改备注：
 */
public class ToastUtil {
    private  static Toast toast;
    public static  void showToast (Context context, String content){
        if (toast == null) {
            toast =   Toast.makeText(context,content,Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);//settext是为了你还在连续触发的时候Toast的内容，只要动作
            //没停，他就一直显示这个toast，但最大的好处在于不会连续创建多个toast对象，造成过多的弹出影响体验
        }
        toast.show();
    }
}
