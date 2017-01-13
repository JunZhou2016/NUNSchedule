package com.mawanjun.mindakebiao.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 项目名称：MinDaKeBiao
 * 类描述：SharedPreferencesUtil工具类
 * 创建人：马万军
 * 创建时间：2016/12/27 22:18
 * 修改人：马万军
 * 修改时间：2016/12/27 22:18
 * 修改备注：
 */
public class SharedPreferenceUtil {
    private static Context mContext;
    private  static  String mFileName;
    public SharedPreferenceUtil(Context context,String fileName){
        mContext = context;
        this.mFileName=fileName;
    }
    /**保存键值对
     * @param key
     * @param value
     */
    public  void setKeyData(String key,String value){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(mFileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * 根据键得到值，如果为空返回""
     * @param key
     * @return
     */
    public static String getKeyData(String key){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(mFileName, Context.MODE_PRIVATE);
        String value = sharedPreferences.getString(key, "");//第二个参数为默认值
        return value;
    }

    public void setBoolean(String key,boolean defValue){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(mFileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key,defValue);
        editor.commit();
    }

    public boolean getBoolean(String key){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(mFileName, Context.MODE_PRIVATE);
        boolean value = sharedPreferences.getBoolean(key,true);//第二个参数为默认值
        return value;
    }

    public void setInteger(String key,int defValue){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(mFileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key,defValue);
        editor.commit();
    }

    public int getInteger(String key){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(mFileName, Context.MODE_PRIVATE);
        int value = sharedPreferences.getInt(key,1);//第二个参数为默认值
        return value;
    }


}
