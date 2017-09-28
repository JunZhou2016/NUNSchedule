package com.mawanjun.mindakebiao.net;

import com.mawanjun.mindakebiao.R;
import com.mawanjun.mindakebiao.model.Course;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 项目名称：MinDaKeBiao
 * 类描述：解析文档
 * 创建人：马万军
 * 创建时间：2017/1/1 23:35
 * 修改人：马万军
 * 修改时间：2017/1/1 23:35
 * 修改备注：
 */
public class CourseParse {
    private static final String TAG = "LoginParse";
    private static final int [] COLOR = {R.color.hole_blue,R.color.light_blue,
            R.color.light_green,R.color.light_pink};//

    public static boolean parseIsLoginSucceed(String data){
        Document doc = Jsoup.parse(data);
        //查看登陆文档。
        Element element = doc.getElementById("xhxm");
        if (element != null){
            return true;//
        }
        return false;
    }

    /*解析个人课表*/
    public static List<Course> parsePersonal(String data){
        List<Course> courses = new ArrayList<>();
        Document doc = Jsoup.parse(data);
        //首先获取Table
        Element table = doc.getElementById("Table1");
        //然后获取table中的td节点
        Elements trs = table.select("tr");
        //移除不需要的参数，这里表示移除前两个数值。
        trs.remove(0);
        trs.remove(0);
        //遍历td节点
        for (int i=0; i<trs.size(); ++i){
            Element tr = trs.get(i);
            //获取tr下的td节点，要求algin下的元素
            Elements tds = tr.select("td[align]");
            //遍历td节点
            for(int j=0; j<tds.size(); ++j){
                Element td = tds.get(j);
                String str = td.text();
                //如果数值为空则不计算。
                if (str.length() != 1){
                    //解析文本数据
                   String str1 = parsePersonalCourse(str);
                    Course course = new Course();
                    course.setClsName(str1);
                    course.setDay(j+1);
                    course.setClsCount(Integer.valueOf(td.attr("rowspan")));
                    course.setClsNum(i+1);
                    Random random = new Random();
                    int num = random.nextInt(COLOR.length);
                    course.setColor(COLOR[num]);
                    courses.add(course);
                    int m =  str.indexOf("}");
                    if (str.indexOf("{",m+2) != -1){
                        int p = parsePersonalCourse2(str);
                       String temp = str.substring(p);
                        String str2 = parsePersonalCourse(temp);
                        Course course2 = new Course();
                        course2.setClsName(str2);
                        course2.setDay(j+1);
                        course2.setClsCount(Integer.valueOf(td.attr("rowspan")));
                        course2.setClsNum(i+1);
                        course2.setColor(COLOR[num]);
                        courses.add(course2);
                    }
                }
            }
        }
        return courses;
    }

    private static String parsePersonalCourse(String text){
        String str = "哈";
        String  data = "哈";

        //正则表达式获取课名，和教室
        Pattern courseNamePattern = Pattern.compile("^.+?(\\s{1})");
        Matcher courseNameMatcher = courseNamePattern.matcher(text);
        if (courseNameMatcher.find()) {
            str = courseNameMatcher.group(0);
        }

        Pattern courseLocPattern = Pattern.compile("\\s{1}([0-9A-Z]){6}");
        Matcher courseLocMatcher = courseLocPattern.matcher(text);
        if (courseLocMatcher.find()) {
             data = courseLocMatcher.group(0);
        }

        String weekText = text.substring(text.indexOf("{")+1,text.indexOf("}"));

        Pattern startPattern = Pattern.compile("\\d+");
        Matcher startMatcher = startPattern.matcher(weekText);
        startMatcher.find();
        String startWeek =startMatcher.group();


        Pattern endPattern = Pattern.compile("\\d+");
        Matcher endMatcher = endPattern.matcher(weekText);
        endMatcher.find();
        endMatcher.find();
        String endWeek =endMatcher.group();

        String bWeek = booleanWeek(weekText);


        return str+"@"+data+ "*" + startWeek+ "#" + endWeek +"|" + bWeek;
    }
    private static int parsePersonalCourse2(String text){
        int i = 0;
        if (text.indexOf(")") != -1){
            Pattern courseLocPattern = Pattern.compile("\\s{1}(\\d+)");
            Matcher courseLocMatcher = courseLocPattern.matcher(text);
            courseLocMatcher.find();
            courseLocMatcher.find();
            courseLocMatcher.find();
            String data = courseLocMatcher.group(0);
            i = text.indexOf(data) + 8;
        }else {
            Pattern courseLocPattern = Pattern.compile("\\s{1}(\\d+)");
            Matcher courseLocMatcher = courseLocPattern.matcher(text);
            courseLocMatcher.find();
            courseLocMatcher.find();
            String data = courseLocMatcher.group(0);
            i = text.indexOf(data) + 8;
        }
        return i ;
    }
    private static String booleanWeek (String text ){
        String  temp = "全";
        int j = text.indexOf("|");
        if (j != -1){
            temp = text.substring(j+1,j+2);
        }else {
            temp = "全";
        }
        return temp;
    }
}
