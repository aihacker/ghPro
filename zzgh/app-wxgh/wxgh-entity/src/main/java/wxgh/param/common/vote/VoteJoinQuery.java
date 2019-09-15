package wxgh.param.common.vote;

import java.io.Serializable;

/**
 *----------------------------------------------------------
 * @Description 
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-07-29 15:12
 *----------------------------------------------------------
 */
public class VoteJoinQuery implements Serializable {

    private Integer votedId;
    private Integer status = 1;

    private String userid;

    public VoteJoinQuery() {
    }

    public VoteJoinQuery(Integer votedId) {
        this.votedId = votedId;
    }

    public VoteJoinQuery(String userid, Integer votedId) {
        this.userid = userid;
        this.votedId = votedId;
    }

    public Integer getVotedId() {
        return votedId;
    }

    public void setVotedId(Integer votedId) {
        this.votedId = votedId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}

