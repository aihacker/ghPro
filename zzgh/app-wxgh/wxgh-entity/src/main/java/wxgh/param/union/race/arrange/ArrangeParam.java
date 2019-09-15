package wxgh.param.union.race.arrange;

/**
 * Created by Administrator on 2017/3/6.
 */
public class ArrangeParam {

    public static final Integer TYPE_XUNHUAN = 1; //单循环
    public static final Integer TYPE_GROUPXUNHUAN = 2; //分组循环
    public static final Integer TYPE_TAOTAI = 3; //单淘汰

    private Integer raceId; //比赛ID
    private Integer saizhi; //赛制
    private Integer addrNumb; //场地数量
    private String startTime; //比赛开始时间
    private String amTime; //上午开始时间
    private String pmTime; //下午开始时间
    private Float time; //每场地所需要时长
    private Integer xunhuan; //编排方式

    public Integer getRaceId() {
        return raceId;
    }

    public void setRaceId(Integer raceId) {
        this.raceId = raceId;
    }

    public Integer getSaizhi() {
        return saizhi;
    }

    public void setSaizhi(Integer saizhi) {
        this.saizhi = saizhi;
    }

    public Integer getAddrNumb() {
        return addrNumb;
    }

    public void setAddrNumb(Integer addrNumb) {
        this.addrNumb = addrNumb;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getAmTime() {
        return amTime;
    }

    public void setAmTime(String amTime) {
        this.amTime = amTime;
    }

    public String getPmTime() {
        return pmTime;
    }

    public void setPmTime(String pmTime) {
        this.pmTime = pmTime;
    }

    public Float getTime() {
        return time;
    }

    public void setTime(Float time) {
        this.time = time;
    }

    public Integer getXunhuan() {
        return xunhuan;
    }

    public void setXunhuan(Integer xunhuan) {
        this.xunhuan = xunhuan;
    }
}
