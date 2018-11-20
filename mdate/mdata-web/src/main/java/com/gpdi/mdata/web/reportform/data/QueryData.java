package com.gpdi.mdata.web.reportform.data;

import javax.naming.Name;

/**
 * 查询数据及参数
 */
public class QueryData {
    private String code;
    private String name;
    private Object obj;
    private Integer number;
    private String contractNum;//合同编号
    private String contractName;//合同名称
    private String supperName;//供应商名称
    private String type;//合同类型
    private String dept;//经办部门
    private String purchaseWay;//采购方式
    private String startTime;//开始时间
    private String endTime;//结束时间
    private Integer searchType = 1; //zxc根据什么查询
    private String hid;
    private String str1;
    private String str2;
    private String str3;
    private String str4;
    private String str5;

    private Double percent;//百分比
    private String contractNumber;//合同数量
    private String contractTypeName;//合同类型
    private String start;
    private String end;
    private Integer isExport;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getContractNum() { return contractNum; }

    public void setContractNum(String contractNum) { this.contractNum = contractNum; }

    public String getContractName() { return contractName; }

    public void setContractName(String contractName) { this.contractName = contractName; }

    public String getSupperName() { return supperName; }

    public void setSupperName(String supperName) { this.supperName = supperName; }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getPurchaseWay() {
        return purchaseWay;
    }

    public void setPurchaseWay(String purchaseWay) {
        this.purchaseWay = purchaseWay;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getSearchType() {
        return searchType;
    }

    public void setSearchType(Integer searchType) {
        this.searchType = searchType;
    }

    public String getHid() {
        return hid;
    }

    public void setHid(String hid) {
        this.hid = hid;
    }

    public String getStr1() {
        return str1;
    }

    public void setStr1(String str1) {
        this.str1 = str1;
    }

    public String getStr2() {
        return str2;
    }

    public void setStr2(String str2) {
        this.str2 = str2;
    }

    public String getStr3() {
        return str3;
    }

    public void setStr3(String str3) {
        this.str3 = str3;
    }

    public String getStr4() {
        return str4;
    }

    public void setStr4(String str4) {
        this.str4 = str4;
    }

    public String getStr5() {
        return str5;
    }

    public void setStr5(String str5) {
        this.str5 = str5;
    }

    public Double getPercent() {
        return percent;
    }

    public void setPercent(Double percent) {
        this.percent = percent;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public String getContractTypeName() {
        return contractTypeName;
    }

    public void setContractTypeName(String contractTypeName) {
        this.contractTypeName = contractTypeName;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public Integer getIsExport() {
        return isExport;
    }

    public void setIsExport(Integer isExport) {
        this.isExport = isExport;
    }

    @Override
    public String toString() {
        return "QueryData{" + "code='" + code + '\'' + ", name='" + name + '\'' + ", obj=" + obj + ", number=" + number + ", contractNum='" + contractNum + '\'' + ", contractName='" + contractName + '\'' + ", supperName='" + supperName + '\'' + ", type='" + type + '\'' + ", dept='" + dept + '\'' + ", purchaseWay='" + purchaseWay + '\'' + ", startTime='" + startTime + '\'' + ", endTime='" + endTime + '\'' + ", hid='" + hid + '\'' + ", str1='" + str1 + '\'' + ", str2='" + str2 + '\'' + ", str3='" + str3 + '\'' + ", str4='" + str4 + '\'' + ", str5='" + str5 + '\'' + ", percent=" + percent + ", contractNumber='" + contractNumber + '\'' + ", contractTypeName='" + contractTypeName + '\'' + ", start='" + start + '\'' + ", end='" + end + '\'' +", isExport='"+ isExport+'\''+'}';
    }
}
