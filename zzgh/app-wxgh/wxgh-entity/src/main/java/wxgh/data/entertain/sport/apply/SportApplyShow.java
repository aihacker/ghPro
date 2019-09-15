package wxgh.data.entertain.sport.apply;

import wxgh.data.common.FileData;
import wxgh.entity.entertain.sport.SportApply;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * author：xlkai
 * date：2018/1/9
 * time：11:39
 * version：V1.0
 * Description：
 */
public class SportApplyShow extends SportApply {

    private String username;
    private List<FileData> files;
    private Integer step; //当天最后步数
    private Date lastTime; //最后更新时间

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<FileData> getFiles() {
        return files;
    }

    public void setFiles(List<FileData> files) {
        this.files = files;
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }
}
