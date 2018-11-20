package com.gpdi.mdata.web.utils;

/**
 * Created by Administrator on 2017/6/16.
 */
public enum ExcelType {

    XLSX("xlsx"),
    XLS("xls");

    private String type;

    ExcelType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
