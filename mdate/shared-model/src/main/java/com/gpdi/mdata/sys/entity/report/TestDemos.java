package com.gpdi.mdata.sys.entity.report;

import javax.persistence.Entity;

/**
 * @description:
 * @author: WangXiaoGang
 * @data: Created in 2018/10/29 10:15
 * @modifier:
 */
@Entity
public class TestDemos {
    private String cgjgbm;

    private String shxx;

    public String getCgjgbm() {
        return cgjgbm;
    }

    public void setCgjgbm(String cgjgbm) {
        this.cgjgbm = cgjgbm;
    }

    public String getShxx() {
        return shxx;
    }

    public void setShxx(String shxx) {
        this.shxx = shxx;
    }


}
