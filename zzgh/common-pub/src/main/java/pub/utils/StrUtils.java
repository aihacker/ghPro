package pub.utils;


import java.util.*;

/**
 * Created by XDLK on 2016/6/22.
 * <p>
 * Date： 2016/6/22
 * <p>
 * 字符串工具类
 */
public class StrUtils {

    public static final String CHAR = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz";

    /**
     * 判断字符串是否为空，空返回true
     *
     * @param text
     * @return
     */
    public static boolean empty(String text) {
        if (text != null && !"".equals(text.trim())) {
            return false;
        }
        return true;
    }

    public static String charProcess(String str) {
        if (str == null) str = "";

        return str;
    }

    /**
     * 获取uuid
     *
     * @return
     */
    public static String getUUID() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replace("-", "");
    }

    public static String rand_int(int len) {
        String str = "0123456789";

        str += (str + str + str + str);

        List<String> list = Arrays.asList(str.split(""));
        Collections.shuffle(list);

        Random random = new Random();
        int size = list.size();
        String newStr = "";
        for (int i = 0; i < len; i++) {
            newStr += list.get(random.nextInt(size));
        }

        return newStr;
    }

    public static String rand_string(int len) {
        String base = new String(CHAR);
        base += base;

        List<String> list = Arrays.asList(base.split(""));
        Collections.shuffle(list);

        Random random = new Random();
        int size = list.size();
        String newStr = "";
        for (int i = 0; i < len; i++) {
            newStr += list.get(random.nextInt(size));
        }
        return newStr;
    }



}
