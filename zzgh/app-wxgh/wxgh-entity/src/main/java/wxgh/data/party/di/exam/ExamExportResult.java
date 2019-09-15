package wxgh.data.party.di.exam;


import java.io.Serializable;

/**
 * @Version 1.0
 * --------------------------------------------------------- *
 * @Description >_
 * --------------------------------------------------------- *
 * @Author Ape
 * --------------------------------------------------------- *
 * @Email <16511660@qq.com>
 * --------------------------------------------------------- *
 * @Date 2017-09-22  11:23
 * --------------------------------------------------------- *
 */
public class ExamExportResult implements Serializable {

    private String username;
    private String companyName;
    private String deptName;
    private String mobile;
    private Integer rightTotal;
    private Integer errorTotal;
    private Integer prize;

    public Integer getPrize() {
        return prize;
    }

    public void setPrize(Integer prize) {
        this.prize = prize;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getRightTotal() {
        return rightTotal;
    }

    public void setRightTotal(Integer rightTotal) {
        this.rightTotal = rightTotal;
    }

    public Integer getErrorTotal() {
        return errorTotal;
    }

    public void setErrorTotal(Integer errorTotal) {
        this.errorTotal = errorTotal;
    }
}
