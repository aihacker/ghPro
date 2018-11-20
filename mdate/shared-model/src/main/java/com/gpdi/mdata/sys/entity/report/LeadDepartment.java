package com.gpdi.mdata.sys.entity.report;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @description:领导任职部门情况
 * @author: WangXiaoGang
 * @data: Created in 2018/10/17 11:18
 * @modifier:
 */
@Entity
@Table(name = "t_leader_work_dept")
public class LeadDepartment implements Serializable{

    private static final long serialVersionUID = -5736233563524767835L;

    private int id;

    private String leaderName;//领导名称

    private String workDepartment;//任职部门

    private String startTime;//开始时间

    private String endTime;//结束时间


    private String allSuplier;//单个领导签的所有部门中的所有的供应商

    private String allSuplierNum;//单个领导签的所有部门中的所有的供应商+数量

    private List<String> newSupplerNum;//集合

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    @Column(name ="leader_name")
    public String getLeaderName() {
        return leaderName;
    }

    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName;
    }
    @Column(name ="work_department" )
    public String getWorkDepartment() {
        return workDepartment;
    }

    public void setWorkDepartment(String workDepartment) {
        this.workDepartment = workDepartment;
    }
    @Column(name = "start_time")
    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
    @Column(name = "end_time")
    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getAllSuplier() {
        return allSuplier;
    }

    public void setAllSuplier(String allSuplier) {
        this.allSuplier = allSuplier;
    }

    public String getAllSuplierNum() {
        return allSuplierNum;
    }

    public void setAllSuplierNum(String allSuplierNum) {
        this.allSuplierNum = allSuplierNum;
    }

    public List<String> getNewSupplerNum() {
        return newSupplerNum;
    }

    public void setNewSupplerNum(List<String> newSupplerNum) {
        this.newSupplerNum = newSupplerNum;
    }

    @Override
    public String toString() {
        return "LeadDepartment{" +
                "id=" + id +
                ", leaderName='" + leaderName + '\'' +
                ", workDepartment='" + workDepartment + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", allSuplier='" + allSuplier + '\'' +
                ", allSuplierNum='" + allSuplierNum + '\'' +
                ", newSupplerNum=" + newSupplerNum +
                '}';
    }
}
