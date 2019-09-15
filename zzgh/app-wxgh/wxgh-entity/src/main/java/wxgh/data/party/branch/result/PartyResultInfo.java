package wxgh.data.party.branch.result;


import wxgh.data.common.FileData;
import wxgh.data.common.FileInfo;
import wxgh.entity.party.party.PartyActResult;

import java.util.List;

/**
 * @Version 1.0
 * --------------------------------------------------------- *
 * @Description >_
 * --------------------------------------------------------- *
 * @Author Ape
 * --------------------------------------------------------- *
 * @Email <16511660@qq.com>
 * --------------------------------------------------------- *
 * @Date 2018-01-19  11:15
 * --------------------------------------------------------- *
 */
public class PartyResultInfo extends PartyActResult {

    private String avatar;
    private String username;
    private String timeStr;
    private Integer zanIs;

    private List<FileData> imgFiles;
    private List<FileInfo> fileList;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }

    public Integer getZanIs() {
        return zanIs;
    }

    public void setZanIs(Integer zanIs) {
        this.zanIs = zanIs;
    }

    public List<FileData> getImgFiles() {
        return imgFiles;
    }

    public void setImgFiles(List<FileData> imgFiles) {
        this.imgFiles = imgFiles;
    }

    public List<FileInfo> getFileList() {
        return fileList;
    }

    public void setFileList(List<FileInfo> fileList) {
        this.fileList = fileList;
    }
}
