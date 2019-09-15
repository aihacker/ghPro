package wxgh.param.union.race.arrange;


import pub.utils.StrUtils;
import pub.utils.TypeUtils;
import wxgh.entity.union.race.Race;
import wxgh.entity.union.race.RaceArrange;
import wxgh.entity.union.race.RaceGroup;
import wxgh.entity.union.race.RaceGroupDetail;
import wxgh.param.union.race.arrange.group.GroupInfo;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/5.
 */
public class RaceApi {

    private static int[] getTeams(Integer joinNumb) {
        boolean isEven = TypeUtils.isEvenNumber(joinNumb); //参赛队伍或选手是否为偶数

        int[] teams = new int[isEven ? joinNumb : joinNumb + 1];
        for (int i = 0; i < joinNumb; i++) {
            teams[i] = (i + 1);
        }
        if (!isEven) teams[joinNumb] = 0; //如果为奇数
        return teams;
    }

    private static int getLunshu(Integer joinNumb) {
        return TypeUtils.isEvenNumber(joinNumb) ? (joinNumb - 1) : joinNumb;
    }

    private static int getTotal(Integer joinNumb) {
        return joinNumb * (joinNumb - 1) / 2;
    }

    /**
     * 单循环“固定轮转”编排
     *
     * @param joinNumb 参赛队伍数量或参赛人数
     * @return
     */
    public static ArrangeResult single_loop_guding(Integer joinNumb) {
        Map<Integer, List<Arrange>> resultMap = new LinkedHashMap<>();

        int[] teams = getTeams(joinNumb);

        int len = teams.length;
        for (int i = 1; i < len; i++) {
            List<Arrange> results = new ArrayList<>();
            resultMap.put(i, results);
            for (int j = 0; j < len / 2; j++) {
                Arrange result = new Arrange();
                result.setRival1(teams[j]);
                result.setRival2(teams[len - 1 - j]);
                resultMap.get(i).add(result);
            }
            int temp = teams[len - 1];
            for (int k = len - 1; k > 0; k--) {
                teams[k] = teams[k - 1];
            }
            teams[1] = temp;
        }

        ArrangeResult result = new ArrangeResult();
        result.setArrangeMap(resultMap);
        result.setTotal(getTotal(joinNumb));
        result.setLunshu(getLunshu(joinNumb));

        return result;
    }

    /**
     * 单循环“贝格尔”编排
     *
     * @param joinNumb
     * @return
     */
    public static ArrangeResult single_loop_beige(Integer joinNumb) {
        Map<Integer, List<Arrange>> resultMap = new LinkedHashMap<>();

        int[] teams = getTeams(joinNumb);

        int len = teams.length;

        boolean empty = !TypeUtils.isEvenNumber(joinNumb); //是否轮空
        int jump; //调动幅度
        int round; //比赛轮数
        int flag; //标志，队伍的最大的 或者0，其他队伍在移动时，如果碰到他，则跳过
        int tempNum, tempNum1; //队伍在迭代时候保存临时变量的东西

        round = len - 1;
        jump = (len + 1) / 2 - 1;
        int team_temp[] = new int[len];

        flag = len - 1;

        for (int i = 0; i < round; i++) {
            System.out.println("第" + (i + 1) + "轮：");
            List<Arrange> results = new ArrayList<>();
            resultMap.put(i + 1, results);
            for (int j = 0; j < len / 2; j++) {
                Arrange result = new Arrange();
                result.setRival1(teams[j]);
                result.setRival2(teams[len - 1 - j]);
                System.out.println(result);
                resultMap.get(i + 1).add(result);
            }
            for (int m = 0; m < len; m++) {
                team_temp[m] = teams[m];
            }
            if (flag != 0) {
                tempNum = teams[flag];
                flag = 0;
                tempNum1 = teams[flag];
                teams[flag] = tempNum;
            } else {
                tempNum = teams[flag];
                tempNum1 = teams[len - 1];
                flag = len - 1;
                teams[flag] = team_temp[flag] = tempNum;
                teams[0] = team_temp[0] = tempNum1;
            }

            for (int k = 0; k < len - 1; k++) {
                int t = k;
                if (t >= len) {
                    t = t - len;
                }
                int z = t;
                for (int u = 0; u < jump; u++) {
                    t++;
                    if (t == len) {
                        t = t - len;
                    }
                    if (t == flag) {
                        t++;
                    }
                    if (t == len) {
                        t = t - len;
                    }
                }
                teams[t] = team_temp[z];
            }
        }

        ArrangeResult result = new ArrangeResult();
        result.setArrangeMap(resultMap);
        result.setTotal(getTotal(joinNumb));
        result.setLunshu(getLunshu(joinNumb));
        return result;
    }


    public static Map<Integer, List<Integer>> group_loop_fenzu(Integer joinNumb) {
        Map<Integer, List<Integer>> groupMap = new LinkedHashMap<>(); //用于保存分组结果
        int numb = 0;
        if (joinNumb % 9 == 0) {
            numb = joinNumb / 9 * 3;
        } else {
            if (joinNumb <= 4) {
                numb = 1;
            } else if (joinNumb > 4 && joinNumb / 2 <= 4) {
                numb = 2;
            } else if (joinNumb / 2 > 4 && joinNumb / 4 <= 4) {
                numb = 4;
            } else if (joinNumb / 4 > 4 && joinNumb / 8 <= 4) {
                numb = 8;
            } else if (joinNumb / 8 > 4) {
                numb = 8;
            }
        }

        List<Integer> teams = new ArrayList<>();
        for (int i = 0; i < joinNumb; i++) {
            teams.add(i + 1);
        }

        int everyGroupNumb = (int) (joinNumb / numb);
        int yuNumb = joinNumb % numb;
        for (int i = 0; i < numb; i++) {
            List<Integer> groups = new ArrayList<>();
            groupMap.put(i + 1, groups);

            for (int j = 0; j < everyGroupNumb; j++) {
                groups.add(teams.get(0));
                teams.remove(0);
            }
            if (i < yuNumb) {
                groups.add(teams.get(0));
                teams.remove(0);
            }
        }
        return groupMap;
    }

    public static List<RaceArrange> group_loop(List<GroupInfo> infos, Race race) {
        Map<String, List<RaceArrange>> arrangeMap = new LinkedHashMap<>();

        int maxCount = 0;
        List<String> groups = new ArrayList<>();
        for (GroupInfo info : infos) {
            List<RaceGroupDetail> details = info.getDetails();
            RaceGroup group = info.getGroup();
            groups.add(group.getName());
            Map<Integer, List<Arrange>> argMap = single_loop_guding(details.size()).getArrangeMap();

            List<RaceArrange> arranges = new ArrayList<>();
            arrangeMap.put(group.getName(), arranges);

            int i = 0;
            for (Map.Entry<Integer, List<Arrange>> entry : argMap.entrySet()) {
                List<Arrange> results = entry.getValue();
                for (int j = 0; j < results.size(); j++) {
                    int raval1 = results.get(j).getRival1();
                    int raval2 = results.get(j).getRival2();

                    RaceArrange arrange = new RaceArrange();
                    arrange.setRaceId(race.getId());
                    arrange.setRival1(raval1 == 0 ? null : details.get(raval1 - 1).getUserid());
                    arrange.setName1(raval1 == 0 ? null : details.get(raval1 - 1).getName());
                    arrange.setRival2(raval2 == 0 ? null : details.get(raval2 - 1).getUserid());
                    arrange.setName2(raval2 == 0 ? null : details.get(raval2 - 1).getName());
                    arrange.setLunNum(entry.getKey());
                    arrange.setOrderNum(entry.getKey() * (i + 1));
                    arrange.setType(race.getRaceType());
                    arrange.setArrangeType(RaceArrange.ARRANGE_TYPE_GROUP);
                    arrange.setStatus(0);
                    arrange.setRemark(group.getName());
                    arrange.setLunkong((StrUtils.empty(arrange.getRival1()) || StrUtils.empty(arrange.getRival2())) ? 1 : 0);
                    arranges.add(arrange);
                    i++;
                }
            }

            if (arranges.size() > maxCount) {
                maxCount = arranges.size();
            }
        }

        List<RaceArrange> arranges = new ArrayList<>();

        int j = 1;
        for (int i = 0; i < maxCount; i++) {
            for (Map.Entry<String, List<RaceArrange>> entry : arrangeMap.entrySet()) {
                List<RaceArrange> as = entry.getValue();
                if (i + 1 < as.size()) {
                    RaceArrange a = as.get(i);
                    a.setOrderNum(j);
                    arranges.add(a);
                    j++;
                }
            }
        }
        return arranges;
    }

    /**
     * 淘汰赛
     *
     * @param joinNumb
     * @return
     */
    public static List<ArrangeResult> eliminate(Integer joinNumb) {
        List<ArrangeResult> results = new ArrayList<>();
        boolean isTwo = TypeUtils.isTwo(joinNumb); //是否为2的乘方数

        int lunshu = (int) TypeUtils.log(joinNumb, 2); //比赛轮数
        if (!isTwo) lunshu += 1; //如果未非2的乘方数，则轮空指数+1
        int teams[] = new int[(int) Math.pow(2, lunshu) / 2];

        if (!isTwo) { //非2的乘方数
            int lunkong = (int) (Math.pow(2, lunshu)) - joinNumb;
            int shangbanqi;
            int xiabanqi;
            if (TypeUtils.isEvenNumber(lunkong)) { //如果轮空数为双数
                shangbanqi = xiabanqi = lunkong / 2;
            } else {
                shangbanqi = ((int) lunkong / 2) + 1; //上半区
                xiabanqi = (int) lunkong / 2; //下半区
            }

            for (int i = 0; i < teams.length; i++) {
                if (i < shangbanqi) { //上半区
                    teams[i] = 0;
                } else { //下半区

                }
            }
        } else { //为2的乘方
            for (int i = 0; i < teams.length; i++) {
                teams[i] = i + 1;
            }
        }


        return results;
    }

}
