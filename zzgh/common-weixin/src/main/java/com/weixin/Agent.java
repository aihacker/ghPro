package com.weixin;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/4.
 */
public enum Agent {

    CONCAT(-1, "TgoKNWUxuMc-kM67v-GObFWXodmZAsTUpmKadW0KL5E"), //通讯录
    //
    //hqvEnN5kWvuFiIKUmInEYO6u69BBqNFP-W4sKgbGbk0





    USER(1000002, "kDHCBz7AK6fqaN5KYP0HbD4b_QtR1VBzZSYdDg5-30A"), //个人中心

    BBS(1000003, "ksdKuCXtEn_ztkNuWJY7_Aks7nYFi2ze6vguXKXmF1w"), //岗位创新

    WORK(1000004, "4ywMB3DSIDYzgX2t5hhqn3TjWevoP4kKLX70RQ8g2GI"), //工会家园

    ACT(1000005, "zkl0BAQIH76Vi64DylbFErzXe_IjT5g1JKRNIAmefjA"), //大型活动


    SHEYING(1000006, "lZcIRZbrCJjfNC3UjVSfFoZFciiEzC9AAQ1llO-3vf0"), //摄影（魅美影像）


    TRAIN(1000007, "Nlf4JWTUOT2kVEg07PJdLJJ_4GfTHc6q6FBvQdNG6mg"), //民主管理


    FOUR(1000008, "QabwyyHUbiYWvhPOP9Lg9RmeXQKizLBA8rSwrypqVls"), //四小建设

    GROUP(1000009, "JU9AlkNklsKN_AlQAl4fi0dotvPFuMxF4ungaiCUtH8"), //兴趣协会

    ADMIN(1000010, "Scf50aR7Vl4wzE09lwVXvCQfuDJfYivn-RQRBONIcW0"), //管理员审核

    UNION(1000011, "l7ZSYwN85cK844K1ZQkrSc1ilw_EfApzClu9ntcrHHA"), //热点论坛



    SPORT(0, "c0RYDRTwxMgTl6VF1_wLFa3Lj8ezyKRbmw2I00yn6qw"), //工会运动





    MATCH(0, "HJ0emsX6O5lOpvtR_qR_8HKUfXLXsPGacfqNWxPMKPs"), //赛事信息


    NEWS(0, "gT8cNT_tJclriF843YyheIifAeoeXS4eixnx-8cEx0o"),//新闻频道

    YUYUE(0, "J6xQjM38Ys9-oqjDy0PLy7jeA0FWjNoZ-hDlf_jwJn8"),//场馆预约

    CANTEEN(0,"OZ4CrquGLJXxYvuCVDqBVwLXbv7nCaLH8qfGfhgd9gI"),//饭堂报餐

    MILEAGE(0,"DTMfP6LLtVSUb9Ka5xv3S-gvDsELl6w6gn0f6kWSPaE");//公车里程

/*    USER(1000002, "IzlLusS0wkCAGzsd65jsjnkYFP-5MuY2L87Biqsq-qs"), //个人中心

    BBS(1000003, "7KWoEoagwSOtzrIAwznNvgBCNnkR1PlNWWD-PFxX66s"), //热点论坛

    UNION(1000004, "L1IV1_H6fU0rPYcm-JIndEYA4mHplpZpLk6-83kuL9c"), //工会家园

    WORK(1000005, "qv-66E336EIQaL-pidWARUH71U3u_THNWXPyxDrzUww"), //岗位创新

    TRAIN(1000006, "Zp-1C9heoJCcFPG2k3b8Maa2Wi2tXowi9qTxRZH-EE4"), //总经理直通车

    SPORT(1000007, "_-kcNI5qndatJ94H0IgC84W-EP7zBqMZ8ownf5Afc8E"), //工会运行

    ADMIN(1000008, "fgsDIUtZj9uqGTmPxByP-GEzLD-nHiplR4_42mFzXes"), //管理员审核

    FOUR(1000009, "syaBWLhxxzBIbHUDWlV4n5JwAHeCEHeaQL38n0vpQs0"), //四小建设

    GROUP(1000010, "2UlvU9kvf2f1Ib8B0oP5VKTSNa52atjThhyq5XuL1-k"), //兴趣协会

    ACT(1000011, "yx93hmn3kTyAEGoZtuDUx0fL-aLHoVaNAJya5kV4vXo"), //大型活动

    MATCH(1000012, "dZ1z925wuK0q8Y9WE7StoTuSVNCSUUouuQXOCZ8er-A"), //赛事信息

    SHEYING(1000013, "Pr5ZcJHXFQJPqRyXGRasEbk4IbfZ2df6vVakdzrPIWM"); //摄影（魅美影像）*/


    private Integer agentId;
    private String secret;

    Agent(Integer agentId, String secret) {
        this.agentId = agentId;
        this.secret = secret;
    }

    public Integer getAgentId() {
        return agentId;
    }

    public String getSecret() {
        return secret;
    }

    public static Agent from(Integer agentId) {
        return map.get(agentId);
    }

    private static final Map<Integer, Agent> map = new HashMap<>();
    static {
        for (Agent agent : values()) {
            map.put(agent.getAgentId(), agent);
        }
    }
}
