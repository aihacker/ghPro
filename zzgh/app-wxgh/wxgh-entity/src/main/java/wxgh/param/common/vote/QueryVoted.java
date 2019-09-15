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
 * @Date 2017-07-29 15:11
 *----------------------------------------------------------
 */
public class QueryVoted implements Serializable {
    private Integer id;
    private Integer status;
    private Integer start;
    private Integer num;


    private Integer isFirst;
    private Integer voteOldestId;

    public Integer getIsFirst() {
        return isFirst;
    }

    public void setIsFirst(Integer isFirst) {
        this.isFirst = isFirst;
    }

    public Integer getVoteOldestId() {
        return voteOldestId;
    }

    public void setVoteOldestId(Integer voteOldestId) {
        this.voteOldestId = voteOldestId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}

