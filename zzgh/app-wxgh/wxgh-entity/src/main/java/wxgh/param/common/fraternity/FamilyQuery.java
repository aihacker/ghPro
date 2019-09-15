package wxgh.param.common.fraternity;

import java.io.Serializable;

/**
 *----------------------------------------------------------
 * @Description 
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-08-01 11:20
 *----------------------------------------------------------
 */
public class FamilyQuery implements Serializable {

    private String userid;
    private String relation;

    public FamilyQuery(String userid) {
        this.userid = userid;
    }

    public FamilyQuery(String userid, String relation) {
        this.userid = userid;
        this.relation = relation;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

}