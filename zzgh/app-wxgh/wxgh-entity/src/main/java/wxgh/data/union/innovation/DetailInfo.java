package wxgh.data.union.innovation;


import wxgh.entity.union.innovation.InnovateAdvice;
import wxgh.entity.union.innovation.InnovateMicro;
import wxgh.entity.union.innovation.InnovateRace;
import wxgh.entity.union.innovation.InnovateShop;
import wxgh.entity.union.innovation.WorkResult;

import java.util.Date;

public class DetailInfo {
    private Integer id;
    private Integer applyType;
    private Date time;
    private String applicant;
    private Integer status;

    private InnovateRace innovateRace;
    private InnovateShop innovateShop;
    private InnovateMicro innovateMicro;
    private InnovateAdvice innovateAdvice;

    private WorkResult workResult;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getApplyType() {
        return applyType;
    }

    public void setApplyType(Integer applyType) {
        this.applyType = applyType;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public InnovateRace getInnovateRace() {
        return innovateRace;
    }

    public void setInnovateRace(InnovateRace innovateRace) {
        this.innovateRace = innovateRace;
    }

    public InnovateShop getInnovateShop() {
        return innovateShop;
    }

    public void setInnovateShop(InnovateShop innovateShop) {
        this.innovateShop = innovateShop;
    }

    public InnovateMicro getInnovateMicro() {
        return innovateMicro;
    }

    public void setInnovateMicro(InnovateMicro innovateMicro) {
        this.innovateMicro = innovateMicro;
    }

    public InnovateAdvice getInnovateAdvice() {
        return innovateAdvice;
    }

    public void setInnovateAdvice(InnovateAdvice innovateAdvice) {
        this.innovateAdvice = innovateAdvice;
    }

    public WorkResult getWorkResult() {
        return workResult;
    }

    public void setWorkResult(WorkResult workResult) {
        this.workResult = workResult;
    }
}
;