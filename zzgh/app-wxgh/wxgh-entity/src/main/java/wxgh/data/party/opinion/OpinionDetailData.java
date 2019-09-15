package wxgh.data.party.opinion;


import wxgh.entity.party.opinion.PartyOpinionDetail;

/**
 * @Version 1.0
 * --------------------------------------------------------- *
 * @Description >_
 * --------------------------------------------------------- *
 * @Author Ape
 * --------------------------------------------------------- *
 * @Email <16511660@qq.com>
 * --------------------------------------------------------- *
 * @Date 2018-01-23  19:41
 * --------------------------------------------------------- *
 */
public class OpinionDetailData extends PartyOpinionDetail {

    private String opinionTitle;
    private String username;

    public String getOpinionTitle() {
        return opinionTitle;
    }

    public void setOpinionTitle(String opinionTitle) {
        this.opinionTitle = opinionTitle;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
