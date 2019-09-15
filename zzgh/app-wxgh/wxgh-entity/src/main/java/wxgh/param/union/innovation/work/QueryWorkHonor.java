package wxgh.param.union.innovation.work;

import java.io.Serializable;
import java.util.List;

/**
 * Created by WIN on 2016/9/12.
 */
public class QueryWorkHonor implements Serializable {

    private String userId;
    private Integer status;
    private Integer start;
    private Integer num;
    private Integer id;

    private Integer isFirst;

    private Integer honorOldestId;

    private Integer deptidC;

    private String content;
    private String txt;
    private List<String> imgList;
    private String honorName;
    private String remark;
    private String honorLevel;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public List<String> getImgList() {
        return imgList;
    }

    public void setImgList(List<String> imgList) {
        this.imgList = imgList;
    }

    public String getHonorName() {
        return honorName;
    }

    public void setHonorName(String honorName) {
        this.honorName = honorName;
    }

    public String getHonorLevel() {
        return honorLevel;
    }

    public void setHonorLevel(String honorLevel) {
        this.honorLevel = honorLevel;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getDeptidC() {
        return deptidC;
    }

    public void setDeptidC(Integer deptidC) {
        this.deptidC = deptidC;
    }

    public Integer getIsFirst() {
        return isFirst;
    }

    public void setIsFirst(Integer isFirst) {
        this.isFirst = isFirst;
    }

    public Integer getHonorOldestId() {
        return honorOldestId;
    }

    public void setHonorOldestId(Integer honorOldestId) {
        this.honorOldestId = honorOldestId;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
