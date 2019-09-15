package wxgh.data.pub.push;

import java.util.List;

/**
 * todo 2017-09-06 添加默认agentid为管理员审核应用
 * Created by Administrator on 2017/8/10.
 */
public class ApplyPush extends WxPush {

    private Type type; //推送类型
    private String fromUser;
    private String paramVal;
    private List<String> toUsers;

    public List<String> getToUsers() {
        return toUsers;
    }

    public void setToUsers(List<String> toUsers) {
        this.toUsers = toUsers;
    }

    public ApplyPush() {
        super(71);
    }

    public ApplyPush(Type type, String fromUser, String paramVal) {
        super(1000008);
        this.type = type;
        this.fromUser = fromUser;
        this.paramVal = paramVal;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getParamVal() {
        return paramVal;
    }

    public void setParamVal(String paramVal) {
        this.paramVal = paramVal;
    }

    public enum Type {
        WORK_ORDER("党务工单审核", "/wx/party/workorder/show.html?id="),
        PARTY_LEDGER("入党申请", "/wx/party/ledger/show.html?id="),
        GROUP("兴趣协会", "/wx/admin/union/group/show.html?id="),
        BBS("热点论坛", "/wx/admin/common/bbs/show.html?id="),
        BIG_ACT("大型活动", "/wx/admin/entertain/act/show.html?id="),

        SUGGEST("会员提案", "/wx/admin/union/suggest/show.html?id="),

        VOTE("投票", "/wx/admin/common/vote/detail.html?id="),
        VOTEPIC("图片投票","/wx/admin/common/vote/pic.html?id="),
        FRATERNITY("互助会入会", "/wx/admin/common/fraternity/index.html"),
        INNOVATION_ADVICE_MRICO("创新项目资金申请", "/wx/admin/union/advice/micro/index.html?adviceId="),
        INNOVATION_ADVICE("创新建议", "/wx/admin/union/advice/show/index.html?id="),
        INNOVATION_WORK("工作坊", "/wx/admin/union/work/show/index.html?id="),

        ZIZHU_JB("疾病资助", "/wx/admin/common/disease/detail.html?type=getJB&applyId="),
        ZIZHU_JY("教育资助", "/wx/admin/common/disease/detail.html?type=getJY&applyId="),
        ZIZHU_QS("去世资助", "/wx/admin/common/disease/detail.html?type=getQS&applyId="),
        ZIZHU_ZC("因公致残资助", "/wx/admin/common/disease/detail.html?type=getZC&applyId="),
        ZIZHU_ZH("自然灾害资助", "/wx/admin/common/disease/detail.html?type=getZH&applyId="),
        ZIZHU_ZX("直系亲属去世慰问", "/wx/admin/common/disease/detail.html?type=getZX&applyId="),
        ZIZHU_PK("困难家庭资助", "/wx/admin/common/disease/detail.html?type=getPK&applyId="),

        GROUP_MM("兴趣协会新成员审核", "/wx/union/group/apply/index.html?id="),

        HOME_ACT("工会家园活动申请", "/wx/admin/common/act/detail.html?id="),

        SPORT_APPLY("健步积分申述", "/wx/admin/entertain/sport/apply/show.html?id="),

        PHOTO("魅美影像","/wx/admin/beauty/show.html?id="),

        CANTEEN("饭堂报餐新成员审核","/wx/canteen/apply/index.html?id=");


        private String type;
        private String url;

        Type(String type, String url) {
            this.type = type;
            this.url = url;
        }

        public String getType() {
            return type;
        }

        public String getUrl() {
            return url;
        }
    }

    @Override
    public String toString() {
        return "ApplyPush{" +
                "type=" + type +
                ", fromUser='" + fromUser + '\'' +
                ", paramVal='" + paramVal + '\'' +
                ", toUsers=" + toUsers +
                '}';
    }
}
