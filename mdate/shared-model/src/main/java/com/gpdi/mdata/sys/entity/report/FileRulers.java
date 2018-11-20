package com.gpdi.mdata.sys.entity.report;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @description:招标规则稳定说明
 * @author: WangXiaoGang
 * @data: Created in 2018/10/11 9:29
 * @modifier:
 */
@Entity
@Table(name = "t_file_rules")
public class FileRulers implements Serializable {

    private static final long serialVersionUID = 5586745782679793169L;

    private Integer id;

    private Integer ruleNumber;//规则编号

    private String startDate;//生效日期

    private String endDate;//废止日期

    private String fileBasisName;//生效文件名称

    private String fileNumber;//生效文件编号

    private String fileAbolishName;//被废除文件名称

    private String fileAbolishNumber; //被废除文件编号

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//为实体生成唯一标识的主键并自增
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    @Column(name = "rule_number")
    public Integer getRuleNumber() {
        return ruleNumber;
    }

    public void setRuleNumber(Integer ruleNumber) {
        this.ruleNumber = ruleNumber;
    }
    @Column(name = "start_date")
    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    @Column(name = "end_date")
    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Column(name = "file_basis_name")
    public String getFileBasisName() {
        return fileBasisName;
    }

    public void setFileBasisName(String fileBasisName) {
        this.fileBasisName = fileBasisName;
    }
    @Column(name = "file_number")
    public String getFileNumber() {
        return fileNumber;
    }

    public void setFileNumber(String fileNumber) {
        this.fileNumber = fileNumber;
    }
    @Column(name = "file_abolish_name")
    public String getFileAbolishName() {
        return fileAbolishName;
    }

    public void setFileAbolishName(String fileAbolishName) {
        this.fileAbolishName = fileAbolishName;
    }
    @Column(name = "file_abolish_number")
    public String getFileAbolishNumber() {
        return fileAbolishNumber;
    }

    public void setFileAbolishNumber(String fileAbolishNumber) {
        this.fileAbolishNumber = fileAbolishNumber;
    }

    @Override
    public String toString() {
        return "FileRulers{" +
                "id=" + id +
                ", ruleNumber=" + ruleNumber +
                ", startDate='" + startDate + '\'' +
                ", fileBasisName='" + fileBasisName + '\'' +
                ", fileNumber='" + fileNumber + '\'' +
                ", fileAbolishName='" + fileAbolishName + '\'' +
                ", fileAbolishNumber='" + fileAbolishNumber + '\'' +
                '}';
    }
}
