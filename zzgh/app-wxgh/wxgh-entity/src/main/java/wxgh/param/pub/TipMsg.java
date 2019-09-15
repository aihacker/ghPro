package wxgh.param.pub;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Administrator on 2017/7/13.
 */
public class TipMsg {

    private String msg;
    private String url;
    private String urlMsg;
    private String title;
    private Integer type;

    public static TipMsg success(String title, String msg) {
        TipMsg tipMsg = new TipMsg();
        tipMsg.setTitle(title);
        tipMsg.setMsg(msg);
        tipMsg.setType(1);
        return tipMsg;
    }

    public static TipMsg error(String title, String msg) {
        TipMsg tipMsg = success(title, msg);
        tipMsg.setType(2);
        return tipMsg;
    }

    public static TipMsg info(String title, String msg) {
        TipMsg tipMsg = success(title, msg);
        tipMsg.setType(3);
        return tipMsg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlMsg() {
        return urlMsg;
    }

    public void setUrlMsg(String urlMsg) {
        this.urlMsg = urlMsg;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTipUrl() {
        String redirect = "";
        try {
            StringBuilder urlBuilder = new StringBuilder("/wx/pub/tip/show.html");
            urlBuilder.append("?type=" + type);
            if (msg != null) {
                urlBuilder.append("&msg=" + URLEncoder.encode(msg, "UTF-8"));
            }
            if (url != null) {
                urlBuilder.append("&url=" + URLEncoder.encode(url, "UTF-8"));
            }
            if (urlMsg != null) {
                urlBuilder.append("&urlMsg=" + URLEncoder.encode(urlMsg, "UTF-8"));
            }
            if (title != null) {
                urlBuilder.append("&title=" + URLEncoder.encode(title, "UTF-8"));
            }
            redirect = urlBuilder.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "redirect:" + redirect;
    }

}
