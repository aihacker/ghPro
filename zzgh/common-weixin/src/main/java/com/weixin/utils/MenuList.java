package com.weixin.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author xlkai
 * @Version 2016/11/10
 */
public class MenuList {

    //基本类
    public static final String EXPOSEARTICLE = "menuItem:exposeArticle"; //举报
    public static final String SETFONT = "menuItem:setFont"; //调整字体
    public static final String DAYMODE = "menuItem:dayMode"; //日间模式
    public static final String NIGHTMODE = "menuItem:nightMode"; //夜间模式
    public static final String REFRESH = "menuItem:refresh"; //刷新
    public static final String PROFILE = "menuItem:profile"; //查看企业号（已添加）
    public static final String ADDCONTACT = "menuItem:addContact"; //查看企业号（未添加）

    //传播类
    public static final String SHARE_APPMESSAGE = "menuItem:share:appMessage"; //发送给朋友
    public static final String SHARE_TIMELINE = "menuItem:share:timeline"; //分享给朋友圈
    public static final String SHARE_QQ = "menuItem:share:qq"; //分享给QQ
    public static final String SHARE_QZONE = "menuItem:share:QZone"; //分享给QQ空间
    public static final String SHARE_WEIBOAPP = "menuItem:share:weiboApp"; //分享给Weibo
    public static final String FAVORITE = "menuItem:favorite"; //收藏
    public static final String SHARE_FACEBOOK = "menuItem:share:facebook"; //分享到FB

    //保护类
    public static final String JSDEBUG = "menuItem:jsDebug"; //调试
    public static final String EDITTAG = "menuItem:editTag"; //编辑标签
    public static final String DELETE = "menuItem:delete"; //删除
    public static final String COPYURL = "menuItem:copyUrl"; //复制链接
    public static final String ORIGINPAGE = "menuItem:originPage"; //原网页
    public static final String READMODE = "menuItem:readMode"; //阅读模式
    public static final String OPENWITHQQBROWSER = "menuItem:openWithQQBrowser"; //在QQ浏览器中打开
    public static final String OPENWITHSAFARI = "menuItem:openWithSafari"; //在Safari中打开
    public static final String SHARE_EMAIL = "menuItem:share:email"; //邮件
    public static final String SHARE_BRAND = "menuItem:share:brand";//一些特殊企业号

    /**
     * 获取默认隐藏的菜单
     *
     * @return
     */
    public static List<String> getDefultMenuList() {
        List<String> apiList = new ArrayList<>();
        apiList.add(EXPOSEARTICLE); //举报
        apiList.add(SHARE_APPMESSAGE); //发送给朋友
        apiList.add(SHARE_TIMELINE); //分享到朋友圈
        apiList.add(SHARE_QQ); //分享到QQ
        apiList.add(SHARE_QZONE); //分享到QQ空间
        apiList.add(SHARE_WEIBOAPP); //分享到微博
        apiList.add(FAVORITE); //收藏
        apiList.add(SHARE_FACEBOOK); //分享到FB
        apiList.add(COPYURL); //复制链接
        return apiList;
    }

    public static List<String> getMenuList() {
        return new ArrayList<>();
    }

    public static List<String> getShareMenuList() {
        List<String> menuList = getMenuList();
        menuList.add(EXPOSEARTICLE); //举报
        menuList.add(FAVORITE); //收藏
        menuList.add(SHARE_FACEBOOK); //分享到FB
        menuList.add(COPYURL); //复制链接
        return menuList;
    }
}
