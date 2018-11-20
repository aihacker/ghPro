package com.gpdi.mdata.sys.entity.report;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @description:招标方式
 * @author: WangXiaoGang
 * @data: Created in 2018/10/13 16:41
 * @modifier:
 */
@Entity
@Table(name = "t_file_rules_rel")
public class TenderingrulesWay  implements Serializable{
    private static final long serialVersionUID = 7744499985814447552L;

    private String id;
    private String way;//方式
    private String projectGoodsOne ;//工程货物1
    private String projectGoodsTwo ; //工程货物2
    private String projectServicesOne ; //工程服务1
    private String projectServicesTwo ; //工程服务2
    private String noProjectGoodsOne ; //非工程货物1
    private String noProjectGoodsTwo ; //非工程货物2
    private String noProjectGoodsThree ; //非工程货物3
    private String integratedServicesOne ; //综合服务1
    private String integratedServicesTwo ; //综合服务2
    private String integratedServicesThree ; //综合服务3
    private Integer parent ; //规则编号

    @Id//设置主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)//设置id自增
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    @Column(name = "way")
    public String getWay() {
        return way;
    }

    public void setWay(String way) {
        this.way = way;
    }
    @Column(name = "project_goods_one")
    public String getProjectGoodsOne() {
        return projectGoodsOne;
    }

    public void setProjectGoodsOne(String projectGoodsOne) {
        this.projectGoodsOne = projectGoodsOne;
    }
    @Column(name = "project_goods_two")
    public String getProjectGoodsTwo() {
        return projectGoodsTwo;
    }

    public void setProjectGoodsTwo(String projectGoodsTwo) {
        this.projectGoodsTwo = projectGoodsTwo;
    }
    @Column(name = "project_services_one")
    public String getProjectServicesOne() {
        return projectServicesOne;
    }

    public void setProjectServicesOne(String projectServicesOne) {
        this.projectServicesOne = projectServicesOne;
    }
    @Column(name = "project_services_two")
    public String getProjectServicesTwo() {
        return projectServicesTwo;
    }

    public void setProjectServicesTwo(String projectServicesTwo) {
        this.projectServicesTwo = projectServicesTwo;
    }
    @Column(name = "no_project_goods_one")
    public String getNoProjectGoodsOne() {
        return noProjectGoodsOne;
    }

    public void setNoProjectGoodsOne(String noProjectGoodsOne) {
        this.noProjectGoodsOne = noProjectGoodsOne;
    }
    @Column(name = "no_project_goods_two")
    public String getNoProjectGoodsTwo() {
        return noProjectGoodsTwo;
    }

    public void setNoProjectGoodsTwo(String noProjectGoodsTwo) {
        this.noProjectGoodsTwo = noProjectGoodsTwo;
    }
    @Column(name = "no_project_goods_three")
    public String getNoProjectGoodsThree() {
        return noProjectGoodsThree;
    }

    public void setNoProjectGoodsThree(String noProjectGoodsThree) {
        this.noProjectGoodsThree = noProjectGoodsThree;
    }
    @Column(name = "integrated_services_one")
    public String getIntegratedServicesOne() {
        return integratedServicesOne;
    }

    public void setIntegratedServicesOne(String integratedServicesOne) {
        this.integratedServicesOne = integratedServicesOne;
    }
    @Column(name = "integrated_services_two")
    public String getIntegratedServicesTwo() {
        return integratedServicesTwo;
    }

    public void setIntegratedServicesTwo(String integratedServicesTwo) {
        this.integratedServicesTwo = integratedServicesTwo;
    }
    @Column(name = "integrated_services_three")
    public String getIntegratedServicesThree() {
        return integratedServicesThree;
    }

    public void setIntegratedServicesThree(String integratedServicesThree) {
        this.integratedServicesThree = integratedServicesThree;
    }

    @Column(name = "parent")
    public Integer getParent() {
        return parent;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }




    @Override
    public String toString() {
        return "TenderingrulesWay{" +
                "id='" + id + '\'' +
                ", way='" + way + '\'' +
                ", projectGoodsOne='" + projectGoodsOne + '\'' +
                ", projectGoodsTwo='" + projectGoodsTwo + '\'' +
                ", projectServicesOne='" + projectServicesOne + '\'' +
                ", projectServicesTwo='" + projectServicesTwo + '\'' +
                ", noProjectGoodsOne='" + noProjectGoodsOne + '\'' +
                ", noProjectGoodsTwo='" + noProjectGoodsTwo + '\'' +
                ", noProjectGoodsThree='" + noProjectGoodsThree + '\'' +
                ", integratedServicesOne='" + integratedServicesOne + '\'' +
                ", integratedServicesTwo='" + integratedServicesTwo + '\'' +
                ", integratedServicesThree='" + integratedServicesThree + '\'' +
                ", parent='" + parent + '\'' +
                '}';
    }
}
