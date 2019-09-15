package wxgh.app.callback;

import wxgh.entity.pub.SysFile;

import java.io.File;

/**
 * Created by Administrator on 2017/7/18.
 */
public interface SuccessCallBack {

    public abstract void onSuccess(SysFile paramSysFile, File paramFile);

}
