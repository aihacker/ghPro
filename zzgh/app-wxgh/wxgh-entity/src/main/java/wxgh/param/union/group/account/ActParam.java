package wxgh.param.union.group.account;

import pub.dao.page.Page;

/**
 * Created by Administrator on 2017/8/16.
 */
public class ActParam extends Page {

    private Integer hasScore = 1;
    private Integer account = 0;
    private Integer groupId;

    public Integer getHasScore() {
        return hasScore;
    }

    public void setHasScore(Integer hasScore) {
        this.hasScore = hasScore;
    }

    public Integer getAccount() {
        return account;
    }

    public void setAccount(Integer account) {
        this.account = account;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }
}
