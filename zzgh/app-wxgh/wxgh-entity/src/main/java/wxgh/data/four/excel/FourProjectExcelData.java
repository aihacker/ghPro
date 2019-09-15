package wxgh.data.four.excel;


/**
 * @Version 1.0
 * --------------------------------------------------------- *
 * @Description >_
 * --------------------------------------------------------- *
 * @Author Ape
 * --------------------------------------------------------- *
 * @Email <16511660@qq.com>
 * --------------------------------------------------------- *
 * @Date 2017-09-04  09:12
 * --------------------------------------------------------- *
 */
public class FourProjectExcelData {

    private String company;
    private String projectName;  // 对应four_project表name
    private Integer fpId;
    private Integer mId;
    private String deptname;
    private Integer numb;
    private String scale;
    private String image;
    private String introduction;
    private String remark;

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    public Integer getNumb() {
        return numb;
    }

    public void setNumb(Integer numb) {
        this.numb = numb;
    }

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getFpId() {
        return fpId;
    }

    public void setFpId(Integer fpId) {
        this.fpId = fpId;
    }

    public Integer getmId() {
        return mId;
    }

    public void setmId(Integer mId) {
        this.mId = mId;
    }

    @Override
    public String toString() {
        return "FourProject{" +
                "company='" + company + '\'' +
                ", projectName='" + projectName + '\'' +
                ", fpId=" + fpId +
                ", mId=" + mId +
                ", deptname='" + deptname + '\'' +
                ", numb=" + numb +
                ", scale='" + scale + '\'' +
                ", image='" + image + '\'' +
                ", introduction='" + introduction + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
