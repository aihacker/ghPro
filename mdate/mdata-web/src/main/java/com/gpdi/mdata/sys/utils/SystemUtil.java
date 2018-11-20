package com.gpdi.mdata.sys.utils;

import static java.lang.System.getProperty;

/**
 * Created by lihz on 2017/6/26.
 */
public class SystemUtil {
    private SystemUtil(){
    }

    public static boolean isWindows(){
        String os = System.getProperty("os.name").toLowerCase();
        return os.contains("window");
    }
}
