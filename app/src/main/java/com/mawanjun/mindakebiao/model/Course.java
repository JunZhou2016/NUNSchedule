package com.mawanjun.mindakebiao.model;

/**
 * 项目名称：MinDaKeBiao
 * 类描述：
 * 创建人：马万军
 * 创建时间：2017/1/1 23:39
 * 修改人：马万军
 * 修改时间：2017/1/1 23:39
 * 修改备注：
 */
public class Course {
    //星期几:周一到周日
    private int day;
    //第几节课：总共12节
    private int clsNum;
    //每节课的长度
    private int clsCount;
    //随机的颜色
    private int color;
    //课程名
    private String clsName;

    public int getClsNum() {
        return clsNum;
    }

    public void setClsNum(int clsNum) {
        this.clsNum = clsNum;
    }

    public String getClsName() {
        return clsName;
    }

    public void setClsName(String clsName) {
        this.clsName = clsName;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
    public int getClsCount() {
        return clsCount;
    }

    public void setClsCount(int clsCount) {
        this.clsCount = clsCount;
    }

    @Override
    public String toString() {
        return "StuClass{" +
                "clsCount=" + clsCount +
                ", day=" + day +
                ", clsNum=" + clsNum +
                ", color=" + color +
                ", clsName='" + clsName + '\'' +
                '}';
    }
}
