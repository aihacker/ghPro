package wxgh.data.union.woman.mom;

import wxgh.data.common.FileData;

import java.util.List;

/**
 * Created by Administrator on 2017/9/14.
 */
public class MomShow {

    private Integer id;
    private String name;
    private String info;
    private String week;
    private String cover;
    private Integer yuyueNum;
    private List<FileData> fileList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Integer getYuyueNum() {
        return yuyueNum;
    }

    public void setYuyueNum(Integer yuyueNum) {
        this.yuyueNum = yuyueNum;
    }

    public List<FileData> getFileList() {
        return fileList;
    }

    public void setFileList(List<FileData> fileList) {
        this.fileList = fileList;
    }
}
