package com.gpdi.mdata.sys.entity.report;

/**
 * @description:
 * @author: WangXiaoGang
 * @data: Created in 2018/9/25 17:11
 * @modifier:
 */
public class CleanTemps  {
    private String supplierName;
    private int nam;
    private double percent;
    private int sum;

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public int getNam() {
        return nam;
    }

    public void setNam(int nam) {
        this.nam = nam;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    @Override
    public String toString() {
        return "CleanTemps{" +
                "supplierName='" + supplierName + '\'' +
                ", nam=" + nam +
                ", percent=" + percent +
                ", sum=" + sum +
                '}';
    }
}
