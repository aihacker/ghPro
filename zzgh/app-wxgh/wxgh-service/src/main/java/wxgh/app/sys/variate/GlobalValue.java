package wxgh.app.sys.variate;

/**
 * Created by Administrator on 2017/7/12.
 */
public class GlobalValue {

    public static String appName = "zzgh"; //项目名称

    public static String host = "http://wxgh.fsdxhlwgh.cn:8085"; //项目域名

    public static String mileagehost = "http://wxgh.fsdxhlwgh.cn:8099"; //公车里程上传服务域名

    public static String getUrl() {
        return host + "/" + appName;
    }
}
