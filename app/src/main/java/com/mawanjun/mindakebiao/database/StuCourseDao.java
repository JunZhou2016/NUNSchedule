package com.mawanjun.mindakebiao.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mawanjun.mindakebiao.model.Course;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：MinDaKeBiao
 * 类描述：
 * 创建人：马万军
 * 创建时间：2017/1/2 0:35
 * 修改人：马万军
 * 修改时间：2017/1/2 0:35
 * 修改备注：
 */
public class StuCourseDao {
    private SQLiteDatabase mDataBase;
    private static StuCourseDao sStuCourseDao;

    public StuCourseDao(Context context){
        mDataBase = new DBHelper(context).getWritableDatabase();
    }

    public static StuCourseDao getInstance (Context context){
        synchronized (StuCourseDao.class){
            if (sStuCourseDao == null){
                sStuCourseDao = new StuCourseDao(context);
            }
        }
        return sStuCourseDao;
    }

    public void saveStuCls(Course course){
        ContentValues values = new ContentValues();
        values.put(DBHelper.STUCLS_NAME_COL, course.getClsName());
        values.put(DBHelper.STUCLS_DAY_COL, course.getDay());
        values.put(DBHelper.STUCLS_NUM_COL, course.getClsNum());
        values.put(DBHelper.STUCLS_COUNT_COL, course.getClsCount());
        values.put(DBHelper.STUCLS_COLOR_COL, course.getColor());
        //当相同的时候替换。
        mDataBase.insertWithOnConflict(DBHelper.TABLE_STUCLS,null,values,SQLiteDatabase.CONFLICT_REPLACE);
    }

    public void saveStuClsList(List<Course> courses){
        for (Course course : courses){
            saveStuCls(course);
        }
    }

    public List<Course> getStuClsList(){
        List<Course> courses = new ArrayList<>();
        Cursor cursor = mDataBase.rawQuery("select * from "+DBHelper.TABLE_STUCLS,null);
        while (cursor.moveToNext()){
            Course course = new Course();
            course.setClsName(cursor.getString(
                    cursor.getColumnIndex(DBHelper.STUCLS_NAME_COL))
            );
            course.setClsNum(cursor.getInt(
                    cursor.getColumnIndex(DBHelper.STUCLS_NUM_COL)
            ));
            course.setDay(cursor.getInt(
                    cursor.getColumnIndex(DBHelper.STUCLS_DAY_COL)
            ));
            course.setClsCount(cursor.getInt(
                    cursor.getColumnIndex(DBHelper.STUCLS_COUNT_COL)
            ));
            course.setColor(cursor.getInt(
                    cursor.getColumnIndex(DBHelper.STUCLS_COLOR_COL)
            ));
            courses.add(course);
        }
        cursor.close();
        return courses;
    }

    public void removeAll(){
        //删除全部记录
        mDataBase.delete(DBHelper.TABLE_STUCLS,null,null);
    }
}
