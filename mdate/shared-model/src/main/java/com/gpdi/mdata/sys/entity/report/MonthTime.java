package com.gpdi.mdata.sys.entity.report;

/**
 * @description:
 * @author: WangXiaoGang
 * @data: Created in 2018/9/25 17:11
 * @modifier:
 */
public class MonthTime {
    private int month;
    private int time;
    private  String mileage;
    private  String litre;
    private  String oil;
    private  String axleFee;
    private  String violations;
    private  String carRepairing ;
    private  String CarNature;
    private  String totalRepair;
    private  int repairingId;
    private  Double carRepairFee;//修车费

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public String getOil() {
        return oil;
    }

    public void setOil(String oil) {
        this.oil = oil;
    }

    public String getAxleFee() {
        return axleFee;
    }

    public void setAxleFee(String axleFee) {
        this.axleFee = axleFee;
    }

    public String getViolations() {
        return violations;
    }

    public void setViolations(String violations) {
        this.violations = violations;
    }

    public String getCarRepairing() {
        return carRepairing;
    }

    public void setCarRepairing(String carRepairing) {
        this.carRepairing = carRepairing;
    }

    public String getCarNature() {
        return CarNature;
    }

    public void setCarNature(String carNature) {
        CarNature = carNature;
    }

    public String getTotalRepair() {
        return totalRepair;
    }

    public void setTotalRepair(String totalRepair) {
        this.totalRepair = totalRepair;
    }

    public int getRepairingId() {
        return repairingId;
    }

    public void setRepairingId(int repairingId) {
        this.repairingId = repairingId;
    }

    public String getLitre() {
        return litre;
    }

    public void setLitre(String litre) {
        this.litre = litre;
    }

    public Double getCarRepairFee() {
        return carRepairFee;
    }

    public void setCarRepairFee(Double carRepairFee) {
        this.carRepairFee = carRepairFee;
    }

    @Override
    public String toString() {
        return "MonthTime{" + "month=" + month + ", time=" + time + ", mileage='" + mileage + '\'' + ", litre='" + litre + '\'' + ", oil='" + oil + '\'' + ", axleFee='" + axleFee + '\'' + ", violations='" + violations + '\'' + ", carRepairing='" + carRepairing + '\'' + ", CarNature='" + CarNature + '\'' + ", totalRepair='" + totalRepair + '\'' + ", repairingId=" + repairingId + ", carRepairFee=" + carRepairFee + '}';
    }
}
