package com.weixin.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author xlkai
 * @Version 2016/11/10
 */
public class ApiList {

    public static final String OPENENTERPRISECHAT = "openEnterpriseChat"; //创建企业会话
    public static final String OPENENTERPRISECONTACT = "selectEnterpriseContact"; //打开企业通讯录选人openEnterpriseContact
    public static final String ONMENUSHARETIMELINE = "onMenuShareTimeline"; //获取“分享到朋友圈”按钮点击状态及自定义分享内容接口
    public static final String ONMENUSHAREAPPMESSAGE = "onMenuShareAppMessage"; //获取“分享给朋友”按钮点击状态及自定义分享内容接口
    public static final String ONMENUSHAREQQ = "onMenuShareQQ"; //获取“分享到QQ”按钮点击状态及自定义分享内容接口
    public static final String ONMENUSHAREWEIBO = "onMenuShareWeibo"; //获取“分享到腾讯微博”按钮点击状态及自定义分享内容接口
    public static final String ONMENUSHAREQZONE = "onMenuShareQZone"; //获取“分享到QQ空间”按钮点击状态及自定义分享内容接口
    public static final String STARTRECORD = "startRecord"; //开始录音接口
    public static final String STOPRECORD = "stopRecord"; //停止录音接口
    public static final String ONVOICERECORDEND = "onVoiceRecordEnd"; //监听录音自动停止接口
    public static final String PLAYVOICE = "playVoice"; //播放语音接口
    public static final String PAUSEVOICE = "pauseVoice"; //暂停播放接口
    public static final String STOPVOICE = "stopVoice"; //停止播放接口
    public static final String ONVOICEPLAYEND = "onVoicePlayEnd"; //监听语音播放完毕接口
    public static final String UPLOADVOICE = "uploadVoice"; //上传语音接口
    public static final String DOWNLOADVOICE = "downloadVoice"; //下载语音接口
    public static final String CHOOSEIMAGE = "chooseImage";  //选择图片
    public static final String PREVIEWIMAGE = "previewImage"; //预览图片
    public static final String UPLOADIMAGE = "uploadImage"; //上传图片
    public static final String DOWNLOADIMAGE = "downloadImage"; //下载图片
    public static final String TRANSLATEVOICE = "translateVoice"; //识别音频并返回识别结果接口
    public static final String GETNETWORKTYPE = "getNetworkType"; //获取网络状态
    public static final String OPENLOCATION = "openLocation"; //使用微信内置地图查看位置接口
    public static final String GETLOCATION = "getLocation"; //获取地理位置接口
    public static final String HIDEOPTIONMENU = "hideOptionMenu"; //隐藏右上角菜单接口
    public static final String SHOWOPTIONMENU = "showOptionMenu"; //显示右上角菜单接口
    public static final String HIDEMENUITEMS = "hideMenuItems"; //批量隐藏功能按钮接口
    public static final String SHOWMENUITEMS = "showMenuItems"; //批量显示功能按钮接口
    public static final String HIDEALLNONBASEMENUITEM = "hideAllNonBaseMenuItem"; //隐藏所有非基础按钮接口
    public static final String SHOWALLNONBASEMENUITEM = "showAllNonBaseMenuItem"; //显示所有功能按钮接口
    public static final String CLOSEWINDOW = "closeWindow"; //关闭当前网页窗口接口
    public static final String SCANQRCODE = "scanQRCode"; //调起微信扫一扫接口


    /**
     * 获取选择图片Api列表
     *
     * @return
     */
    public static List<String> getImageApi() {
        List<String> apiList = getApiList();
        apiList.add(CHOOSEIMAGE);
        apiList.add(PREVIEWIMAGE);
        apiList.add(UPLOADIMAGE);
        return apiList;
    }

    public static List<String> getShareApi() {
        List<String> apiList = getApiList();
        apiList.add(ONMENUSHAREAPPMESSAGE);
        apiList.add(ONMENUSHAREQQ);
        apiList.add(ONMENUSHAREQZONE);
        apiList.add(ONMENUSHARETIMELINE);
        apiList.add(ONMENUSHAREWEIBO);
        return apiList;
    }

    /**
     * 关闭窗口API
     *
     * @return
     */
    public static List<String> getCloseApi() {
        List<String> apiList = getApiList();
        apiList.add(CLOSEWINDOW);
        return apiList;
    }

    /**
     * 打开联系人
     *
     * @return
     */
    public static List<String> getContactApi() {
        List<String> apiList = getApiList();
        apiList.add(OPENENTERPRISECONTACT);
        return apiList;
    }

    /**
     * 获取默认Api列表
     *
     * @return
     */
    public static List<String> getApiList() {
        List<String> apiList = new ArrayList<>();
        apiList.add(HIDEMENUITEMS);
        return apiList;
    }

}
