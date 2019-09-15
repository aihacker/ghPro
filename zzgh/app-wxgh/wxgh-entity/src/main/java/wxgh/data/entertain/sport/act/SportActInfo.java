package wxgh.data.entertain.sport.act;

import wxgh.entity.entertain.sport.SportAct;

/**
 * Created with IntelliJ IDEA.
 * author：xlkai
 * date：2018/1/5
 * time：17:31
 * version：V1.0
 * Description：
 */
public class SportActInfo extends SportAct {

    private String imgPath;
    private String bgPath;
    private Integer actId;
    private String deptIds;

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getBgPath() {
        return bgPath;
    }

    public void setBgPath(String bgPath) {
        this.bgPath = bgPath;
    }

    public Integer getActId() {
        return actId;
    }

    public void setActId(Integer actId) {
        this.actId = actId;
    }

    public String getDeptIds() {
        return deptIds;
    }

    public void setDeptIds(String deptIds) {
        this.deptIds = deptIds;
    }

    @Override
    public String toString() {
        return "SportActInfo{" +
                "imgPath='" + imgPath + '\'' +
                ", bgPath='" + bgPath + '\'' +
                ", actId=" + actId +
                ", deptIds='" + deptIds + '\'' +
                '}';
    }
}
