package com.weixin.utils;

import com.weixin.bean.ErrResult;
import com.weixin.bean.FileBean;

/**
 * Created by Administrator on 2017/7/18.
 */
public interface DownloadCallback {

    void onFaile(Exception e);

    void onError(ErrResult errResult);

    void onSuccess(FileBean fileBean);

}
