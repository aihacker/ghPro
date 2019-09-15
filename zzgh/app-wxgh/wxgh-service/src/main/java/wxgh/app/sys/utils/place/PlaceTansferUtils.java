package wxgh.app.sys.utils.place;


import com.libs.common.type.TypeUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * @Version 1.0
 * --------------------------------------------------------- *
 * @Description >_
 * --------------------------------------------------------- *
 * @Author Ape
 * --------------------------------------------------------- *
 * @Email <16511660@qq.com>
 * --------------------------------------------------------- *
 * @Date 2017-11-01  14:19
 * --------------------------------------------------------- *
 */
public class PlaceTansferUtils {

    public static String week(Integer week){
        switch (week){
            case 1:
                return "星期一";
            case 2:
                return "星期二";
            case 3:
                return "星期三";
            case 4:
                return "星期四";
            case 5:
                return "星期五";
            case 6:
                return "星期六";
            case 7:
                return "星期天";
        }
        return "";
    }

    public static String time(String timeIds){
        List<String> list = TypeUtils.strToList(timeIds);
        List<String> r = new LinkedList<>();
        for (String id : list){
            if(id.equals("1"))
                r.add("上午");
            else if(id.equals("2"))
                r.add("下午");
            else if(id.equals("3"))
                r.add("晚上");
        }
        return TypeUtils.listToStr(r, "、");
    }

}
