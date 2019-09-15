package com.weixin.config;

import com.libs.common.file.FileUtils;
import com.libs.common.type.TypeUtils;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.io.File;

/**
 * Created by XLKAI on 2017/7/8.
 */
public class Config {

    public static String HOST = "https://qyapi.weixin.qq.com/cgi-bin";

    public static String APPID;

    public static String SECRET;

    public static String TOKEN_PATH;

    public static Boolean DEBUG;

    public static NamedParameterJdbcTemplate jdbcTemplate;

    public static File getWeixinFile(String filename) {
        File dir;
        if (!TypeUtils.empty(TOKEN_PATH)) {
            dir = new File(TOKEN_PATH);
        } else {
            File tmpDir = FileUtils.getTemp();
            dir = new File(tmpDir, "/weixin");
        }
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir, filename + ".xml");
        return file;
    }
}
