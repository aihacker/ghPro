package wxgh.entity.entertain.sport;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * author：xlkai
 * date：2018/1/5
 * time：17:20
 * version：V1.0
 * Description：
 */
public class SportAct implements Serializable {

    private Integer id;
    private String name;
    private Integer start;
    private Integer end;
    private String info;
    private String img;
    private String bgImg;
    private String deptids;
    private Integer status;
    private Date addTime;

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

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getEnd() {
        return end;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getBgImg() {
        return bgImg;
    }

    public void setBgImg(String bgImg) {
        this.bgImg = bgImg;
    }

    public String getDeptids() {
        return deptids;
    }

    public void setDeptids(String deptids) {
        this.deptids = deptids;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    @Override
    public String toString() {
        return "SportAct{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", start=" + start +
                ", end=" + end +
                ", info='" + info + '\'' +
                ", img='" + img + '\'' +
                ", bgImg='" + bgImg + '\'' +
                ", deptids='" + deptids + '\'' +
                ", status=" + status +
                ", addTime=" + addTime +
                '}';
    }
}
