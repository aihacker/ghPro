package com.gpdi.mdata.sys.service.reportform.daoexcel.purchaseparent;

import java.io.Serializable;

public class QueryData implements Serializable {

    private String code;

    private String md5;

    private String date;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
