package wxgh.app.utils;


import pub.utils.StrUtils;
import wxgh.param.pub.TipMsg;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by Administrator on 2016/10/20.
 */
public class UrlUtils {

    /**
     * encode
     *
     * @param txt
     * @return
     */
    public static String URLEncode(String txt) {
        try {
            if (StrUtils.empty(txt)) {
                return "";
            }
            return URLEncoder.encode(txt, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String URLDecode(String txt) {
        if (StrUtils.empty(txt)) {
            return "";
        }
        try {
            return URLDecoder.decode(txt, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getTipUrl(TipMsg tipMsg) {
        String redirect = "";
        try {
            StringBuilder urlBuilder = new StringBuilder("/tip/show.wx");
            urlBuilder.append("?type=" + tipMsg.getType());
            if (tipMsg.getMsg() != null) {
                urlBuilder.append("&msg=" + URLEncoder.encode(tipMsg.getMsg(), "UTF-8"));
            }
            if (tipMsg.getUrl() != null) {
                urlBuilder.append("&url=" + URLEncoder.encode(tipMsg.getUrl(), "UTF-8"));
            }
            if (tipMsg.getUrlMsg() != null) {
                urlBuilder.append("&urlMsg=" + URLEncoder.encode(tipMsg.getUrlMsg(), "UTF-8"));
            }
            if (tipMsg.getTitle() != null) {
                urlBuilder.append("&title=" + URLEncoder.encode(tipMsg.getTitle(), "UTF-8"));
            }
            redirect = urlBuilder.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return redirect;
    }

}
